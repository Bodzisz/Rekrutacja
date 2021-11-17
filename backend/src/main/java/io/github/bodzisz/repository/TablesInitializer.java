package io.github.bodzisz.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bodzisz.model.ListOfTables;
import io.github.bodzisz.model.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class TablesInitializer {

    private final TableRepository repository;
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(TablesInitializer.class);

    public TablesInitializer(final TableRepository repository, final ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    // when 'tables' is empty in database
    @Transactional
    @PostConstruct
    public void initialize() {
        if(repository.findAll().size() == 0) {
            Path fileName = Path.of("seats.json");
            try {
                String json = Files.readString(fileName);
                ListOfTables listOfTables = objectMapper.readValue(json, ListOfTables.class);
                repository.saveAll(listOfTables.getTables());
                logger.info("Added tables to database from JSON file!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

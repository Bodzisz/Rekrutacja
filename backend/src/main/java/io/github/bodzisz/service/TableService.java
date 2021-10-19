package io.github.bodzisz.service;

import io.github.bodzisz.model.Table;
import io.github.bodzisz.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableService {
    private TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }


    public List<Table> getAllTables() {
        return  tableRepository.findAll();
    }

    public List<Table> getAllTablesWith(Integer seats, String status, LocalDateTime date, Integer duration) {
        List<Table> allTables = tableRepository.findAll();

        boolean available;
        if(status == null) {
            available = true;
        }
        else {
            available = !status.equals("taken");
        }
        if(date == null) { date = LocalDateTime.now(); }
        if(duration == null) { duration = 0; }

        boolean finalAvailable = available;
        LocalDateTime finalDate = date;
        Integer finalDuration = duration;

        if(seats == null) {
            allTables = allTables.stream()
                    .filter(table -> table.isAvailable(finalDate, finalDuration) == finalAvailable)
                    .collect(Collectors.toList());
        }
        else {
            allTables = allTables.stream()
                    .filter(table -> table.getMinNumberOfSeats() <= seats)
                    .filter(table -> table.getMaxNumberOfSeats() >= seats)
                    .filter(table -> table.isAvailable(finalDate, finalDuration) == finalAvailable)
                    .collect(Collectors.toList());
        }

        return allTables;
    }


}

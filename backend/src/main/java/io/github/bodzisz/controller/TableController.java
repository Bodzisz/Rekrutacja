package io.github.bodzisz.controller;

import io.github.bodzisz.model.Table;
import io.github.bodzisz.service.TableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/tables")
public class TableController {
    private TableService tableService;
    private Logger logger = LoggerFactory.getLogger(TableController.class);

    public TableController(final TableService tableService) {
        this.tableService = tableService;
    }


    @GetMapping
    public ResponseEntity<List<Table>> getAllTablesWithParams(@RequestParam(required = false, name = "seats") Integer seats,
                                                              @RequestParam(required = false) String status,
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                              @RequestParam(required = false, name = "date")LocalDateTime startDate,
                                                              @RequestParam(required = false) Integer duration) {

        return ResponseEntity.ok(tableService.getAllTablesWith(seats, status, startDate, duration));
    }
}

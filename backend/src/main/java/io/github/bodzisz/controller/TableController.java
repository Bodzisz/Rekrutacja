package io.github.bodzisz.controller;

import io.github.bodzisz.model.Table;
import io.github.bodzisz.repository.TableRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/tables")
public class TableController {
    TableRepository tableRepository;

    public TableController(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

//    @GetMapping(path = "/tables", produces = "application/json")
//    public ResponseEntity<List<Table>> getAllTables(@RequestParam(required = false) Integer min_seats,
//                                                    @RequestParam(required = false) String status) {
//        if(min_seats == null) { min_seats = 0; }
//        if(status == null) { status = "free"; }
//        return ResponseEntity.ok(tableRepository.getTables().getTables(status, min_seats));
//    }

    @GetMapping()
    public ResponseEntity<List<Table>> getAllTables() {
        return ResponseEntity.ok(tableRepository.findAll());
    }
}

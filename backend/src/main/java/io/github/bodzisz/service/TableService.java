package io.github.bodzisz.service;

import io.github.bodzisz.repository.TableRepository;

public class TableService {
    private TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }


}

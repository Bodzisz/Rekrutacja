package io.github.bodzisz.model;

import java.util.List;

public class ListOfTables {

    private List<Table> tables;

    public ListOfTables() {
    }

    public ListOfTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
}

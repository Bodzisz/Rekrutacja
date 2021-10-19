package io.github.bodzisz.model;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "cancellations")
public class Cancellation {
    @Id
    private int id;
    private String code;

    public Cancellation() {
    }

    public Cancellation(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }
}

package io.github.bodzisz.model;

import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@javax.persistence.Table(name = "tables")
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int number;
    @Column(name = "min_number_of_seats")
    private int minNumberOfSeats;
    @Column(name = "max_number_of_seats")
    private int maxNumberOfSeats;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name="seat_number")
    private List<Reservation> reservations;

    public Table() {
    }

    public Table(int number, int minNumberOfSeats, int maxNumberOfSeats) {
        this.number = number;
        this.minNumberOfSeats = minNumberOfSeats;
        this.maxNumberOfSeats = maxNumberOfSeats;
    }

    @Override
    public String toString() {
        return "Table{" +
                "number=" + number +
                ", minNumberOfSeats=" + minNumberOfSeats +
                ", maxNumberOfSeats=" + maxNumberOfSeats +
                '}';
    }

    public boolean isAvailable(LocalDateTime date, int duration) {
        for(Reservation reservation : reservations) {
            if(date.isAfter(reservation.getDate()) &&
                    date.plusHours(duration).isBefore(reservation.getDate().plusHours(reservation.getDuration()))) {
                return false;
            }
        }
        return true;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getMinNumberOfSeats() {
        return minNumberOfSeats;
    }

    public void setMinNumberOfSeats(int minNumberOfSeats) {
        this.minNumberOfSeats = minNumberOfSeats;
    }

    public int getMaxNumberOfSeats() {
        return maxNumberOfSeats;
    }

    public void setMaxNumberOfSeats(int maxNumberOfSeats) {
        this.maxNumberOfSeats = maxNumberOfSeats;
    }
}

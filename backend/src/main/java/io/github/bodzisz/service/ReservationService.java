package io.github.bodzisz.service;

import io.github.bodzisz.model.Reservation;
import io.github.bodzisz.model.Table;
import io.github.bodzisz.repository.ReservationRepository;
import io.github.bodzisz.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    ReservationRepository reservationRepository;
    TableRepository tableRepository;

    public ReservationService(ReservationRepository reservationRepository, TableRepository tableRepository) {
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
    }

    public List<Reservation> getAllReservations(LocalDateTime date) {
        if(date == null) {
            return reservationRepository.findAll();
        }
        return reservationRepository.findReservationByDateBetween(date.toLocalDate().atStartOfDay(),
                date.plusDays(1).toLocalDate().atStartOfDay());
    }

    public Reservation saveReservation(Reservation reservation) {
        Table table = tableRepository.findById(reservation.getSeatNumber())
                .orElseThrow(() -> new IllegalArgumentException("Table with given seat number does not exist"));

        if(!table.isAvailable(reservation.getDate(), reservation.getDuration())) {
            throw new IllegalArgumentException("Table is not available at the selected time");
        }
        return reservationRepository.save(reservation);
    }
}

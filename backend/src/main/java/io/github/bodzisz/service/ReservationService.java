package io.github.bodzisz.service;

import io.github.bodzisz.model.Reservation;
import io.github.bodzisz.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations(LocalDateTime date) {
        if(date == null) {
            return reservationRepository.findAll();
        }
        return reservationRepository.findReservationByDateBetween(date.toLocalDate().atStartOfDay(),
                date.plusDays(1).toLocalDate().atStartOfDay());
    }

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}

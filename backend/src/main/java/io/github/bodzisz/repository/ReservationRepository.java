package io.github.bodzisz.repository;

import io.github.bodzisz.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository{

    List<Reservation> findAll();

    Optional<Reservation> findById(Integer id);

    List<Reservation> findReservationByDateBetween(LocalDateTime start, LocalDateTime end);

    Reservation save(Reservation reservation);

    void deleteById(Integer id);
}

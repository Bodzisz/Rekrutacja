package io.github.bodzisz.controller;

import io.github.bodzisz.model.Reservation;
import io.github.bodzisz.repository.ReservationRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations(@RequestParam(name = "date",required = false)
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                            LocalDateTime date) {
        if(date == null) {
            return ResponseEntity.ok(reservationRepository.findAll());
        }
        return ResponseEntity.ok(reservationRepository.findReservationByDateBetween(date.toLocalDate().atStartOfDay(),
                date.plusDays(1).toLocalDate().atStartOfDay()));
    }

    @PostMapping
    public ResponseEntity<Reservation> saveReservation (@RequestBody Reservation reservation) {
        Reservation savedReservation = reservationRepository.save(reservation);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedReservation.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedReservation);
    }
}

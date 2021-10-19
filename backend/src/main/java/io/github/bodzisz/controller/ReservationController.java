package io.github.bodzisz.controller;

import io.github.bodzisz.model.Reservation;
import io.github.bodzisz.repository.ReservationRepository;
import io.github.bodzisz.service.ReservationService;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations(@RequestParam(name = "date",required = false)
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                            LocalDateTime date) {
        return ResponseEntity.ok(reservationService.getAllReservations(date));
    }

    @PostMapping
    public ResponseEntity<?> saveReservation (@RequestBody Reservation reservation) {
        try {
            Reservation savedReservation = reservationService.saveReservation(reservation);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedReservation.getId())
                    .toUri();

            return ResponseEntity.created(location).body(savedReservation);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> requestReservationCancellation(@PathVariable("id") int id) {
        try {
            reservationService.requestReservationCancellation(id);
        } catch (IllegalArgumentException | IllegalStateException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Reservation cancellation processed successfully. Check out your email for verification code");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable("id") int id, @RequestBody String code) {
        try {
            reservationService.deleteReservation(id, code);
        } catch(IllegalArgumentException | IllegalStateException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Reservation deleted!");
    }
}

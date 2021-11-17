package io.github.bodzisz.repository.adapter;

import io.github.bodzisz.model.Reservation;
import io.github.bodzisz.repository.ReservationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ReservationRepositoryAdapter extends JpaRepository<Reservation, Integer>, ReservationRepository {
}

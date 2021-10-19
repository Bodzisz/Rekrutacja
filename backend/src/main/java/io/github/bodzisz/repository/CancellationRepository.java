package io.github.bodzisz.repository;

import io.github.bodzisz.model.Cancellation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CancellationRepository extends JpaRepository<Cancellation, Integer> {

    Optional<Cancellation> getCancellationById(int id);
}

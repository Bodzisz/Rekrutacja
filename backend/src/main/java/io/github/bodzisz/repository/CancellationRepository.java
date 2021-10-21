package io.github.bodzisz.repository;

import io.github.bodzisz.model.Cancellation;
import java.util.Optional;

public interface CancellationRepository {

    Optional<Cancellation> getCancellationById(int id);

    Cancellation save(Cancellation cancellation);

    boolean existsById(Integer id);
}

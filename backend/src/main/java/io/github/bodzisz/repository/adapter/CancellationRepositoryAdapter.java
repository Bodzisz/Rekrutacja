package io.github.bodzisz.repository.adapter;

import io.github.bodzisz.model.Cancellation;
import io.github.bodzisz.repository.CancellationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CancellationRepositoryAdapter extends JpaRepository<Cancellation, Integer>, CancellationRepository {
}

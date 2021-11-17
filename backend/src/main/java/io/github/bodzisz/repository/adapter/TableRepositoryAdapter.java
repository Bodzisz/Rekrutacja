package io.github.bodzisz.repository.adapter;

import io.github.bodzisz.model.Table;
import io.github.bodzisz.repository.TableRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TableRepositoryAdapter extends JpaRepository<Table, Integer>, TableRepository {
}

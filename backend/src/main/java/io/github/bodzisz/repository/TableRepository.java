package io.github.bodzisz.repository;

import io.github.bodzisz.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TableRepository {

    List<Table> findAll();

    Optional<Table> findById(Integer id);

    <S extends Table> List<S> saveAll(Iterable<S> entities);
}

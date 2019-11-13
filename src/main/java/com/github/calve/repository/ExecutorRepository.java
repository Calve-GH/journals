package com.github.calve.repository;

import com.github.calve.model.Executor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ExecutorRepository extends JpaRepository<Executor, Integer> {

    @Query("SELECT e FROM Executor e WHERE e.name=:name")
    Executor findByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM Executor e where e.id=:id")
    int delete(@Param("id") Integer id);

    @Override
    List<Executor> findAll();

    @Override
    @Transactional
    <E extends Executor> E save(E entity);

    @Query("SELECT e FROM Executor e WHERE e.enabled=true")
    List<Executor> findAllEnabled();

    @Override
    Optional<Executor> findById(Integer integer);
}

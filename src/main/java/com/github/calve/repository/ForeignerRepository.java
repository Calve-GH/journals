package com.github.calve.repository;

import com.github.calve.model.Foreigner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ForeignerRepository extends JpaRepository<Foreigner, Integer> {

    @Override
    @Transactional
    <S extends Foreigner> S save(S entity);

    @Transactional
    @Modifying
    @Query("DELETE FROM Foreigner f where f.id=:id")
    int delete(@Param("id") Integer id); // TODO: 26.09.2019 mb return id;

    Optional<Foreigner> findById(Integer id);

    @Override
    List<Foreigner> findAll();

    //refactoring n + 1 PROBLEM
//    @EntityGraph(attributePaths = {})
    @Query("SELECT f FROM Foreigner f WHERE f.incomeDate BETWEEN :from AND :to ")
    List<Foreigner> getBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);
}

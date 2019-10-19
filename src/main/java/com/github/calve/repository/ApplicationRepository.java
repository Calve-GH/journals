package com.github.calve.repository;

import com.github.calve.model.Application;
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
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    @Override
    @Transactional
    <A extends Application> A save(A entity);

    @Transactional
    @Modifying
    @Query("DELETE FROM Application a where a.id=:id")
    int delete(@Param("id") Integer id); // TODO: 26.09.2019 mb return id;

    Optional<Application> findById(Integer id);

    @Override
    List<Application> findAll();

    //refactoring n + 1 PROBLEM
//    @EntityGraph(attributePaths = {})
    @Query("SELECT a FROM Application a WHERE a.incomeDate BETWEEN :from AND :to ")
    List<Application> getBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);
}

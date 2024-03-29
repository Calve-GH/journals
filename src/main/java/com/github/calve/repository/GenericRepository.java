package com.github.calve.repository;

import com.github.calve.model.journal.Generic;
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
public interface GenericRepository extends JpaRepository<Generic, Integer>, JpaSpecificationExecutor<Generic> {

    @Override
    @Transactional
    <G extends Generic> G save(G entity);

    @Transactional
    @Modifying
    @Query("DELETE FROM Generic g where g.id=:id")
    int delete(@Param("id") Integer id); // TODO: 26.09.2019 mb return id;

    @Override
    Optional<Generic> findById(Integer id);

    @Override
    List<Generic> findAll();
}

package com.github.calve.repository;

import com.github.calve.model.journal.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface RequestRepository extends JpaRepository<Request, Integer>, JpaSpecificationExecutor<Request> {

    @Override
    @Transactional
    <R extends Request> R save(R entity);

    @Transactional
    @Modifying
    @Query("DELETE FROM Request r where r.id=:id")
    int delete(@Param("id") Integer id); // TODO: 26.09.2019 mb return id;

    @Override
    Optional<Request> findById(Integer id);

    @Override
    List<Request> findAll();

    @Override
    Page<Request> findAll(Pageable pageable);
}

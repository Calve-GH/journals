package com.github.calve.repository;

import com.github.calve.model.email.Sent;
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
public interface SentRepository extends JpaRepository<Sent, Integer>, JpaSpecificationExecutor<Sent> {

    @Override
    @Transactional
    <S extends Sent> S save(S entity);

    @Transactional
    @Modifying
    @Query("DELETE FROM Sent s where s.id=:id")
    int delete(@Param("id") Integer id);

    @Override
    Optional<Sent> findById(Integer id);

    @Override
    List<Sent> findAll();
}

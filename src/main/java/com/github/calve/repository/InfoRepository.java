package com.github.calve.repository;

import com.github.calve.model.Info;
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
public interface InfoRepository extends JpaRepository<Info, Integer> {

    @Override
    @Transactional
    <S extends Info> S save(S entity);

    @Transactional
    @Modifying
    @Query("DELETE FROM Info i where i.id=:id")
    int delete(@Param("id") Integer id); // TODO: 26.09.2019 mb return id;

    Optional<Info> findById(Integer id);

    @Override
    List<Info> findAll();
}

package com.github.calve.repository;

import com.github.calve.model.common.Outgoing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OutgoingRepository extends JpaRepository<Outgoing, Integer>, JpaSpecificationExecutor<Outgoing> {

    @Override
    @Transactional
    <O extends Outgoing> O save(O entity);

    @Query("SELECT max(m.genIndex) FROM Outgoing m where year(m.outerDate)=:year")
    int maxByYear(@Param("year") Integer year);

    @Transactional
    @Modifying
    @Query("DELETE FROM Outgoing m where m.id=:id")
    int delete(@Param("id") Integer id);

    @Override
    Optional<Outgoing> findById(Integer id);

    @Override
    List<Outgoing> findAll();
}

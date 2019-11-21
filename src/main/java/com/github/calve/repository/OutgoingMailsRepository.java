package com.github.calve.repository;

import com.github.calve.model.OutgoingMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OutgoingMailsRepository extends JpaRepository<OutgoingMail, Integer>, JpaSpecificationExecutor<OutgoingMail> {

    @Override
    @Transactional
    <O extends OutgoingMail> O save(O entity);

    @Query("SELECT max(m.genIndex) FROM OutgoingMail m where year(m.outerDate)=:year")
    int maxByYear(@Param("year") Integer year);

    @Transactional
    @Modifying
    @Query("DELETE FROM OutgoingMail m where m.id=:id")
    int delete(@Param("id") Integer id);

    @Override
    Optional<OutgoingMail> findById(Integer id);

    @Override
    List<OutgoingMail> findAll();
}

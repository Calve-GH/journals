package com.github.calve.repository;

import com.github.calve.model.email.Inbox;
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
public interface InboxRepository extends JpaRepository<Inbox, Integer>, JpaSpecificationExecutor<Inbox> {

    @Override
    @Transactional
    <I extends Inbox> I save(I entity);

    @Query("SELECT max(i.genIndex) FROM Inbox i where year(i.regDate)=:year")
    int maxByYear(@Param("year") Integer year);

    @Transactional
    @Modifying
    @Query("DELETE FROM Inbox i where i.id=:id")
    int delete(@Param("id") Integer id);

    @Override
    Optional<Inbox> findById(Integer id);

    @Override
    List<Inbox> findAll();
}

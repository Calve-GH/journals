package com.github.calve.repository;

import com.github.calve.model.Complaint;
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
public interface ComplaintRepository extends JpaRepository<Complaint, Integer>, JpaSpecificationExecutor<Complaint> {

    @Override
    @Transactional
    <C extends Complaint> C save(C entity);

    @Transactional
    @Modifying
    @Query("DELETE FROM Complaint c where c.id=:id")
    int delete(@Param("id") Integer id); // TODO: 26.09.2019 mb return id;

    @Override
    Optional<Complaint> findById(Integer id);

    @Override
    List<Complaint> findAll();
}

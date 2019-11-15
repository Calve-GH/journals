package com.github.calve.repository;

import com.github.calve.model.Application;
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
public interface ApplicationRepository extends JpaRepository<Application, Integer>, JpaSpecificationExecutor<Application> {

    @Override
    @Transactional
    <A extends Application> A save(A entity);

    @Transactional
    @Modifying
    @Query("DELETE FROM Application a where a.id=:id")
    int delete(@Param("id") Integer id);

    @Override
    Optional<Application> findById(Integer id);

    @Override
    List<Application> findAll();
}

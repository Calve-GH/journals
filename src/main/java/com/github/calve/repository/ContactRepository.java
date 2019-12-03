package com.github.calve.repository;

import com.github.calve.model.etc.Contact;
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
public interface ContactRepository extends JpaRepository<Contact, Integer>, JpaSpecificationExecutor<Contact> {

    @Query("SELECT c FROM Contact c WHERE c.name=:name")
    Contact findByAlias(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM Contact c where c.id=:id")
    int delete(@Param("id") Integer id);

    @Override
    List<Contact> findAll();

    @Override
    @Transactional
    <C extends Contact> C save(C entity);

    @Override
    Optional<Contact> findById(Integer integer);
}

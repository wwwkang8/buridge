package com.realestate.service.property.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

  @Query("select p from Property p "
       + "where p.id = :id "
       + "and p.user.id = :userId "
       + "and p.deletedDateTime is null ")
  Optional<Property> findByIdAndUserId(@Param("id") long id, @Param("userId") long userId);
}

package com.realestate.service.property.address.entity;

import java.util.Optional;

import com.realestate.service.property.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyAddressRepository extends JpaRepository<PropertyAddress, Long> {

  Optional<PropertyAddress> findByProperty(Property property);
}

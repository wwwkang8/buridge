package com.realestate.service.property.address.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyAddressRepository extends JpaRepository<PropertyAddress, Long> {
}

package com.realestate.service.user.repository;

import java.util.Optional;

import com.realestate.service.user.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findUserByEmail(String email);

}

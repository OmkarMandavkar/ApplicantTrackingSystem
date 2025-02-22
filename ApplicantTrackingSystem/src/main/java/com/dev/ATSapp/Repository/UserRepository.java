package com.dev.ATSapp.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.ATSapp.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

}

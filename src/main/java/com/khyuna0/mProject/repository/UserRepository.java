package com.khyuna0.mProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khyuna0.mProject.entity.SiteUser;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
	
	public Optional<SiteUser> findByUsername(String username);
}

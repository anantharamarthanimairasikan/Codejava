package com.prodapt.capstoneproject.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.capstoneproject.entities.ERole;
import com.prodapt.capstoneproject.entities.UserEntity;
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	
	public Optional<UserEntity> findByUsername(String username);

	public Boolean existsByUsername(String username);

	public Optional<UserEntity> findByRole(ERole role);

}


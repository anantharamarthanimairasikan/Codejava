package com.prodapt.capstoneproject.security.service;

import java.util.Optional;

import com.prodapt.capstoneproject.entities.ERole;
import com.prodapt.capstoneproject.entities.UserEntity;

public interface UserEntityService {
	
	public UserEntity addUserEntity(UserEntity user);

	public String updateRole(Long userId, ERole role);
	
	public UserEntity updateUser(UserEntity user) throws Exception;

	public Optional<UserEntity> findByUsername(String username);

	public Boolean existsByUsername(String username);

	public Optional<UserEntity> findByRole(ERole role);
	
	public void deletebyusername(String username);
	
}

package com.prodapt.capstoneproject.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.capstoneproject.entities.ERole;
import com.prodapt.capstoneproject.entities.UserEntity;
import com.prodapt.capstoneproject.repositories.UserRepository;

@Service
public class UserEntityServiceImpl implements UserEntityService {

	@Autowired
	UserRepository repo;
	
	@Override
	public String updateRole(Long userId, ERole role) {
		Optional<UserEntity> user= repo.findById(userId);
		if(user.isPresent())
		{
		  user.get().setRole(role);
		  repo.save(user.get());
		  return "Role Updated Successfully!!!";
		  
		}
		return null;
	}

	@Override
	public UserEntity addUserEntity(UserEntity user) {
		return repo.save(user);
	}

	@Override
	public Optional<UserEntity> findByUsername(String username) {
		return repo.findByUsername(username);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return repo.existsByUsername(username);
	}

	@Override
	public Optional<UserEntity> findByRole(ERole role) {
		 return repo.findByRole(role);
	}

	
	
	

}

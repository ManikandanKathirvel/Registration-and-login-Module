package com.Mani.loginRepository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.Mani.LoginEntity.User;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User,Long>{
	
	 Optional<User> findByEmailAndPassword(String email, String password );
}

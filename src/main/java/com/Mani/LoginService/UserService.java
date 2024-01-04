package com.Mani.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.Mani.LoginEntity.User;
import com.Mani.loginRepository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userrepo;
	private final JdbcTemplate jdbcTemplate;
	 public UserService(JdbcTemplate jdbcTemplate) {
	        this.jdbcTemplate = jdbcTemplate;
	    }

	public User register(String name,String email,String password){
		if(name==null ||password==null) {
			return null;
		}
		else{
			User user=new User();
			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);
			return userrepo.save(user);
		}
		
		
	}
	public User auth( String email,String password) {
		
		return userrepo.findByEmailAndPassword(email, password).orElseThrow();
	}
	
	public boolean isUserAlreadyRegistered(String email) {
	
		 String query = "SELECT COUNT(*) FROM login_user WHERE email = ?";
	        int count = jdbcTemplate.queryForObject(query, Integer.class, email);

	        return count > 0;
	}
}

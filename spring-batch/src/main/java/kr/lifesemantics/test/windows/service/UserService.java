package kr.lifesemantics.test.windows.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.lifesemantics.test.windows.domain.User;
import kr.lifesemantics.test.windows.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public ArrayList<User> getAllUsers() {

		ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
		
		return users;
	}

}

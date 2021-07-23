package kr.lifesemantics.test.windows.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.lifesemantics.test.windows.domain.User;
import kr.lifesemantics.test.windows.service.UserService;

@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<User>> getUsers() {
		
		return ResponseEntity.ok(userService.getAllUsers());
		
	}

}

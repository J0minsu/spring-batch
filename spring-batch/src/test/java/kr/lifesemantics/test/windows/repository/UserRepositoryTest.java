package kr.lifesemantics.test.windows.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import kr.lifesemantics.test.windows.domain.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {
	
	@Autowired 
	private UserRepository userRepository;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void crudTest() {
		
		logger.info("START TOTAL TEST ==========================================");
		
		String msjoName = "ms.jo";
		String jsleeName = "js.lee";
		String nhchoiName = "nh.choi";
		
		HashSet<String> names = new HashSet<String>();
		names.add(msjoName);
		names.add(nhchoiName);
		names.add(jsleeName);
		
		User msjo = new User(msjoName, "01055253786");
		User jslee = new User(jsleeName, "01012345678");
		User nhchoi = new User(nhchoiName, "01023456789");
			
		userRepository.save(msjo);
		userRepository.save(jslee);
		userRepository.save(nhchoi);
		
		//제대로 들어갔는지 CREATE
		assertEquals(3, userRepository.count());
		
		List<User> users = (List<User>) userRepository.findAll();
		
		
		//제대로 수정되는지 UPDATE
		for(User user : users) {
			user.setName(user.getName() + "2");
			userRepository.save(user);
		}
		
		users = (List<User>) userRepository.findAll(); 
		
		for(User user : users) {
			String testName = user.getName();
			//
			assertEquals(names.contains(testName.substring(0, testName.length()-1)), true);
		}
		
		
		//제대로 삭제되는지 DELETE
		for(User user : users) {
			userRepository.delete(user);
		}
		
		assertEquals(0, userRepository.count());
		
		
		logger.info("FINISH TOTAL TEST ==========================================");
	}
	
}

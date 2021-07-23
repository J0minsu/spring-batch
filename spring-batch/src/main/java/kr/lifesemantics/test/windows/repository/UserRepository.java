package kr.lifesemantics.test.windows.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kr.lifesemantics.test.windows.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}

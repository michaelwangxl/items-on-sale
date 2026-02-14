package com.michael.test.repository;

import com.michael.test.domains.User;
import org.springframework.data.repository.Repository;

/**
 * @author michaelwang on 2021-03-14
 */
public interface UserRepository extends Repository<User, Integer> {
	public User findUserByUserId(Integer userId);
}

package com.michael.test.services;

import com.michael.test.domains.User;
import com.michael.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author michaelwang on 2021-03-10
 */
@Service
public class UserService {
  private UserRepository userRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findUserByUserId(Integer userId) {
    return userRepository.findUserByUserId(userId);
  }
}

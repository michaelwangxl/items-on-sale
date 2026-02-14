package com.michael.test.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.michael.test.domains.User;
import com.michael.test.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	private User testUser;

	@BeforeEach
	public void setUp() {
		testUser = new User(1, "testUser", "password123");
	}

	@Test
	public void testFindUserByUserIdWithValidId() {
		Integer userId = 1;

		when(userRepository.findUserByUserId(userId)).thenReturn(testUser);

		User result = userService.findUserByUserId(userId);

		assertNotNull(result);
		assertEquals(userId, result.getUserId());
		assertEquals("testUser", result.getUserName());
		assertEquals("password123", result.getPassWord());

		verify(userRepository, times(1)).findUserByUserId(userId);
	}

	@Test
	public void testFindUserByUserIdWithNonExistentId() {
		Integer userId = 999;

		when(userRepository.findUserByUserId(userId)).thenReturn(null);

		User result = userService.findUserByUserId(userId);

		assertNull(result);
		verify(userRepository, times(1)).findUserByUserId(userId);
	}

	@Test
	public void testFindUserByUserIdReturnsCorrectUserInfo() {
		Integer userId = 1;
		User expectedUser = new User(1, "michael", "pass456");

		when(userRepository.findUserByUserId(userId)).thenReturn(expectedUser);

		User result = userService.findUserByUserId(userId);

		assertNotNull(result);
		assertEquals("michael", result.getUserName());
		assertEquals("pass456", result.getPassWord());
	}

	@Test
	public void testFindUserByUserIdCallsRepositoryOnce() {
		Integer userId = 1;

		when(userRepository.findUserByUserId(userId)).thenReturn(testUser);

		userService.findUserByUserId(userId);

		verify(userRepository, times(1)).findUserByUserId(userId);
		verify(userRepository, times(1)).findUserByUserId(userId);
	}

	@Test
	public void testFindUserByUserIdWithDifferentUsers() {
		User user1 = new User(1, "user1", "pass1");
		User user2 = new User(2, "user2", "pass2");

		when(userRepository.findUserByUserId(1)).thenReturn(user1);
		when(userRepository.findUserByUserId(2)).thenReturn(user2);

		User result1 = userService.findUserByUserId(1);
		User result2 = userService.findUserByUserId(2);

		assertNotNull(result1);
		assertNotNull(result2);
		assertEquals("user1", result1.getUserName());
		assertEquals("user2", result2.getUserName());
	}

	@Test
	public void testUserServiceIsInstantiated() {
		assertNotNull(userService);
	}
}

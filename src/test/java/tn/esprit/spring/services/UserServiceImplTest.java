package tn.esprit.spring.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

   @InjectMocks
   private UserServiceImpl userService;

   @Mock
   private UserRepository userRepository;

   private User user;

   @BeforeEach
   void setUp() {
      user = new User();
      user.setId(1L);
      user.setLastName("Test User");  // Change this to "Test User"
      // Set other user properties as needed
   }

   @Test
   void testRetrieveAllUsers() {
      List<User> users = new ArrayList<>();
      users.add(user);
      when(userRepository.findAll()).thenReturn(users);

      List<User> result = userService.retrieveAllUsers();
      assertNotNull(result);
      assertEquals(1, result.size());
   }

   @Test
   void testAddUser() {
      when(userRepository.save(any(User.class))).thenReturn(user);

      User result = userService.addUser(user);
      assertNotNull(result);
      assertEquals("Test User", result.getLastName());  // This now matches
      verify(userRepository, times(1)).save(user);
   }

   @Test
   void testAddUserException() {
      when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

      User result = userService.addUser(user);
      assertNull(result);
   }

   @Test
   void testUpdateUser() {
      when(userRepository.save(any(User.class))).thenReturn(user);

      User result = userService.updateUser(user);
      assertNotNull(result);
      assertEquals("Test User", result.getLastName());  // This now matches
      verify(userRepository, times(1)).save(user);
   }

   @Test
   void testUpdateUserException() {
      when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

      User result = userService.updateUser(user);
      assertNull(result);
   }

   @Test
   void testDeleteUser() {
      doNothing().when(userRepository).deleteById(anyLong());

      assertDoesNotThrow(() -> userService.deleteUser("1"));
      verify(userRepository, times(1)).deleteById(1L);
   }

   @Test
   void testDeleteUserException() {
      doThrow(new RuntimeException("Database error")).when(userRepository).deleteById(anyLong());

      assertDoesNotThrow(() -> userService.deleteUser("1"));
      verify(userRepository, times(1)).deleteById(1L);
   }

   @Test
   void testRetrieveUser() {
      when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

      User result = userService.retrieveUser("1");
      assertNotNull(result);
      assertEquals("Test User", result.getLastName());  // This now matches
   }

   @Test
   void testRetrieveUserNotFound() {
      when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

      User result = userService.retrieveUser("1");
      assertNull(result);
   }
}

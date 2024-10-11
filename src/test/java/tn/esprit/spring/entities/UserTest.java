package tn.esprit.spring.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

 class UserTest {

   @Test
    void testUserConstructorAndGettersWithoutFirstName() {
      Date dateNaissance = new Date();
      Role role = Role.INGENIEUR; // Assuming you have a Role enum
      User user = new User(null, "Doe", dateNaissance, role); // No firstName

      assertEquals("Doe", user.getLastName());
      assertEquals(dateNaissance, user.getDateNaissance());
      assertEquals(role, user.getRole());
   }

   @Test
    void testSettersWithoutFirstName() {
      User user = new User();
      Date dateNaissance = new Date();
      Role role = Role.INGENIEUR; // Assuming you have a Role enum

      user.setLastName("Doe"); // No firstName
      user.setDateNaissance(dateNaissance);
      user.setRole(role);

      assertEquals("Doe", user.getLastName());
      assertEquals(dateNaissance, user.getDateNaissance());
      assertEquals(role, user.getRole());
   }

   @Test
    void testToStringWithoutFirstName() {
      Date dateNaissance = new Date();
      Role role = Role.INGENIEUR; // Assuming you have a Role enum
      User user = new User(null, null, "Doe", dateNaissance, role); // No firstName
      String expectedString = "User [id=null, firstName=null, lastName=Doe, dateNaissance=" + dateNaissance + ", role=" + role + "]";
      assertEquals(expectedString, user.toString());
   }
}

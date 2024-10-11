package tn.esprit.spring.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tn.esprit.spring.control.UserRestControl;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.services.IUserService;

class UserRestControlTest {

    @InjectMocks
    private UserRestControl userRestControl; // Inject mocks into the controller

    @Mock
    private IUserService userService; // Mock the service

    private MockMvc mockMvc; // MockMvc should not be @Autowired

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userRestControl).build();
    }

    @Test
    void testRetrieveAllUsers() throws Exception {
        User user1 = new User(1L, null, "Doe", new Date(), Role.INGENIEUR); // No firstName
        User user2 = new User(2L, null, "Smith", new Date(), Role.ADMINISTRATEUR); // No firstName
        List<User> users = Arrays.asList(user1, user2);

        when(userService.retrieveAllUsers()).thenReturn(users);

        mockMvc.perform(get("/user/retrieve-all-users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"));
    }

    @Test
    void testRetrieveUser() throws Exception {
        User user = new User(1L, null, "Doe", new Date(), Role.INGENIEUR); // No firstName

        when(userService.retrieveUser("1")).thenReturn(user);

        mockMvc.perform(get("/user/retrieve-user/{user-id}", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void testAddUser() throws Exception {
        User user = new User(null, null, "Doe", new Date(), Role.INGENIEUR); // No firstName
        when(userService.addUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/user/add-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":null,\"lastName\":\"Doe\",\"dateNaissance\":\"2024-01-01\",\"role\":\"INGENIEUR\"}")) // No firstName
                .andExpect(status().isOk()) // Expecting 200
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void testRemoveUser() throws Exception {
        doNothing().when(userService).deleteUser("1");

        mockMvc.perform(delete("/user/remove-user/{user-id}", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateUser() throws Exception {
        User user = new User(1L, null, "Doe", new Date(), Role.INGENIEUR); // No firstName
        when(userService.updateUser(any(User.class))).thenReturn(user);

        mockMvc.perform(put("/user/modify-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"firstName\":null,\"lastName\":\"Doe\",\"dateNaissance\":\"2024-01-01\",\"role\":\"INGENIEUR\"}")) // No firstName
                .andExpect(status().isOk()) // Expecting 200
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }
}

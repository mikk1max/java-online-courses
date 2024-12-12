package com.example.onlinecourses;

import com.example.onlinecourses.model.Student;
import com.example.onlinecourses.service.StudentService;
import com.example.onlinecourses.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    public void testGetRegister() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    public void testPostRegisterSuccess() throws Exception {
        Student student = new Student();
        student.setUsername("newUser");
        student.setPassword("password");

        when(userService.findByUsername("newUser")).thenReturn(null);

        mockMvc.perform(post("/register")
                        .param("username", "newUser")
                        .param("password", "password")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }

    @Test
    public void testPostRegisterError() throws Exception {
        Student student = new Student();
        student.setUsername("existingUser");
        student.setPassword("password");

        when(userService.findByUsername("existingUser")).thenReturn(student);

        mockMvc.perform(post("/register")
                        .param("username", "existingUser")
                        .param("password", "password")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/register?error"));
    }
}

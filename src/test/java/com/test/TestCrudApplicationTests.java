package com.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TestCrudApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Иван")))
                .andExpect(jsonPath("$[1].name", is("Надежда")));
    }


    @Test
    void testCreate() throws Exception {
        User employee = new User("Игорь", 35);
        String employeeJson = objectMapper.writeValueAsString(employee);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Игорь")))
                .andExpect(jsonPath("$.age", is(35)));
    }

    @Test
    void testUpdate() throws Exception {
        User employee = new User("Иван", 40);
        String employeeJson = objectMapper.writeValueAsString(employee);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Иван")))
                .andExpect(jsonPath("$.age", is(40)));
    }


    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());
    }


}

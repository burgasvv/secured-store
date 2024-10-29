package org.burgas.employeeservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class PositionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void handleGetAllPosition() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/positions")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        result -> {
                            MockHttpServletResponse response = result.getResponse();
                            response.setCharacterEncoding("UTF-8");
                            System.out.println(response.getContentAsString());
                        }
                )
                .andReturn();
    }

    @Test
    void handleGetPositionById() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/positions/3")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        result -> {
                            MockHttpServletResponse response = result.getResponse();
                            response.setCharacterEncoding("UTF-8");
                            System.out.println(response.getContentAsString());
                        }
                )
                .andReturn();
    }

    @Test
    void handleCreatePosition() throws Exception {

        //language=JSON
        String json = """
                {
                  "id": "",
                  "name": "Главный продавец-консультант"
                }""";

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/positions/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        result -> {
                            MockHttpServletResponse response = result.getResponse();
                            response.setCharacterEncoding("UTF-8");
                            System.out.println(response.getContentAsString());
                        }
                )
                .andReturn();
    }

    @Test
    void handleUpdatePosition() throws Exception {

        //language=JSON
        String json = """
                {
                  "id": "3",
                  "name": "Продавец-консультант"
                }""";

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/positions/edit")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        result -> {
                            MockHttpServletResponse response = result.getResponse();
                            response.setCharacterEncoding("UTF-8");
                            System.out.println(response.getContentAsString());
                        }
                )
                .andReturn();
    }

    @Test
    void handleDeletePosition() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/positions/delete/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        result -> {
                            MockHttpServletResponse response = result.getResponse();
                            response.setCharacterEncoding("UTF-8");
                            System.out.println(response.getContentAsString());
                        }
                )
                .andReturn();
    }
}
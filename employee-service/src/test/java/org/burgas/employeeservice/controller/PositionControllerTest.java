package org.burgas.employeeservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class PositionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void handleGetAllPosition() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/positions")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        String contentAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(contentAsString);
    }

    @Test
    void handleGetPositionById() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/positions/3")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        String contentAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(contentAsString);
    }

    @Test
    void handleCreatePosition() throws Exception {

        //language=JSON
        String json = """
                {
                  "id": "",
                  "name": "Главный продавец-консультант"
                }""";

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/positions/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        String contentAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(contentAsString);
    }

    @Test
    void handleUpdatePosition() throws Exception {

        //language=JSON
        String json = """
                {
                  "id": "3",
                  "name": "Продавец-консультант"
                }""";

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put("/positions/edit")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        String contentAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(contentAsString);
    }

    @Test
    void handleDeletePosition() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.delete("/positions/delete/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        String contentAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(contentAsString);
    }
}
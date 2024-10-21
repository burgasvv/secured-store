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
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void handleGetAllEmployees() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        String contentAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(contentAsString);
    }

    @Test
    void handleGetEmployee() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/employees/3")
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        String contentAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(contentAsString);
    }

    @Test
    void handleCreateEmployee() throws Exception {

        //language=JSON
        String json = """
                {
                  "id": "",
                  "firstName": "Vyacheslav",
                  "lastName": "Burgas",
                  "patronymic": "Vasilich",
                  "email": "burgasvv@gmail.com",
                  "positionId": "3",
                  "storeId": "3"
                }""";

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/employees/create")
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
    void handleEditEmployee() throws Exception {

        //language=JSON
        String json = """
                {
                  "id": "2",
                  "firstName": "Danil",
                  "lastName": "Kugrashov",
                  "patronymic": "Sergeevich",
                  "email": "kugrashov@gmail.com",
                  "positionId": "2",
                  "storeId": "2"
                }""";

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put("/employees/edit")
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
    void handleDeleteEmployee() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.delete("/employees/delete/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        String contentAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(contentAsString);
    }
}
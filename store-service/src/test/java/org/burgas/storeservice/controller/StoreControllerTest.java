package org.burgas.storeservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class StoreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void handleGetAllStores() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/stores")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println(contentAsString);

        assertThat(contentAsString)
                .isNotEmpty()
                .contains("id", "name", "address");
    }

    @Test
    void handleCreateStore() throws Exception {

        //language=JSON
        String json = """
                {
                  "id": "",
                  "name": "Магазин на Октябрьской",
                  "address": "Октябрьская 76"
                }""";

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/stores/create")
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
    void handleEditStore() throws Exception {

        //language=JSON
        String json = """
                {
                  "id": "2",
                  "name": "Магазин в Дзержинском районе",
                  "address": "Сибревкома 81"
                }""";

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put("/stores/edit")
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
    void handleDeleteStore() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/stores/delete")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("storeId", "2")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        result -> {
                            result.getResponse().setCharacterEncoding("UTF-8");
                            System.out.println(result.getResponse().getContentAsString());
                        }
                ).andReturn();
    }
}
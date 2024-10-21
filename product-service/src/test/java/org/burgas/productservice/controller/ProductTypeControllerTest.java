package org.burgas.productservice.controller;

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
class ProductTypeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void handleGetAllProductTypes() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/product-types")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        String contentAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(contentAsString);
    }

    @Test
    void handleGetProductTypeById() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/product-types/3")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        String contentAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(contentAsString);
    }

    @Test
    void handleCreateProductType() throws Exception {

        //language=JSON
        String json = """
                {
                  "id": 0,
                  "name": "Мониторы"
                }
        """;

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/product-types/create")
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
    void handleUpdateProductType() throws Exception {

        //language=JSON
        String json = """
                {
                  "id": 1,
                  "name": "Видеокарты AMD NVIDIA"
                }
        """;

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put("/product-types/edit")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        String contentAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(contentAsString);
    }
}
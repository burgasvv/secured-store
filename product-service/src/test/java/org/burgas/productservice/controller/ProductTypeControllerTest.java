package org.burgas.productservice.controller;

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
class ProductTypeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void handleGetAllProductTypes() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/product-types")
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
    void handleGetProductTypeById() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/product-types/3")
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
    void handleCreateProductType() throws Exception {

        //language=JSON
        String json = """
                {
                  "id": 0,
                  "name": "Мониторы"
                }
        """;

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/product-types/create")
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
    void handleUpdateProductType() throws Exception {

        //language=JSON
        String json = """
                {
                  "id": 1,
                  "name": "Видеокарты AMD NVIDIA"
                }
        """;

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/product-types/edit")
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
}
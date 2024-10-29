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
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void handleGetAllProducts() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products")
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
    void handleGetProductById() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/3")
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
    void handleCreateProduct() throws Exception {

        //language=JSON
        String json = """
                {
                  "id": 0,
                  "name": "AMD 5700",
                  "amount": 70,
                  "price": 250000,
                  "description": "New Radeon video card",
                  "productTypeResponse": {
                    "id": 1,
                    "name": "Видеокарты"
                  },
                  "storeResponses": [
                    {
                      "id": 1,
                      "name": "Магазин на Ленина",
                      "address": "Ленина 65",
                      "productAmount": 30
                    },
                    {
                      "id": 2,
                      "name": "Магазин на Красном",
                      "address": "Красный проспект 99",
                      "productAmount": 40
                    }
                  ]
                }""";

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/products/create")
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
    void handleUpdateProduct() throws Exception {

        //language=JSON
        String json = """
                {
                  "id": 1,
                  "name": "AMD 6000",
                  "amount": 50,
                  "price": 220000,
                  "description": "New Radeon video card",
                  "productTypeResponse": {
                    "id": 1,
                    "name": "Видеокарты"
                  },
                  "storeResponses": [
                    {
                      "id": 1,
                      "name": "Магазин на Ленина",
                      "address": "Ленина 65",
                      "productAmount": 25
                    },
                    {
                      "id": 2,
                      "name": "Магазин на Красном",
                      "address": "Красный проспект 99",
                      "productAmount": 25
                    }
                  ]
                }""";

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/products/edit")
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
    void handleDeleteProduct() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/products/delete/3")
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
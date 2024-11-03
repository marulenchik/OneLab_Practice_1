package practice.controller;

import practice.service.JMSKafkaProducer;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private JMSKafkaProducer producer;

    @InjectMocks
    private ProductController productController;

    @Test
    void testAddProductEndpoint() throws Exception {
        // Arrange
        String name = "NewProduct";
        String description = "A description of the new product";

        // Act and Assert
        mockMvc.perform(post("/products/add")
                        .param("name", name)
                        .param("description", description))
                .andExpect(status().isOk());

        // Verify that producer's send method was called with correct parameters
        verify(producer).sendNewProductNotification(name, description);
    }
}
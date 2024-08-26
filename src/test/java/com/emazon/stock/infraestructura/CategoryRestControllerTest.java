package com.emazon.stock.infraestructura;

import static com.emazon.stock.dominio.constants.GlobalConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import com.emazon.stock.aplicacion.dtos.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.CategoryResponse;
import com.emazon.stock.aplicacion.handler.ICategoryHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoryHandler categoryHandler;

    @Autowired
    private ObjectMapper objectMapper;
    private CategoryResponse categoryResponse;
    private Page<CategoryResponse> categoryResponsePage;

    @BeforeEach
    void setup() {
        categoryResponse = new CategoryResponse(VALID_ID, VALID_NAME, VALID_DESCRIPTION);
    }

    @Test
    @DisplayName("Should Save Category Successfully")
    void testSaveCategory() throws Exception {
        doNothing().when(categoryHandler).saveCategory(any(CategoryRequest.class));
        CategoryRequest categoryRequest = new CategoryRequest(VALID_ID, VALID_NAME, VALID_DESCRIPTION);
        mockMvc.perform(post("/category/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should Return List of Categories")
    void testGetCategories() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        categoryResponsePage = new PageImpl<>(List.of(categoryResponse), pageable, 1);
        when(categoryHandler.getCategories("ASC", 0, 10)).thenReturn(categoryResponsePage);
        mockMvc.perform(get("/category")
                .param("sortDirection", "ASC")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(VALID_ID))
                .andExpect(jsonPath("$.content[0].name").value(VALID_NAME))
                .andExpect(jsonPath("$.content[0].description").value(VALID_DESCRIPTION));
    }

    @Test
    @DisplayName("Should Retrieve Category By ID")
    void testGetCategoryById() throws Exception {
        when(categoryHandler.getCategory(VALID_ID)).thenReturn(categoryResponse);
        mockMvc.perform(get("/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(VALID_ID))
                .andExpect(jsonPath("$.name").value(VALID_NAME))
                .andExpect(jsonPath("$.description").value(VALID_DESCRIPTION));
    }
}

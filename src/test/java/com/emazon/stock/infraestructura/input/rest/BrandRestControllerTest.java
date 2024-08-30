package com.emazon.stock.infraestructura.input.rest;

import com.emazon.stock.aplicacion.dtos.BrandRequest;
import com.emazon.stock.aplicacion.dtos.BrandResponse;
import com.emazon.stock.aplicacion.handler.IBrandHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
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

import static com.emazon.stock.constants.TestConstants.VALID_PAGE;
import static com.emazon.stock.constants.TestConstants.VALID_ID;
import static com.emazon.stock.constants.TestConstants.VALID_SIZE;
import static com.emazon.stock.constants.TestConstants.VALID_BRAND_NAME;
import static com.emazon.stock.constants.TestConstants.VALID_BRAND_DESCRIPTION;
import static com.emazon.stock.constants.TestConstants.VALID_TOTAL_ELEMENTS;
import static com.emazon.stock.dominio.utils.ConstantsDominio.DIRECTION_ASC;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BrandRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBrandHandler brandHandler;

    @Autowired
    private ObjectMapper objectMapper;
    private BrandResponse brandResponse;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        brandResponse = new BrandResponse(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
    }

    @Test
    @DisplayName("Should Save brand Successfully")
    void testSaveBrand() throws Exception {
        doNothing().when(brandHandler).saveBrand(any(BrandRequest.class));
        BrandRequest brandRequest = new BrandRequest(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
        mockMvc.perform(post("/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(status().isCreated());
    }
    @Test
    @DisplayName("Should Retrieve brand By ID")
    void testGetBrandById() throws Exception {
        when(brandHandler.getBrand(VALID_ID)).thenReturn(brandResponse);
        mockMvc.perform(get("/brand/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(VALID_ID))
                .andExpect(jsonPath("$.name").value(VALID_BRAND_NAME))
                .andExpect(jsonPath("$.description").value(VALID_BRAND_DESCRIPTION));
    }

    @Test
    @DisplayName("Should Return List of Brands")
    void testGetBrands() throws Exception {
        Pageable pageable = PageRequest.of(VALID_PAGE,VALID_SIZE);
        Page<BrandResponse> brandResponsePage = new PageImpl<>(List.of(brandResponse), pageable, VALID_TOTAL_ELEMENTS);
        when(brandHandler.getBrandsByName(VALID_PAGE, VALID_SIZE, DIRECTION_ASC)).thenReturn(brandResponsePage);
        mockMvc.perform(get("/brand")
                        .param("sortDirection", DIRECTION_ASC)
                        .param("page", Integer.toString(VALID_PAGE))
                        .param("size", Integer.toString(VALID_SIZE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(VALID_ID))
                .andExpect(jsonPath("$.content[0].name").value(VALID_BRAND_NAME))
                .andExpect(jsonPath("$.content[0].description").value(VALID_BRAND_DESCRIPTION));
    }


}
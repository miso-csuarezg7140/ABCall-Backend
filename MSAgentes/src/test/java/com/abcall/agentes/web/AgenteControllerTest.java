package com.abcall.agentes.web;

import com.abcall.agentes.domain.dto.AgenteDto;
import com.abcall.agentes.domain.dto.ResponseServiceDto;
import com.abcall.agentes.domain.service.AgenteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.abcall.agentes.util.Constant.CODIGO_200;
import static com.abcall.agentes.util.Constant.CODIGO_206;
import static com.abcall.agentes.util.Constant.CODIGO_400;
import static com.abcall.agentes.util.Constant.CODIGO_500;
import static com.abcall.agentes.util.Constant.MENSAJE_200;
import static com.abcall.agentes.util.Constant.MENSAJE_206;
import static com.abcall.agentes.util.Constant.MENSAJE_500;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AgenteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AgenteService agenteService;

    @InjectMocks
    private AgenteController agenteController;

    private static final String TIPO_DOCUMENTO = "CC";
    private static final String NUMERO_DOCUMENTO = "123456789";
    private static final String CONTRASENA = "Password123!";
    private static final String BASE_URL = "/login";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(agenteController)
                .setControllerAdvice(new AdviceController())
                .build();
    }

    @Test
    void loginSuccessful() throws Exception {
        // Arrange
        ResponseServiceDto response = new ResponseServiceDto();
        response.setStatusCode(CODIGO_200);
        response.setStatusDescription(MENSAJE_200);
        response.setData(new AgenteDto());

        when(agenteService.login(TIPO_DOCUMENTO, NUMERO_DOCUMENTO, CONTRASENA))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(get(BASE_URL)
                        .param("tipoDocumentoAgente", TIPO_DOCUMENTO)
                        .param("numDocumentoAgente", NUMERO_DOCUMENTO)
                        .param("contrasena", CONTRASENA)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(CODIGO_200))
                .andExpect(jsonPath("$.statusDescription").value(MENSAJE_200));
    }

    @Test
    void loginPartialContent() throws Exception {
        // Arrange
        ResponseServiceDto response = new ResponseServiceDto();
        response.setStatusCode(CODIGO_206);
        response.setStatusDescription(MENSAJE_206);

        when(agenteService.login(TIPO_DOCUMENTO, NUMERO_DOCUMENTO, CONTRASENA))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(get(BASE_URL)
                        .param("tipoDocumentoAgente", TIPO_DOCUMENTO)
                        .param("numDocumentoAgente", NUMERO_DOCUMENTO)
                        .param("contrasena", CONTRASENA)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPartialContent())
                .andExpect(jsonPath("$.statusCode").value(CODIGO_206))
                .andExpect(jsonPath("$.statusDescription").value(MENSAJE_206));
    }

    @Test
    void loginBadRequest() throws Exception {
        // Arrange
        ResponseServiceDto response = new ResponseServiceDto();
        response.setStatusCode(CODIGO_400);
        response.setStatusDescription("Bad Request");

        when(agenteService.login(TIPO_DOCUMENTO, NUMERO_DOCUMENTO, CONTRASENA))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(get(BASE_URL)
                        .param("tipoDocumentoAgente", TIPO_DOCUMENTO)
                        .param("numDocumentoAgente", NUMERO_DOCUMENTO)
                        .param("contrasena", CONTRASENA)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(CODIGO_400));
    }

    @Test
    void loginInternalServerError() throws Exception {
        // Arrange
        ResponseServiceDto response = new ResponseServiceDto();
        response.setStatusCode(CODIGO_500);
        response.setStatusDescription(MENSAJE_500);

        when(agenteService.login(anyString(), anyString(), anyString()))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(get(BASE_URL)
                        .param("tipoDocumentoAgente", TIPO_DOCUMENTO)
                        .param("numDocumentoAgente", NUMERO_DOCUMENTO)
                        .param("contrasena", CONTRASENA)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.statusCode").value(CODIGO_500))
                .andExpect(jsonPath("$.statusDescription").value(MENSAJE_500));
    }

    @Test
    void pingTest() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string("pong"));
    }
}

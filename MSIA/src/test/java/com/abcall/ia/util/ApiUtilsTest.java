package com.abcall.ia.util;

import com.abcall.ia.domain.dto.response.ResponseServiceDto;
import com.abcall.ia.util.enums.HttpResponseCodes;
import com.abcall.ia.util.enums.HttpResponseMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ApiUtilsTest {

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ApiUtils apiUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void badRequestResponseShouldReturnValidationErrors() {
        FieldError fieldError1 = new FieldError("objectName", "field1", "error1");
        FieldError fieldError2 = new FieldError("objectName", "field2", "error2");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        ResponseServiceDto response = apiUtils.badRequestResponse(bindingResult);

        assertEquals(HttpResponseCodes.BAD_REQUEST.getCode(), response.getStatusCode());
        assertEquals(HttpResponseMessages.BAD_REQUEST.getMessage(), response.getStatusDescription());
        Map<String, String> expectedErrors = Map.of("field1", "error1", "field2", "error2");
        assertEquals(expectedErrors, response.getData());
    }

    @Test
    void buildResponseShouldReturnResponseWithPaginationWhenStatusCodeIsOk() {
        Object response = "response";
        Object pagination = "pagination";

        ResponseServiceDto result = apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                HttpResponseMessages.OK.getMessage(), response, pagination);

        assertEquals(HttpResponseCodes.OK.getCode(), result.getStatusCode());
        assertEquals(HttpResponseMessages.OK.getMessage(), result.getStatusDescription());
        Map<String, Object> expectedData = Map.of(Constants.RESPONSE_VALUE, response, Constants.PAGINATION_VALUE,
                pagination);
        assertEquals(expectedData, result.getData());
    }

    @Test
    void buildResponseShouldReturnResponseWithoutPaginationWhenStatusCodeIsNotOk() {
        Object response = "response";

        ResponseServiceDto result = apiUtils.buildResponse(HttpResponseCodes.BAD_REQUEST.getCode(),
                HttpResponseMessages.BAD_REQUEST.getMessage(), response, null);

        assertEquals(HttpResponseCodes.BAD_REQUEST.getCode(), result.getStatusCode());
        assertEquals(HttpResponseMessages.BAD_REQUEST.getMessage(), result.getStatusDescription());
        assertEquals(response, result.getData());
    }

    @Test
    void buildResponseShouldReturnResponseWithGivenData() {
        Object response = "response";

        ResponseServiceDto result = apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                HttpResponseMessages.OK.getMessage(), response);

        assertEquals(HttpResponseCodes.OK.getCode(), result.getStatusCode());
        assertEquals(HttpResponseMessages.OK.getMessage(), result.getStatusDescription());
        assertEquals(response, result.getData());
    }
}
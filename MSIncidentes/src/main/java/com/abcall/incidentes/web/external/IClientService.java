package com.abcall.incidentes.web.external;

import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "clientes", url = "${client.service.url}")
public interface IClientService {

    @GetMapping("/validateUserClient")
    ResponseEntity<ResponseServiceDto> validateUserClient(@RequestParam Long documentClient,
                                                          @RequestParam String documentTypeUser,
                                                          @RequestParam Long documentUser);
}

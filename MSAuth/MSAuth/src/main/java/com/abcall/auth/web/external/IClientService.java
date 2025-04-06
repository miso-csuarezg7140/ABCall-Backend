package com.abcall.auth.web.external;

import com.abcall.auth.domain.dto.request.ClientAuthRequest;
import com.abcall.auth.domain.dto.response.ResponseServiceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "client-service", url = "${client.service.url}")
public interface IClientService {

    @PostMapping("/authenticate")
    ResponseEntity<ResponseServiceDto> authenticateClient(@RequestBody ClientAuthRequest clientAuthRequest);
}

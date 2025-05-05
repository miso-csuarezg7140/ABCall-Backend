package com.abcall.auth.web.external;

import com.abcall.auth.domain.dto.request.AgentAuthRequest;
import com.abcall.auth.domain.dto.response.ResponseServiceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "agent-service", url = "${agent.service.url}")
public interface IAgentService {

    @PostMapping("/autenticar")
    ResponseEntity<ResponseServiceDto> authenticateAgent(@RequestBody AgentAuthRequest agentAuthRequest);
}

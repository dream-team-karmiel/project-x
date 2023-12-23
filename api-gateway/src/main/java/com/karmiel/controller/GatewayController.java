package com.karmiel.controller;

import com.karmiel.service.ProxyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GatewayController {
    private final ProxyService service;

    @GetMapping("api/v1/**")
    ResponseEntity<?> getOffice(ProxyExchange<byte[]> proxy, HttpServletRequest request) {
        log.trace("Receive get query with URI {}, received query string {}", request.getRequestURI(), request.getQueryString());
        return service.proxyRoutingGet(proxy, request);
    }

    @RequestMapping(path = "api/v1/**", method = {RequestMethod.POST, RequestMethod.PATCH})
    ResponseEntity<?> postOffice(
            ProxyExchange<byte[]> proxy,
            HttpServletRequest request,
            @RequestBody(required = false) String body) {
        log.trace("Receive post query with URI {}, receive query string {}", request.getRequestURI(), request.getQueryString());
        return service.proxyRoutingPost(proxy, request, body);
    }
}

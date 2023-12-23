package com.karmiel.gateway.controllers;

import com.karmiel.gateway.service.ProxyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GatewayController {
    final ProxyService proxyService;
    ResponseEntity<byte[]> getResult(ProxyExchange<byte[]> proxy, HttpServletRequest request){
        return null;
    }
}

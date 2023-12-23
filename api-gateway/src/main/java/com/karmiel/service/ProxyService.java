package com.karmiel.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;

public interface ProxyService {
    ResponseEntity<byte[]> proxyRoutingGet(ProxyExchange<byte[]> proxy, HttpServletRequest request);

    ResponseEntity<byte[]> proxyRoutingPost(ProxyExchange<byte[]> proxy, HttpServletRequest request, String body);
}

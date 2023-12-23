package com.karmiel.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProxyServiceImpl implements ProxyService {
    @Value("#{${com.karmiel.routed.urls}}")
    private Map<String, String> urlsMap;

    @Override
    public ResponseEntity<byte[]> proxyRoutingGet(ProxyExchange<byte[]> proxy, HttpServletRequest request) {
        String routedURI = getRoutedURI(request);
        log.trace("Routed URI {}", routedURI);
        return proxy.uri(routedURI).get();
    }

    @Override
    public ResponseEntity<byte[]> proxyRoutingPost(ProxyExchange<byte[]> proxy, HttpServletRequest request, String body) {
        String routedURI = getRoutedURI(request);
        log.trace("Routed URI {}", routedURI);
        return proxy.uri(routedURI).body(body).post();
    }

    private String getRoutedURI(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String mainURN = requestURI.split("/+")[2];
        log.trace("Main URN {}", mainURN);
        if (!urlsMap.containsKey(mainURN)) {
            throw new IllegalArgumentException(mainURN + " not found");
        }
        String routedURL = urlsMap.get(mainURN);
        log.trace("Routed url {}", routedURL);
        String queryString = request.getQueryString();
        if (queryString == null)
            queryString = "";
        return String.format("%s%s?%s", routedURL, requestURI, queryString);
    }
}

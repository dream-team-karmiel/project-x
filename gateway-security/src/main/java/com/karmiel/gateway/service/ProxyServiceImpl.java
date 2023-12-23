package com.karmiel.gateway.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProxyServiceImpl implements ProxyService{
    @Value("#{${app.routed.urls}}")
    Map<String, String> serviceURLs;

    @Override
    public ResponseEntity<byte[]> proxyRouting(ProxyExchange<byte[]> proxy, HttpServletRequest request){
        String requestUri = request.getRequestURI();
        String uri1 = requestUri.split("/+")[0];
        if(!serviceURLs.containsKey(uri1))
            throw new IllegalArgumentException(uri1 + " Not found");
        String serviceUrl = serviceURLs.get(uri1);
        String queryString = request.getQueryString();
        if (queryString == null)
            queryString = "";
        String routedServiceUrl = String.format("%s%s%s", serviceUrl, requestUri, queryString);
        return proxy.uri(routedServiceUrl).get();
    }
}

package com.sharmait.apigateway.references;

import com.mettl.mpaasgateway.services.ClientService;
import com.mettl.mpaasgateway.utils.JsonConverter;
import com.mettl.mpaasgatewayclientmodel.reponse.beans.ClientTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ClientVerifyFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return null;
    }

   /* @Autowired
    private ClientService clientService;

    @Autowired
    private JsonConverter jsonConverter;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ClientTokenInfo clientInfo = (ClientTokenInfo) exchange.getAttributes().get("clientInfo");
        Integer clientId = clientInfo.getClientId();
        return clientService.getClientSettings(clientId).flatMap(clientSettings -> {
            clientService.validateClientAccount(clientSettings);
            return chain.filter(exchange);
        });
    }*/
}

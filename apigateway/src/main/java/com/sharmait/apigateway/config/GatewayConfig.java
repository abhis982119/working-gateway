package com.sharmait.apigateway.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.function.Supplier;

@Configuration
public class GatewayConfig {


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
      return   builder.routes()
                .route( r-> r.path("/health/**")
                        .uri("http://localhost:8081/")

                ).
              route(r->r.path("/orderServcie/**")
                      .uri("http://localhost:8082/")
              ).

              build();
    }




    @PostConstruct
    public void postConstruct(){
        System.out.println("*********");
    }
}

package com.ksef.api_gateway.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends
        AbstractGatewayFilterFactory<Object> {

    private final Map<String, Bucket>
            cache = new ConcurrentHashMap<>();

    private Bucket createBucket() {

        return Bucket.builder()
                .addLimit(
                        Bandwidth.classic(
                                10,
                                Refill.greedy(
                                        10,
                                        Duration.ofMinutes(1)
                                )
                        )
                )
                .build();
    }

    @Override
    public GatewayFilter apply(
            Object config
    ) {

        return (exchange, chain) -> {

            String ip =
                    exchange
                            .getRequest()
                            .getRemoteAddress()
                            .getAddress()
                            .getHostAddress();

            Bucket bucket =
                    cache.computeIfAbsent(
                            ip,
                            k -> createBucket()
                    );

            if(bucket.tryConsume(1)){

                return chain.filter(exchange);
            }

            exchange.getResponse()
                    .setStatusCode(
                            HttpStatus.TOO_MANY_REQUESTS
                    );

            return exchange
                    .getResponse()
                    .setComplete();
        };
    }
}
package com.panerai.gateway.route.service;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

import com.panerai.gateway.route.entity.GatewayDefine;
import com.panerai.gateway.route.repository.GatewayDefineRepository;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class GatewayDefineService implements ApplicationEventPublisherAware {
    @Autowired
    private GatewayDefineRepository gatewayDefineRepository;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    public List<GatewayDefine> findAll() {
        return gatewayDefineRepository.findAll();
    }

    public String loadRouteDefinition() {
        List<GatewayDefine> gatewayDefineList = findAll();

        if (CollectionUtils.isEmpty(gatewayDefineList)) {
            throw new RuntimeException("没有任何配置，无法启动");
        }

        try {
            for (GatewayDefine define : gatewayDefineList) {
                RouteDefinition definition = new RouteDefinition();

                definition.setId(define.getId());
                definition.setUri(new URI(define.getUri()));
                List<PredicateDefinition> predicateDefinitions = define.getPredicateDefinition();
                if (CollectionUtils.isEmpty(predicateDefinitions)) {
                    throw new RuntimeException("predicate must not be empty");
                }

                Map<String, Object> timeoutMap = new HashMap<>();
                timeoutMap.put(RESPONSE_TIMEOUT_ATTR, define.getTimeout() * 1000);
                timeoutMap.put(CONNECT_TIMEOUT_ATTR, define.getTimeout() * 1000);
                definition.setMetadata(timeoutMap);
                PredicateDefinition methodPredicate = define.buildMethodPredicate();
                if (methodPredicate != null) {
                    predicateDefinitions.add(methodPredicate);
                }
                definition.setPredicates(predicateDefinitions);
                List<FilterDefinition> filterDefinitions = define.getFilterDefinition();
                if (CollectionUtils.isNotEmpty(filterDefinitions)) {
                    definition.setFilters(filterDefinitions);
                }
                routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            }
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "failure";
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        publisher = applicationEventPublisher;
    }
}

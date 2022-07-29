package com.panerai.gateway.route.service;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

import com.panerai.gateway.route.entity.GatewayDefine;
import com.panerai.gateway.route.repository.GatewayDefineRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
public class GatewayDefineService implements ApplicationEventPublisherAware {

  private static Logger log = LoggerFactory.getLogger(GatewayDefineService.class);

  @Autowired
  private GatewayDefineRepository gatewayDefineRepository;

  @Autowired
  private InMemoryRouteDefinitionRepository routeDefinitionWriter;

  private ApplicationEventPublisher publisher;

  public String loadRouteDefinition() {
    List<GatewayDefine> gatewayDefineList = gatewayDefineRepository.findAll();

    if (CollectionUtils.isEmpty(gatewayDefineList)) {
      throw new RuntimeException("没有任何配置，无法启动");
    }

    try {
      for (GatewayDefine define : gatewayDefineList) {
        addRouteDefinition(define);
      }
      this.publisher.publishEvent(new RefreshRoutesEvent(this));
      return "success";
    } catch (Exception e) {
      log.info("something wrong", e);
      return "failure";
    }
  }

  public void addSingleRoute(GatewayDefine define) {
    addRouteDefinition(define);

    this.publisher.publishEvent(new RefreshRoutesEvent(this));
  }

  private void addRouteDefinition(GatewayDefine define) {
    RouteDefinition definition = new RouteDefinition();

    definition.setId(define.getId());
    definition.setUri(UriComponentsBuilder.fromUriString(define.getUri()).build().toUri());
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

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    publisher = applicationEventPublisher;
  }

  public void deleteAll() {
    routeDefinitionWriter.getRouteDefinitions()
        .subscribe(route -> {
          routeDefinitionWriter.delete(Mono.just(route.getId())).subscribe();
          log.info("delete route: {}.", route.getId());
        });

    this.publisher.publishEvent(new RefreshRoutesEvent(this));
  }
}

package com.panerai.gateway.route.controller;

import com.panerai.gateway.route.entity.GatewayDefine;
import com.panerai.gateway.route.service.GatewayDefineService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/route")
public class RouteController {

  @Autowired
  private InMemoryRouteDefinitionRepository routeDefinitionLocator;

  @Autowired
  private GatewayDefineService defineService;

  @PostMapping("/routes")
  public void addRoutes() {
    GatewayDefine define = new GatewayDefine();
    define.setId(RandomStringUtils.randomAlphabetic(6));
    define.setUri("http://localhost:8091");
    define.setPredicates("Path:/authors/**");
    define.setTimeout(RandomUtils.nextInt(10, 100));
    defineService.addSingleRoute(define);
  }

  @GetMapping("/deleteAll")
  public void deleteAll() {
    defineService.deleteAll();
  }

  //获取网关所有的路由信息
  @RequestMapping("/routes")
  public Flux<RouteDefinition> getRouteDefinitions() {
    return routeDefinitionLocator.getRouteDefinitions();
  }
}

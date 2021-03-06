package com.panerai.gateway;

import com.panerai.gateway.route.service.GatewayDefineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationRunner {
    @Autowired
    private GatewayDefineService gatewayDefineService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        gatewayDefineService.loadRouteDefinition();
    }
}

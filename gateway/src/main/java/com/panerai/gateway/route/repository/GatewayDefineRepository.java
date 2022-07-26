package com.panerai.gateway.route.repository;

import com.panerai.gateway.route.entity.GatewayDefine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatewayDefineRepository extends JpaRepository<GatewayDefine, String> {
}

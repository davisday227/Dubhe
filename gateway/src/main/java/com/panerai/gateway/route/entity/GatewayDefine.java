package com.panerai.gateway.route.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "gateway_define")
public class GatewayDefine {
    @Id
    private String id;

    private String uri;
    // 暂时定义的格式 aaaa:aaaa, bbb:bbbb; ccc:cccc, ddd: dddd
    private String predicates;
    private String filters;

    public List<PredicateDefinition> getPredicateDefinition() {
        if (StringUtils.isNotBlank(this.predicates)) {
            return Arrays.asList(this.predicates.split(";")).stream()
                    .map(x -> buildPredicateDefinition(x))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public List<FilterDefinition> getFilterDefinition() {
        if (StringUtils.isNotBlank(this.filters)) {
            return Arrays.asList(this.filters.split(";")).stream().map(x -> buildFilter(x)).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    private FilterDefinition buildFilter(String filterStr) {
        // TODO: 2022/5/25 待实现
        return new FilterDefinition();
    }

    private PredicateDefinition buildPredicateDefinition(String definitionStr) {
        PredicateDefinition definition = new PredicateDefinition();
        definition.setName("Path"); // TODO: 2022/5/25 暂时写死
        Map<String, String> args = new HashMap<>();
        Arrays.asList(definitionStr.split(",")).stream().forEach(x -> {
            String[] split = x.split(":");
            args.put(split[0], split[1]);
        });
        definition.setArgs(args);
        return definition;
    }
}

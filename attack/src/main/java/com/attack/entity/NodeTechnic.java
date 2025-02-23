package com.attack.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Builder
@Node("Technic")
public class NodeTechnic {

    @Id
    @GeneratedValue
    Long id0;

    @Property(name = "id")
    private String id;

    @Property(name = "name")
    private String name;

    @Property(name = "description")
    private String des;
}


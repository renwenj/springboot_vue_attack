package com.attack.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Data
@Builder
@Node("Mitigation")
public class NodeMitigation {
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

package com.attack.entity;

import lombok.Data;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.GeneratedValue;


@Data
@Node("Mitigation")
public class NodeMitigation {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;
}


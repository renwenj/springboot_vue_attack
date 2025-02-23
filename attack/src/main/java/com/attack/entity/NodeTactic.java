package com.attack.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;

@Data
@Builder
@Node("Tactic")
public class NodeTactic {
    @Id
    @GeneratedValue
    Long id0;

    @Property(name = "id")
    private String id;

    @Property(name = "name")
    private String name;

    @Property(name = "description")
    private String des;

    @Relationship(type = "技术", direction = Relationship.Direction.OUTGOING)
    private List<TechnicRelation> technicRelation;
}


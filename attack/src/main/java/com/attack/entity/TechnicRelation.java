package com.attack.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

@Data
@Builder
@RelationshipProperties
public class TechnicRelation {
    @Id
    @GeneratedValue
    Long id0;

    @TargetNode
    private  NodeTechnic nodeTechnic;
}

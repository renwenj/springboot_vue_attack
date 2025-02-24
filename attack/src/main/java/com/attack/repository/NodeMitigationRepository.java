package com.attack.repository;

import com.attack.entity.NodeMitigation;
import com.attack.entity.NodeTechnic;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeMitigationRepository extends Neo4jRepository<NodeMitigation, Long> {
    NodeMitigation findNodeMitigationById(String id);
}

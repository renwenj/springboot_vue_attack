package com.attack.repository;

import com.attack.entity.NodeTechnic;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeTechnicRepository extends Neo4jRepository<NodeTechnic, Long> {
    NodeTechnic findNodeTechnicById(String id);
}

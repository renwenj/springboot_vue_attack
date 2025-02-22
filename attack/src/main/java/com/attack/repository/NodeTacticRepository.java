package com.attack.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import com.attack.entity.NodeTactic;

@Repository
public interface NodeTacticRepository extends Neo4jRepository<NodeTactic,Long> {
}

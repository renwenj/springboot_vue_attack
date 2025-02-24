package com.attack.repository;

import com.attack.entity.NodeTactic;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeTacticRepository extends Neo4jRepository<NodeTactic, Long> {
    NodeTactic findNodeTacticById(String id);

    List<NodeTactic> findAll();
}

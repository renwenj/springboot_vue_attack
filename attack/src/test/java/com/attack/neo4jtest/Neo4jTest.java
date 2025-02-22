package com.attack.neo4jtest;

import com.attack.entity.NodeTactic;
import com.attack.repository.NodeTacticRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Neo4jTest {
    @Autowired
    private NodeTacticRepository nodeTacticRepository;

    @Test
    void contextLoads() {
        NodeTactic nodeTactic = new NodeTactic();
        nodeTactic.setName("neo4j测试");
        nodeTacticRepository.save(nodeTactic);
    }
}

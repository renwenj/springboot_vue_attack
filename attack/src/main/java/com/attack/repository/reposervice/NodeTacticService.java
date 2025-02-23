package com.attack.repository.reposervice;

import com.attack.entity.NodeTactic;
import com.attack.repository.NodeTacticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeTacticService {
    @Autowired
    private NodeTacticRepository nodeTacticRepository;

    public List<NodeTactic> getAllNodeTactic(){
        return nodeTacticRepository.findAll();
    }

    public NodeTactic getNodeTacticById(String id){
        return nodeTacticRepository.findNodeTacticById(id);
    }

    public NodeTactic addNodeTactic(String id){
        return nodeTacticRepository.save(NodeTactic.builder().id(id).build());
    }

    public void deleteNodeTactic(String id){
        nodeTacticRepository.delete(getNodeTacticById(id));
    }
}

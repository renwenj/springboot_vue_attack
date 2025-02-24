package com.attack.repository.reposervice;

import com.attack.entity.NodeMitigation;
import com.attack.repository.NodeMitigationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeMitigationService {
    @Autowired
    private NodeMitigationRepository nodeMitigationRepository;

    public List<NodeMitigation> getAllNodeMitigation(){
        return nodeMitigationRepository.findAll();
    }

    public NodeMitigation getNodeMitigationById(String id){
        return nodeMitigationRepository.findNodeMitigationById(id);
    }


    public NodeMitigation addNodeMitigation(String id){
        return nodeMitigationRepository.save(NodeMitigation.builder().id(id).build());
    }

    public void deleteNodeMitigation(String id){
        nodeMitigationRepository.delete(getNodeMitigationById(id));
    }
}

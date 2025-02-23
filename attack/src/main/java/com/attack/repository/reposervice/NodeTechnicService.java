package com.attack.repository.reposervice;

import com.attack.entity.NodeTechnic;
import com.attack.repository.NodeTechnicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeTechnicService {
    @Autowired
    private NodeTechnicRepository nodeTechnicRepository;

    public List<NodeTechnic> getAllNodeTechnic(){
        return nodeTechnicRepository.findAll();
    }

    public NodeTechnic getNodeTechnicById(String id){
        return nodeTechnicRepository.findNodeTechnicById(id);
    }


    public NodeTechnic addNodeTechnic(String id){
        return nodeTechnicRepository.save(NodeTechnic.builder().id(id).build());
    }

    public void deleteNodeTechnic(String id){
        nodeTechnicRepository.delete(getNodeTechnicById(id));
    }
}

package com.attack.controller;

import com.attack.entity.NodeTactic;
import com.attack.entity.NodeTechnic;
import com.attack.repository.reposervice.NodeTacticService;
import com.attack.repository.reposervice.NodeTechnicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nodetechnic")
public class NodeTechnicController {
    @Autowired
    private NodeTechnicService nodeTechnicService;

    @GetMapping("/getnodetechnic")
    public NodeTechnic getNodeTechnic(@RequestParam("id")String id){
        return nodeTechnicService.getNodeTechnicById(id);
    }
}

package com.attack.controller;

import com.attack.entity.NodeTactic;
import com.attack.repository.reposervice.NodeTacticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nodetactic")
public class NodeTacticController {
    @Autowired
    private NodeTacticService nodeTacticService;

    @GetMapping("/getnodetactic")
    public NodeTactic getNodeTactic(@RequestParam("id")String id){
        return nodeTacticService.getNodeTacticById(id);
    }


}

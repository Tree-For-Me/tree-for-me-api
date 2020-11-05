package com.TreeForMe.Controllers;

import com.TreeForMe.Models.Plant;
import com.TreeForMe.Models.Message;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import com.TreeForMe.Shared.DiscoveryService;
import com.TreeForMe.Shared.AssistantService;
import com.TreeForMe.Models.PlantInfo;

@RestController
public class PlantInfoController {

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("discovery/getPlantSearchResult")
    public List<Plant> getPlantSearchResult(Message userMessage) {
        //TODO: get actual userid from front-end
        int userid = userMessage.getUser();
        PlantInfo pi = AssistantService.getInstance().getConvos().get(userid).getPlantInfo();
        System.out.println(pi.getFlowers());
        System.out.println(pi.getHumidity());
        System.out.println(pi.getLight());
//        ArrayList<String> keywords = new ArrayList<>();
//        if (pi.getFlowers() != "")
//            keywords.add(pi.getFlowers());
//        if (pi.getHumidity() != "")
//            keywords.add(pi.getHumidity());
//        if (pi.getLight() != "")
//            keywords.add(pi.getLight());

        List<Plant> plantResults = DiscoveryService.getInstance().getPlantNameFromFieldSearch(pi);
        return plantResults;
    }

}
package com.TreeForMe.Controllers;

import com.TreeForMe.Models.Plant;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import com.TreeForMe.Shared.DiscoveryService;
import com.TreeForMe.Models.PlantInfo;

@RestController
public class PlantInfoController {

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("discovery/getPlantSearchResult")
    public Plant getPlantSearchResult(PlantInfo plantInfo) {
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add(plantInfo.getFlowerType());
        keywords.add(plantInfo.isHumidity()? "humid" : "dry");
        keywords.add(plantInfo.getLight());
        keywords.add(plantInfo.isFlowers()? "flowers" : "no flowers");

        String plantResult = DiscoveryService.getInstance().getPlantNameFromKeywordSearch(keywords);
        Plant plant = new Plant(plantResult);
        return plant;
    }

}
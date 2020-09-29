package com.TreeForMe.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import com.TreeForMe.Shared.DiscoveryService;
import com.TreeForMe.Models.PlantInfo;

@RestController
public class PlantInfoController {

    @GetMapping("discovery/getPlantSearchResult")
    public String getPlantSearchResult(PlantInfo plantInfo) {

        ArrayList<String> keywords = new ArrayList<>();
        keywords.add(plantInfo.getFlowerType());
        keywords.add(plantInfo.isHumidity()? "humid" : "dry");
        keywords.add(plantInfo.getLight());
        keywords.add(plantInfo.isFlowers()? "flowers" : "no flowers");

        String plantResult = DiscoveryService.getInstance().getPlantNameFromKeywordSearch(keywords);

        return plantResult;
    }

}
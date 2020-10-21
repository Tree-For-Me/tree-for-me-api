package com.TreeForMe.Controllers;

import com.TreeForMe.Models.Plant;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

import com.TreeForMe.Shared.DiscoveryService;
import com.TreeForMe.Models.PlantInfo;

@RestController
public class PlantInfoController {

    /*
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("discovery/getPlantSearchResult")
    public List<Plant> getPlantSearchResult() {
        /* NOTE: This is currently hardcoded for the sake of testing the front end.
           This will need to be filled in with logic to get the correct keywords
           obtained from the conversation in the stored plantInfo object.

        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("fern");
        keywords.add("humid");
        keywords.add("bright indirect");
        keywords.add("flowers");

        List<Plant> plantResults = DiscoveryService.getInstance().getPlantNameFromKeywordSearch(keywords);
        return plantResults;
    }
    */

}
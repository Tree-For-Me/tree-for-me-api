package com.TreeForMe.Controllers;

import com.TreeForMe.Models.*;
import com.TreeForMe.Models.AssistantResponse.Intent;
import com.TreeForMe.Shared.AssistantService;
import com.TreeForMe.Shared.DiscoveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessageController {

    Map<Integer, Conversation> convos = new HashMap<>();

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getAssistantResponse")
    public ResponseEntity<Message> getAssistantResponse(Message userMessage) {
        int userid;

        // ensure user is an integer
        try {
            userid = userMessage.getUser();
        } catch(NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if(userid == -1) {
            // find a userid that doesn't already exist
            do {
                userid++;
            } while(convos.containsKey(userid));

            // make a new assistant service session and new plantInfo object
            convos.put(userid, new Conversation());
        }

        // ensure userid is valid
        if(!convos.containsKey(userid)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String userMessageContent = userMessage.getMessageContent();
        String returnMessage = "Welcome to Tree For Me. I'm going to help you find the perfect plant!\nTell me something about the plant you're looking for or the environment it will be in. For now, you can talk about humidity, flowers, or sunlight!";
        if(!userMessageContent.isEmpty()) {
            AssistantResponse ar = AssistantService.getInstance().getResponse(userMessage.getMessageContent());
            returnMessage = convos.get(userid).handleResponse(ar);
        }

        if(convos.get(userid).finished) {
            userid = -2;
        }

        return ResponseEntity.ok(new Message(returnMessage, userid));
        //return ResponseEntity.ok(new Message("Here comes the pizza!", -2));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("discovery/getPlantSearchResult")
    public List<Plant> getPlantSearchResult() {
        //TODO: get actual userid from front-end
        int userid = 0;
        PlantInfo pi = convos.get(userid).getPlantInfo();
        System.out.println(pi.getFlowers());
        System.out.println(pi.getHumidity());
        System.out.println(pi.getLight());
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add(pi.getFlowers());
        keywords.add(pi.getHumidity());
        keywords.add(pi.getLight());

        List<Plant> plantResults = DiscoveryService.getInstance().getPlantNameFromKeywordSearch(keywords);
        return plantResults;
    }

}

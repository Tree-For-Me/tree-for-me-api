package com.TreeForMe.Controllers;

import com.TreeForMe.Models.*;
import com.TreeForMe.Models.AssistantResponse.Intent;
import com.TreeForMe.Shared.PersonalityService;
import com.TreeForMe.Shared.TwitterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.watson.personality_insights.v3.model.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PersonalityController {

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("personality/getTwitterPersonality")
    public ResponseEntity<Plant> getTwitterPersonality(Message userMessage) {
        String twitterText = TwitterService.getInstance().getUserTweetText(userMessage.getMessageContent());
        Profile twitterProfile = PersonalityService.getInstance().getPersonalityProfile(twitterText);
        Personality twitterPersonality = new Personality(twitterProfile);
        Plant bestPlant = Personality.getPlantFromPersonality(twitterPersonality.getClosestPlant());
        return ResponseEntity.ok(bestPlant);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("personality/getTextPersonality")
    public ResponseEntity<Plant> getTextPersonality(Message userMessage) {
        Profile textProfile = PersonalityService.getInstance().getPersonalityProfile(userMessage.getMessageContent());
        Personality textPersonality = new Personality(textProfile);
        Plant bestPlant = Personality.getPlantFromPersonality(textPersonality.getClosestPlant());
        return ResponseEntity.ok(bestPlant);
    }
}

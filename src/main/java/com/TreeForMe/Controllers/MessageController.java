package com.TreeForMe.Controllers;

import com.TreeForMe.Models.AssistantResponse;
import com.TreeForMe.Models.AssistantResponse.Intent;
import com.TreeForMe.Models.Conversation;
import com.TreeForMe.Models.Message;
import com.TreeForMe.Models.PlantInfo;
import com.TreeForMe.Shared.AssistantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        AssistantResponse ar = AssistantService.getInstance().getResponse(userMessage.getMessageContent());
        String returnMessage = convos.get(userid).handleResponse(ar);

        return ResponseEntity.ok(new Message(returnMessage, userid));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getPromptMessage")
    public Message getPromptMessage(@RequestParam(value = "name", defaultValue = "-1") int name) {
        return new Message("Hello, welcome to Tree for Me! I’ll help you find the perfect plant for your environment.", name);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getHumidityMessage")
    public Message getHumidityMessage(@RequestParam(value = "name", defaultValue = "-1") int name) {
        return new Message("Is your area humid or dry?", name);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getWaterMessage")
    public Message getWaterMessage(@RequestParam(value = "name", defaultValue = "-1") int name) {
        return new Message("How often would you like to water the plant?", name);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getMaintenanceMessage")
    public Message getMaintenanceMessage(@RequestParam(value = "name", defaultValue = "-1") int name) {
        return new Message("Would you like a high, medium, or low-maintenance plant?", name);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getSunMessage")
    public Message getSunMessage(@RequestParam(value = "name", defaultValue = "-1") int name) {
        return new Message("What kind of sun does your space get?", name);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getSizeMessage")
    public Message getSizeMessage(@RequestParam(value = "name", defaultValue = "-1") int name) {
        return new Message("Would you lke a large, medium, or small-sized plant?", name);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getPlantTypeMessage")
    public Message getPlantTypeMessage(@RequestParam(value = "name", defaultValue = "-1") int name) {
        return new Message("Do you have any idea what kind of plant you want?", name);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getFlowersMessage")
    public Message getFlowersMessage(@RequestParam(value = "name", defaultValue = "-1") int name) {
        return new Message("Would you like your plant to have flowers or no flowers?", name);
    }
}

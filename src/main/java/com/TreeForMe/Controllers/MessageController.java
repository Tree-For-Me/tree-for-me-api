package com.TreeForMe.Controllers;

import com.TreeForMe.Models.Message;
import com.TreeForMe.Shared.AssistantService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getAssistantResponse")
    public Message getAssistantResponse(Message userInput) {
        Message response = AssistantService.getInstance().getResponse(userInput);
        return response;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getPromptMessage")
    public Message getPromptMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("Hello, welcome to Tree for Me! I’ll help you find the perfect plant for your environment.", name);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getHumidityMessage")
    public Message getHumidityMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("Is your area humid or dry?", name);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getWaterMessage")
    public Message getWaterMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("How often would you like to water the plant?", name);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getMaintenanceMessage")
    public Message getMaintenanceMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("Would you like a high, medium, or low-maintenance plant?", name);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getSunMessage")
    public Message getSunMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("What kind of sun does your space get?", name);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getSizeMessage")
    public Message getSizeMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("Would you lke a large, medium, or small-sized plant?", name);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getPlantTypeMessage")
    public Message getPlantTypeMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("Do you have any idea what kind of plant you want?", name);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getFlowersMessage")
    public Message getFlowersMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("Would you like your plant to have flowers or no flowers?", name);
    }
}

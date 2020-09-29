package com.TreeForMe.Controllers;

import com.TreeForMe.Models.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @GetMapping("/getPromptMessage")
    public Message getPromptMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("How can I help you find a plant today?", name);
    }

    @GetMapping("/getHumidityMessage")
    public Message getHumidityMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("How humid is the area in which you'd like the plant to live?", name);
    }

    @GetMapping("/getWaterMessage")
    public Message getWaterMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("How often would you like to water the plant?", name);
    }

    @GetMapping("/getMaintenanceMessage")
    public Message getMaintenanceMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("Would you like a high, medium, or low-maintenance plant?", name);
    }

    @GetMapping("/getSunMessage")
    public Message getSunMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("Towards which direction does your window face, North?", name);
    }

    @GetMapping("/getSizeMessage")
    public Message getSizeMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("Would you lke a large, medium, or small-sized plant?", name);
    }
}

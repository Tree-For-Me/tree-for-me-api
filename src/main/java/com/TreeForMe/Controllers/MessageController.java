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
        return new Message("Is your area humid or dry?", name);
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
        return new Message("What kind of sun does your space get?", name);
    }

    @GetMapping("/getSizeMessage")
    public Message getSizeMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("Would you lke a large, medium, or small-sized plant?", name);
    }

    @GetMapping("/getPlantTypeMessage")
    public Message getSizeMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("Do you have any idea what kind of plant you want?", name);
    }

    @GetMapping("/getFlowersMessage")
    public Message getSizeMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("Would you like your plant to have flowers or no flowers?", name);
    }
}

package com.TreeForMe.Controllers;

import com.TreeForMe.Models.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @GetMapping("/getMessage")
    public Message getMessage(@RequestParam(value = "name", defaultValue = "p1@n7b07") String name) {
        return new Message("How can I help you find a plant today?", name);
    }
}

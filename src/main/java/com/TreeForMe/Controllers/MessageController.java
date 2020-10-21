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

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getAssistantResponse")
    public ResponseEntity<Message> getAssistantResponse(Message userMessage) {
        return AssistantService.getInstance().getAssistantResponse(userMessage);
    }

}

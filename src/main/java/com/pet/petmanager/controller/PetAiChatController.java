package com.pet.petmanager.controller;
import com.pet.petmanager.service.PetAiChatService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pet/ai")
public class PetAiChatController {


    @Autowired
    private PetAiChatService petAiChatService;

    @RequestMapping("/chat")
    public Result chat(@RequestParam Integer userId,
                       @RequestParam Integer petId,
                       @RequestParam String message) {

        return petAiChatService.chat(userId, petId, message);
    }

}

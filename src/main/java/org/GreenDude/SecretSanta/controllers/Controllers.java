package org.GreenDude.SecretSanta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Controllers {

    @GetMapping("/secret_santa")
    public String mainPage(@RequestParam(name = "name", required = false, defaultValue = "there") String name, Model model){
        model.addAttribute("name", name);
        return "secret_santa";
    }
}

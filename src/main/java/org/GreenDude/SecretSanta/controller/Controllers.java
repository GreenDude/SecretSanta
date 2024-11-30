package org.GreenDude.SecretSanta.controller;

import org.GreenDude.SecretSanta.service.MainProcessor;
import org.GreenDude.SecretSanta.service.SimpleInMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
public class Controllers {

    @Autowired
    private SimpleInMemoryStorage simpleInMemoryStorage;

    @Autowired
    private MainProcessor mainProcessor;

    @GetMapping("/secret_santa")
    public String mainPage(@RequestParam(name = "name", required = false, defaultValue = "there") String name, Model model){
        model.addAttribute("name", name);
        return "secret_santa";
    }

    @GetMapping("/")
    public String homePage(){
//        mainProcessor.printFonts();
        return "index";
    }

    @PostMapping("/")
    public String uploadSurvey(Model model, @RequestParam("file") MultipartFile file){
        simpleInMemoryStorage.save(SimpleInMemoryStorage.TYPE.SURVEY, file);
        try {
            mainProcessor.processSurvey(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }
}

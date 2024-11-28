package org.GreenDude.SecretSanta.controller;

import org.GreenDude.SecretSanta.service.FileStorageService;
import org.GreenDude.SecretSanta.service.SimpleInMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Controller
public class Controllers {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired SimpleInMemoryStorage simpleInMemoryStorage;

    @GetMapping("/secret_santa")
    public String mainPage(@RequestParam(name = "name", required = false, defaultValue = "there") String name, Model model){
        model.addAttribute("name", name);
        return "secret_santa";
    }

    @GetMapping("/")
    public String homePage(){
        return "index";
    }

    @PostMapping("/")
    public String uploadSurvey(Model model, @RequestParam("file") MultipartFile file){
        fileStorageService.save(file);

        InputStream inputStream = (InputStream) simpleInMemoryStorage.read(SimpleInMemoryStorage.TYPE.SURVEY);

        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (inputStream, StandardCharsets.UTF_8))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String yeet = textBuilder.toString();
        return null;
    }
}

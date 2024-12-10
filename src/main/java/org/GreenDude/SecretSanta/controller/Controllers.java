package org.GreenDude.SecretSanta.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.GreenDude.SecretSanta.service.EmailService;
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

    @Autowired
    private EmailService emailService;

    @GetMapping("/secret_santa")
    public String mainPage(@RequestParam(name = "name", required = false, defaultValue = "there") String name, Model model){
        model.addAttribute("name", name);
//        try {
//            emailService.test();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return "secret_santa";
    }

    @GetMapping("/")
    public String homePage(){
        return "index";
    }

    @PostMapping("/upload")
    public void uploadSurvey(Model model,
                             @RequestParam("sheetName") String sheetName,
                             @RequestParam("file") MultipartFile file, HttpServletResponse response){

        simpleInMemoryStorage.save(SimpleInMemoryStorage.TYPE.SURVEY, file);

        File zip;
        try {
            zip = mainProcessor.processSurvey(file.getInputStream(), sheetName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Download file
        try {
            // Set response content type
            response.setContentType("text/plain");
            // Set response headers
            response.setHeader("Content-Disposition", "attachment; filename=" + zip.getName());
            // Get input stream from the file resource
            InputStream inputStream = new FileInputStream(zip);
            // Get output stream of the response
            OutputStream outputStream = response.getOutputStream();
            // Copy input stream to output stream
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Close streams
            inputStream.close();
            outputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

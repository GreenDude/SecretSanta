package org.GreenDude.SecretSanta.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.GreenDude.SecretSanta.models.Email;
import org.GreenDude.SecretSanta.models.Participant;
import org.GreenDude.SecretSanta.service.EmailService;
import org.GreenDude.SecretSanta.service.MainProcessor;
import org.GreenDude.SecretSanta.service.SimpleInMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.GreenDude.SecretSanta.service.SimpleInMemoryStorage.TYPE.PARTICIPANTS;

@Controller
public class Controllers {

    @Autowired
    private SimpleInMemoryStorage simpleInMemoryStorage;

    @Autowired
    private MainProcessor mainProcessor;

    @Autowired
    private EmailService emailService;

    @GetMapping("/secret_santa")
    public String finalPage(@RequestParam(name = "name", required = false, defaultValue = "there") String name, Model model) {
        model.addAttribute("name", name);
        return "secret_santa";
    }

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadSurvey(Model model,
                               @RequestParam("sheetName") String sheetName,
                               @RequestParam("file") MultipartFile file, HttpServletResponse response) {

        simpleInMemoryStorage.save(SimpleInMemoryStorage.TYPE.SURVEY, file);

        try {
            mainProcessor.processSurvey(file.getInputStream(), sheetName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "select_dowload_or_mail";
    }

    @GetMapping("/download")
    private void downloadFile(HttpServletResponse response) {
        File zip = mainProcessor.getZip();

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

    @GetMapping("/send-email")
    public String sendTheEmails(@ModelAttribute("email") Email email, Model model) {
        System.out.println(email.getLogin());

        return "authenticate_and_prep_template";
    }

    @PostMapping("/send-email")
    public String authenticateAndSend(Model model, @ModelAttribute("email") Email email){
        String output = "target/output/";

        //Start session
        emailService.createNewSession(email.getLogin(), email.getPassword());
        List<Participant> participants = (List<Participant>) simpleInMemoryStorage.read(PARTICIPANTS);

        List<File> attachments = new ArrayList<>();
        for(Participant participant: participants){

            File file = new File(output.concat(participant.getEmail().concat(".jpg")));
            attachments.add(file);

            emailService.sendMessage(participant.getEmail(), email.getSubject(), email.getText(), attachments);
        }

        return "redirect:/secret_santa";
    }

}

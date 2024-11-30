package org.GreenDude.SecretSanta.service;

import org.GreenDude.SecretSanta.models.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class MainProcessor {

    @Autowired
    private ExcelReader excelReader;
    @Autowired
    private SantaMatcher santaMatcher;
    @Autowired
    private CustomFontManager customFontManager;

    public void printFonts(){
        customFontManager.printCustomFonts();
    }

    public void processSurvey(InputStream surveyFile, String sheetName){
        List<Participant> participants = excelReader.getParticipantList(surveyFile, "Sheet1");
        santaMatcher.cleanDuplicates(participants);

        List<Participant> santaList = santaMatcher.returnSantaList(participants);

        for (Participant participant : santaList) {
            String text = "You are Secret Santa for: ";
            try {
                String favorites = participant.getFavoriteThings();
                ImageSystem imageSystem = new ImageSystem(ResourceUtils.getFile("classpath:cardTemplates/Large Template.jpg"));

                System.out.println("Printing for: ".concat(participant.getSecretSanta().getName()).concat(" is secret santa for:  ").concat(participant.getName()));
                imageSystem.addText("Mountains of Christmas Regular", 30, true, text)
                        .addLongText("Mountains of Christmas Bold", 50, participant.getName())
                        .addFiller(12)
                        .addText("Mountains of Christmas Regular", 30, true, "Their favorite things are: ")
                        .addFiller(12)
                        .addLongText("Mountains of Christmas Bold", 25, favorites)
                        .saveImage(participant.getSecretSanta().getEmail());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

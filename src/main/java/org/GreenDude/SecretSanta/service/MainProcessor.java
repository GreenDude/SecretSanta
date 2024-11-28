package org.GreenDude.SecretSanta.service;

import org.GreenDude.SecretSanta.models.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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
        participants.forEach(x-> System.out.println(x.getName()));
    }
}

package org.GreenDude.SecretSanta.service;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionMethod;
import org.GreenDude.SecretSanta.models.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class MainProcessor {

    @Autowired
    private ExcelReader excelReader;
    @Autowired
    private SantaMatcher santaMatcher;
    @Autowired
    private CustomFontManager customFontManager;

    ZipParameters zipParameters = new ZipParameters();

    public MainProcessor() {
        zipParameters.setCompressionMethod(CompressionMethod.STORE);
    }

    public void printFonts(){
        customFontManager.printCustomFonts();
    }

    public List<Participant> getSecretSantaList(InputStream surveyFile){
        List<Participant> participants = excelReader.getParticipantList(surveyFile, "Sheet1");
        santaMatcher.cleanDuplicates(participants);

        return santaMatcher.returnSantaList(participants);
    }

    public File processSurvey(InputStream surveyFile){
        String dirPath = "target".concat(File.separator).concat("output").concat(File.separator);
        ZipFile zippy = new ZipFile(dirPath.concat("output.zip"));

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

        try {
            zippy.addFolder(new File(dirPath));
            return zippy.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

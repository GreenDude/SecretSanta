package org.GreenDude.SecretSanta.service;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionMethod;
import org.GreenDude.SecretSanta.models.Participant;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class MainProcessor {

    @Autowired
    private ExcelReader excelReader;
    @Autowired
    private SantaMatcher santaMatcher;

    ZipParameters zipParameters = new ZipParameters();

    private final String dirPath = "target".concat(File.separator).concat("output").concat(File.separator);

    public MainProcessor() {
        zipParameters.setCompressionMethod(CompressionMethod.STORE);
    }

    public File processSurvey(InputStream surveyFile){

        List<Participant> participants = excelReader.getParticipantList(surveyFile, "Sheet1");
        santaMatcher.cleanDuplicates(participants);

        //This should fix the index out of bound exception
        List<Participant> santaList = new ArrayList<>();
        while (!santaMatcher.isMatchSuccessful()) {
            santaList = santaMatcher.returnSantaList(participants);
        }

        createOutputFolder();
        for (Participant participant : santaList) {
            createParticipantCard(participant);
        }

        santaMatcher.recordMatches();

        try (ZipFile zippy = new ZipFile(dirPath.concat("output.zip"))){
            zippy.addFolder(new File(dirPath));
            return zippy.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createOutputFolder(){
        Path path = Paths.get(dirPath);
        try {
            if (Files.exists(path)) {
                FileUtils.deleteDirectory(new File(dirPath));
            }
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createParticipantCard(Participant participant){
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

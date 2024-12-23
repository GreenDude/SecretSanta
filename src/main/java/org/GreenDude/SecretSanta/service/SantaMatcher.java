package org.GreenDude.SecretSanta.service;

import org.GreenDude.SecretSanta.models.Participant;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class SantaMatcher {

    private final static Random random = new Random();

    private StringBuilder stringBuilder = new StringBuilder();

    private boolean matchSuccessful = false;

    public void cleanDuplicates(List<Participant> participants) {
        List<Participant> p = participants.stream().distinct().toList();
        participants.clear();
        participants.addAll(p);

    }

    public boolean isMatchSuccessful(){
        return matchSuccessful;
    }

    public List<Participant> returnSantaList(List<Participant> participants) {

        List<Participant> santaList = new ArrayList<>(participants);
        for (Participant participant : participants) {
            try {
                Participant secretSanta = getNextSantaForParticipant(participant, santaList);
                participant.setSecretSanta(secretSanta);
                stringBuilder.append(participant.getName().concat(" : ").concat(secretSanta.getName()).concat("\n"));
            } catch (RuntimeException e){
                System.out.println("THIS SHOULD NEVER HAPPEN BUT IT DOES? WTF");
                matchSuccessful = false;
                return participants;
//                santaList.forEach(x-> System.out.println(x.getName()));
//                throw new RuntimeException("Odd error");
            }
        }
        matchSuccessful = true;
        return participants;
    }

    public void recordMatches(){
        String dirPath = "target".concat(File.separator).concat("output").concat(File.separator);
        String extractPath = "target".concat(File.separator).concat("extract").concat(File.separator).concat("output").concat(File.separator);
        try {
            Files.createDirectories(Paths.get(dirPath));
            Files.createDirectories(Paths.get(extractPath));
            try (FileWriter savedFile = new FileWriter(dirPath.concat("matches.txt"))) {
                savedFile.write(stringBuilder.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Participant getNextSantaForParticipant(Participant participant, List<Participant> santaList) {
        List<Participant> tempList = new ArrayList<>(santaList);
        tempList.remove(participant);
        Participant santa = tempList.get(random.nextInt(tempList.size()));
        santaList.remove(santa);
        return santa;
    }
}

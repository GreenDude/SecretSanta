package org.GreenDude.SecretSanta;

import org.GreenDude.SecretSanta.models.Participant;
import org.apache.commons.lang3.ObjectUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class SantaMatcher {

    private final static Random random = new Random();

    public void cleanDuplicates(List<Participant> participants) {
        List<Participant> p = participants.stream().distinct().toList();
        participants.clear();
        participants.addAll(p);

    }

    public List<Participant> returnSantaList(List<Participant> participants) {

        StringBuilder stringBuilder = new StringBuilder();
        List<Participant> santaList = new ArrayList<>(participants);
        for (Participant participant : participants) {
            Participant secretSanta = getNextSantaForParticipant(participant, santaList);
            participant.setSecretSanta(secretSanta);
            stringBuilder.append(participant.getName().concat(" : ").concat(secretSanta.getName()).concat("\n"));
        }
        String dirPath = "target".concat(File.separator).concat("output").concat(File.separator);
        try {
            Files.createDirectories(Paths.get(dirPath));
            try (FileWriter savedFile = new FileWriter(dirPath.concat("matches.txt"))) {
                savedFile.write(stringBuilder.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return participants;
    }

    private Participant getNextSantaForParticipant(Participant participant, List<Participant> santaList) {
        Participant santa = santaList.get(random.nextInt(santaList.size()));
        if (participant.compareTo(santa) == 0) {
            return getNextSantaForParticipant(participant, santaList);
        } else {
            santaList.remove(santa);
            return santa;
        }
    }
}

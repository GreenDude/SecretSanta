import ij.ImagePlus;
import org.GreenDude.SecretSanta.ExcelReader;
import org.GreenDude.SecretSanta.ImageSystem;
import org.GreenDude.SecretSanta.PathSystem;
import org.GreenDude.SecretSanta.SantaMatcher;
import org.GreenDude.SecretSanta.models.Participant;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //Read Input Params

        PathSystem pathSystem = new PathSystem();
        ExcelReader excelReader = new ExcelReader();
        SantaMatcher santaMatcher = new SantaMatcher();
        String cardTemplatePath = pathSystem.getTemplatePath("template");
        List<Participant> participants = excelReader.getParticipantList(pathSystem.getExcelPath("Ho-ho-ho"), "Sheet1");
        santaMatcher.cleanDuplicates(participants);

        List<Participant> matchedParticipants = santaMatcher.returnSantaList(participants);
        System.out.println("");
        System.out.println("Generating cards");
        System.out.println("");
        for (Participant participant : matchedParticipants) {
            String text = "You are Secret santa for";
            try {
                String favorites = participant.getFavoriteThings();
                ImageSystem imageSystem = new ImageSystem(cardTemplatePath);
                System.out.println("Printing for: ".concat(participant.getName()).concat(" : ").concat(participant.getSecretSanta().getEmail()));
                imageSystem.addText("Helvetica", 30, true, text)
                        .addLongText("Helvetica", 50, participant.getName())
                        .addText("Helvetica", 30, true, "Their favorite things are: ")
                        .addLongText("Helvetica", 25, favorites)
                        .saveImage(participant.getSecretSanta().getEmail());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

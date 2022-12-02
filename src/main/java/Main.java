import ij.ImagePlus;
import org.GreenDude.SecretSanta.ExcelReader;
import org.GreenDude.SecretSanta.ImageSystem;
import org.GreenDude.SecretSanta.PathSystem;
import org.GreenDude.SecretSanta.SantaMatcher;
import org.GreenDude.SecretSanta.models.Participant;

import java.awt.image.BufferedImage;
import java.io.IOException;
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
        for (Participant participant : matchedParticipants) {
            String text = "You are Secret santa for";
            try {
                String favorites = participant.getFavoriteThings();
                ImageSystem imageSystem = new ImageSystem(cardTemplatePath);
                System.out.println("Printing for: ".concat(participant.getName()).concat(" : ").concat(participant.getSecretSanta().getEmail()));
                imageSystem.addText("Helvetica", 30, true, text)
                        .addText("Helvetica", 50, true, participant.getName())
                        .addText("Helvetica", 30, true, "Their favorite things are: ")
                        .addLongText("Helvetica", 25, favorites)
                        .saveImage(participant.getSecretSanta().getEmail());
                /*
                BufferedImage image = ImageSystem.addText(openImage(pathSystem.getTemplatePath("template")), "Helvetica", 30, true, text);
                image = ImageSystem.addText(image, "Helvetica", 50, true, participant.getName());
                String favorites = participant.getFavoriteThings();
                image = ImageSystem.addText(image, "Helvetica", 30, true, "Their favorite things are: ");
                System.out.println("Printing for: ".concat(participant.getName()).concat(" : ").concat(participant.getSecretSanta().getEmail()));
                image = ImageSystem.addLongText(image, "Helvetica", 25, favorites);
                ImageSystem.saveImage(image, participant.getSecretSanta().getEmail());

                */

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

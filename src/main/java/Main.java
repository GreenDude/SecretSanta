import ij.ImagePlus;
import org.GreenDude.SecretSanta.ExcelReader;
import org.GreenDude.SecretSanta.ImageSystem;
import org.GreenDude.SecretSanta.PathSystem;
import org.GreenDude.SecretSanta.SantaMatcher;
import org.GreenDude.SecretSanta.models.Participant;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static org.GreenDude.SecretSanta.ImageSystem.openImage;

public class Main {

    public static void main(String[] args) {
        //Read Input Params

        PathSystem pathSystem = new PathSystem();
        ExcelReader excelReader = new ExcelReader();
        SantaMatcher santaMatcher = new SantaMatcher();
        List<Participant> participants = excelReader.getParticipantList(pathSystem.getExcelPath("Ho-ho-ho"), "Sheet1");
        santaMatcher.cleanDuplicates(participants);

        List<Participant> matchedParticipants = santaMatcher.returnSantaList(participants);
        for (Participant participant : matchedParticipants) {
            String text = "You are Secret santa for";
            try {
                BufferedImage image = ImageSystem.addText(openImage(pathSystem.getTemplatePath("template")), "Helvetica", 30, true, text);
                image = ImageSystem.addText(image, "Helvetica", 50, true, participant.getName());
                String favorites = participant.getFavoriteThings();
                image = ImageSystem.addText(image, "Helvetica", 30, true, "Their favorite things are: ");
                image = ImageSystem.addText(image, "Helvetica", 25, true, favorites);
                ImageSystem.saveImage(image, participant.getSecretSanta().getEmail());
//                new ImagePlus("", image).show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

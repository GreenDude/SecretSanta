import ij.ImagePlus;
import org.GreenDude.SecretSanta.ExcelReader;
import org.GreenDude.SecretSanta.ImageSystem;
import org.GreenDude.SecretSanta.SantaMatcher;
import org.GreenDude.SecretSanta.models.Participant;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static org.GreenDude.SecretSanta.ImageSystem.openImage;

public class Main {

    public static void main(String[] args) {
        //Read Input Params
        String path = "/home/george/IdeaProjects/SecretSanta/src/resources/template.jpg";
//        String path = "C:\\Users\\gmosin\\OneDrive - ENDAVA\\Desktop\\GIT\\SecretSanta\\src\\resources\\template.jpg";
        ExcelReader excelReader = new ExcelReader();
        SantaMatcher santaMatcher = new SantaMatcher();
        List<Participant> participants = excelReader.getParticipantList("/home/george/IdeaProjects/SecretSanta/src/resources/Test.xlsx");
//        List<Participant> participants = excelReader.getParticipantList("C:\\Users\\gmosin\\OneDrive - ENDAVA\\Desktop\\GIT\\SecretSanta\\src\\resources\\Test.xlsx");
        santaMatcher.cleanDuplicates(participants);

        List<Participant> matchedParticipants = santaMatcher.returnSantaList(participants);
        for (Participant participant : matchedParticipants) {
            String text = "You are Secret santa for";
            try {
                BufferedImage image = ImageSystem.addText(openImage(path), "Helvetica", 30, text);
                image = ImageSystem.addText(image, "Helvetica", 50, participant.getName());
                String favorites = "I don't want a lot for christmas, some beer and food. \n".concat(
                        "Oh and some other stuff as well"
                );
                ImageSystem.saveImage(image, participant.getName());
                new ImagePlus("", image).show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

import org.GreenDude.SecretSanta.models.Participant;
import org.GreenDude.SecretSanta.service.*;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void NoLonger_main(String[] args) {
        PathService pathSystem = new PathService();
        ExcelReader excelReader = new ExcelReader("Name", "Email",
                "favorite", null);
        SantaMatcher santaMatcher = new SantaMatcher();
        String cardTemplatePath = pathSystem.getTemplatePath("Large Template");
        CustomFontManager customFontManager = new CustomFontManager(pathSystem.getFontPath());
        customFontManager.printCustomFonts();

        List<Participant> participants = excelReader.getParticipantList(pathSystem.getExcelPath("Ho-ho-ho"), "Sheet1");
        santaMatcher.cleanDuplicates(participants);

        List<Participant> matchedParticipants = santaMatcher.returnSantaList(participants);
        System.out.println();
        System.out.println("Generating cards");
        System.out.println();
        for (Participant participant : matchedParticipants) {
            String text = "You are Secret Santa for: ";
            try {
                String favorites = participant.getFavoriteThings();
                ImageSystem imageSystem = new ImageSystem(cardTemplatePath);

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

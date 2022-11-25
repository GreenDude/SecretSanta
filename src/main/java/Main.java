import org.GreenDude.SecretSanta.ExcelReader;
import org.GreenDude.SecretSanta.SantaMatcher;
import org.GreenDude.SecretSanta.models.Participant;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //Read Input Params

        ExcelReader excelReader = new ExcelReader();
        SantaMatcher santaMatcher = new SantaMatcher();
        List<Participant> participants = excelReader.getParticipantList("C:\\Users\\gmosin\\OneDrive - ENDAVA\\Desktop\\GIT\\SecretSanta\\src\\resources\\Test.xlsx");
        santaMatcher.cleanDuplicates(participants);

        santaMatcher.returnSantaList(participants);
    }
}

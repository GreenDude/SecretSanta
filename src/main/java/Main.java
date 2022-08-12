import org.GreenDude.SecretSanta.SantaMatcher;
import org.GreenDude.SecretSanta.models.Participant;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //Read Input Params


    }

    public void test(){
        //Test matcher

        List<Participant> participants = new ArrayList<>();

        participants.add(new Participant("John Doe", "John.Doe@example.com"));
        participants.add(new Participant("Jane Doe", "Jane.Doe@example.com"));
        participants.add(new Participant("John Cena", "John.Cena@example.com"));
        participants.add(new Participant("Jane Cena", "John.Cena@example.com"));
        participants.add(new Participant("John Poe", "John.Poe@example.com"));
        participants.add(new Participant("Jane Poe", "John.Poe@example.com"));

        SantaMatcher santaMatcher = new SantaMatcher();
        santaMatcher.returnSantaList(participants).forEach(x-> System.out.println(x.getName().concat(" : ").concat(x.getSecretSanta().getName())));
    }
}

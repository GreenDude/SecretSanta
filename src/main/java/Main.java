import org.GreenDude.SecretSanta.ImageSystem;
import org.GreenDude.SecretSanta.SantaMatcher;
import org.GreenDude.SecretSanta.models.Participant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.GreenDude.SecretSanta.ImageSystem.openImage;

public class Main {

    public static void main(String[] args) throws IOException {
        //Read Input Params

        Main main = new Main();
        main.test();
    }

    public void test() throws IOException {

        openImage("/home/george/IdeaProjects/SecretSanta/src/resources/template.jpg");
    }
}

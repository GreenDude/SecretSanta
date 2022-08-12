package org.GreenDude.SecretSanta;

import org.GreenDude.SecretSanta.models.Participant;

import java.util.*;

public class SantaMatcher {

    public List<Participant> returnSantaList(List<Participant> participants) {

        List<Participant> santas = new ArrayList<>(participants);
        for (Participant participant : participants) {
            participant.setSecretSanta(getNextSantaForParticipant(participant, santas));
        }

        return participants;
    }

    private Participant getNextSantaForParticipant(Participant participant, List<Participant> santas) {
        Random random = new Random();

        Participant santa = santas.get(random.nextInt(santas.size()));
        if (participant.compareTo(santa)) {
            return getNextSantaForParticipant(participant, santas);
        } else {
            Participant foundSanta = santa;
            santas.remove(santa);
            return foundSanta;
        }
    }
}

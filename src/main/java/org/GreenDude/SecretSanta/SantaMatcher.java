package org.GreenDude.SecretSanta;

import org.GreenDude.SecretSanta.models.Participant;
import org.apache.commons.lang3.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

public class SantaMatcher {

    public void cleanDuplicates(List<Participant> participants) {
        List <Participant> p = participants.stream().distinct().toList();
        participants.clear();
        participants.addAll(p);

    }

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
        if (participant.compareTo(santa) == 0) {
            return getNextSantaForParticipant(participant, santas);
        } else {
            Participant foundSanta = santa;
            santas.remove(santa);
            return foundSanta;
        }
    }
}

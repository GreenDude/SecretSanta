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

        List<Participant> santaList = new ArrayList<>(participants);
        for (Participant participant : participants) {
            participant.setSecretSanta(getNextSantaForParticipant(participant, santaList));
        }

        return participants;
    }

    private Participant getNextSantaForParticipant(Participant participant, List<Participant> santaList) {
        Random random = new Random();

        Participant santa = santaList.get(random.nextInt(santaList.size()));
        if (participant.compareTo(santa) == 0) {
            return getNextSantaForParticipant(participant, santaList);
        } else {
            santaList.remove(santa);
            return santa;
        }
    }
}

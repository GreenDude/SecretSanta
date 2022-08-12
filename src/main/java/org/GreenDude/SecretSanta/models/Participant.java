package org.GreenDude.SecretSanta.models;

public class Participant {

    private String name;
    private String email;
    private Participant secretSanta;

    public Participant(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Participant getSecretSanta() {
        return secretSanta;
    }

    public void setSecretSanta(Participant secretSanta) {
        this.secretSanta = secretSanta;
    }

    public boolean compareTo(Participant anotherParticipant) {

        return this.name.equals(anotherParticipant.getName()) && this.email.equals(anotherParticipant.getEmail());
    }
}

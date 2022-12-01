package org.GreenDude.SecretSanta.models;

import org.jetbrains.annotations.Contract;

import java.util.Comparator;

public class Participant implements Comparable {

    private final String name;
    private final String email;
    private Participant secretSanta;

    private String favoriteThings;

    public Participant(String name, String email, String favoriteThings) {
        this.name = name;
        this.email = email;
        this.favoriteThings = favoriteThings;
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

    public String getFavoriteThings() {
        return favoriteThings;
    }

    public void setSecretSanta(Participant secretSanta) {
        this.secretSanta = secretSanta;
    }

    @Override
    public int compareTo(Object comparable) throws IllegalArgumentException {
        Participant comparableParticipant = (Participant) comparable;
        return Comparator.comparing(Participant::getName).thenComparing(Participant::getEmail).compare(this, comparableParticipant);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + email.hashCode();
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((Participant) obj).name) && this.email.equals(((Participant) obj).email);
    }
}

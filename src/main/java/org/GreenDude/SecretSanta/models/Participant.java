package org.GreenDude.SecretSanta.models;

public class Participant {

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
}

package org.GreenDude.SecretSanta.Impl;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.awt.Font.*;

public class CustomFontManager {

    private List<Font> customFonts = new ArrayList<>();
    private final GraphicsEnvironment localGraphicsEnvironment;

    public CustomFontManager(String defaultFontPath) {
        localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            List<String> fonts = Stream.of(Objects.requireNonNull(new File(defaultFontPath).listFiles()))
                    .filter(file -> !file.isDirectory()).map(x-> defaultFontPath.concat(x.getName())).toList();

            fonts.forEach(this::addCustomFont);
    }

    public void printCustomFonts(){
        customFonts.forEach(font -> System.out.println(font.getFontName()));
    }

    public void addCustomFont(String path) {
        try {
            Font newFont = createFont(TRUETYPE_FONT, new File(path));
            localGraphicsEnvironment.registerFont(newFont);
            customFonts.add(newFont);

        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

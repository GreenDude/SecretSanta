package org.GreenDude.SecretSanta.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.awt.Font.*;

@Service
public class CustomFontManager {

    private List<Font> customFonts = new ArrayList<>();
    private final GraphicsEnvironment localGraphicsEnvironment;

    public CustomFontManager() {
        localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            File fontFolder = ResourceUtils.getFile("classpath:static/fonts");
            Stream.of(Objects.requireNonNull(fontFolder.listFiles())).forEach(this::addCustomFont);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public CustomFontManager(String defaultFontPath) {
        localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            List<String> fonts = Stream.of(Objects.requireNonNull(new File(defaultFontPath).listFiles()))
                    .filter(file -> !file.isDirectory()).map(File::getPath).toList();
            fonts.forEach(this::addCustomFont);
    }

    public void printCustomFonts(){
        System.out.println();
        System.out.println("Constome fonts added:");
        customFonts.forEach(font -> System.out.println(font.getFontName()));
    }

    private void addCustomFont(File file) {
        try {
            Font newFont = createFont(TRUETYPE_FONT, file);
            localGraphicsEnvironment.registerFont(newFont);
            customFonts.add(newFont);

        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
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

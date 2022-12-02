package org.GreenDude.SecretSanta;

import org.apache.commons.lang3.SerializationUtils;

import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.AttributedString;
import java.util.ArrayList;

public class ImageSystem {

    private final BufferedImage template;
    private BufferedImage draftImage;

    private static int currentYpos = 200;
    private static final int defaultYpos = 200;

    private static final int padding = 200;
    private static int imageWidth = 0;

    public ImageSystem(String path){
        try {
            template = ImageIO.read(new File(path));
            draftImage = template;
            imageWidth = template.getWidth();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ImageSystem addText(String fontName, int size, boolean centered, String text) throws IOException {

        //New Font
        Font font = new Font(fontName, Font.BOLD, size);

        //Center out text
        Graphics graphics = draftImage.getGraphics();

        FontMetrics fontMetrics = graphics.getFontMetrics(font);
        int xPos = centered ? (draftImage.getWidth() - fontMetrics.stringWidth(text)) / 2 : defaultYpos;
        currentYpos += fontMetrics.getAscent() * 1.35;
        int yPos = currentYpos;

        //write string
        AttributedString attributedString = new AttributedString(text);
        attributedString.addAttribute(TextAttribute.FONT, font);
        attributedString.addAttribute(TextAttribute.FOREGROUND, new Color(255, 18, 42));

        graphics.drawString(attributedString.getIterator(), xPos, yPos);

        return this;
    }

    public ImageSystem addLongText(String fontName, int size, String text) throws IOException {

        //Get calculation data
        Font font = new Font(fontName, Font.BOLD, size);
        int textLength = 0;
        //Split text by regex
        List<String> splited = Arrays.asList(text.split("[\\s|\\n]"));
        String tempString = "";
        String stringToAdd = "";
        for (String s : splited) {
            tempString = tempString.concat(s).concat(" ");
            if (textFits(font, draftImage, tempString)) {
                stringToAdd = stringToAdd.concat(s).concat(" ");
            } else {
                addText(fontName, size, true, stringToAdd);
                tempString = s;
                stringToAdd = "";
            }
        }
        addText(fontName, size, true, stringToAdd.length() > 0 ? stringToAdd : tempString);

        return this;
    }

    public void saveImage(String fileName) {
        String dirPath = "target".concat(File.separator).concat("output").concat(File.separator);
        try {
            Files.createDirectories(Paths.get(dirPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File savedFile = new File(dirPath.concat(fileName.concat(".jpg")));
        try {
            ImageIO.write(draftImage, "jpg", savedFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        resetYpos();
        resetImage();
    }

    private void resetImage(){
        this.draftImage = this.template;
    }

    private boolean textFits(Font font, BufferedImage bufferedImage, String text) {

        GlyphVector glyphVector = font.createGlyphVector(bufferedImage.getGraphics().getFontMetrics(font).getFontRenderContext(), text);
        Shape outline = glyphVector.getOutline(0, 0);
        double expectedWith = outline.getBounds().getWidth();

        return expectedWith < (imageWidth - padding * 2);
    }

    private void resetYpos() {
        currentYpos = defaultYpos;
    }
}
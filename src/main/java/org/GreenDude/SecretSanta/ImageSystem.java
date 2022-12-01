package org.GreenDude.SecretSanta;

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
    private static int currentYpos = 200;
    private static final int defaultYpos = 200;

    private static final int padding = 200;
    private static final int imageWidth = 0;

    public static BufferedImage openImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage addText(BufferedImage image, String fontName, int size, boolean centered, String text) throws IOException {

        //New Font
        Font font = new Font(fontName, Font.BOLD, size);

        //Center out text
        Graphics graphics = image.getGraphics();

        FontMetrics fontMetrics = graphics.getFontMetrics(font);
        int xPos = centered ? (image.getWidth() - fontMetrics.stringWidth(text)) / 2 : defaultYpos;
        currentYpos += fontMetrics.getAscent() * 1.35;
        int yPos = currentYpos;

        //write string
        AttributedString attributedString = new AttributedString(text);
        attributedString.addAttribute(TextAttribute.FONT, font);
        attributedString.addAttribute(TextAttribute.FOREGROUND, new Color(255, 18, 42));

        graphics.drawString(attributedString.getIterator(), xPos, yPos);

        return image;
    }

    public static BufferedImage addLongText(BufferedImage image, String fontName, int size, int margin, String text) {

        //Get calculation data
        Font font = new Font(fontName, Font.BOLD, size);
        int width = image.getWidth() - margin * 2;
        //Split text by regex
        List<String> splited = Arrays.asList(text.split("[\\s|\\n,.]"));
        String shortString = "";
        for (String s : splited) {

            shortString.concat(s).concat(", ");
        }


        return image;
    }

    public static void saveImage(BufferedImage image, String fileName) {
        String dirPath = "target".concat(File.separator).concat("output").concat(File.separator);
        try {
            Files.createDirectories(Paths.get(dirPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File savedFile = new File(dirPath.concat(fileName.concat(".jpg")));
        try {
            ImageIO.write(image, "jpg", savedFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        resetYpos();

    }

    private boolean textFits(Font font, BufferedImage bufferedImage, String text){
        GlyphVector glyphVector = font.createGlyphVector(bufferedImage.getGraphics().getFontMetrics(font).getFontRenderContext(), text);

        Shape outline = glyphVector.getOutline(0, 0);
        double expectedWith = outline.getBounds().getWidth();
        double expectedHeight = outline.getBounds().getHeight();

        return false;
    }

    private static void resetYpos() {
        currentYpos = defaultYpos;
    }
}
package org.GreenDude.SecretSanta.Impl;

import java.io.FileWriter;
import java.net.URI;
import java.net.URISyntaxException;

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
import java.util.Locale;

public class ImageSystem {

    private final BufferedImage template;
    private BufferedImage draftImage;

    private static int currentYpos = 200;
    private static final int defaultYpos = 200;

    private static final int padding = 200;
    private static int imageWidth = 0;

    String linkList;

    public ImageSystem(String path){
        try {
            template = ImageIO.read(new File(path));
            draftImage = template;
            imageWidth = template.getWidth();
            linkList = "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ImageSystem addFiller(int size) throws IOException {
        return addText("Helvetica", size, true, "FILLER", new Color(255, 253, 238));
    }

    public ImageSystem addText(String fontName, int size, boolean centered, String text) throws IOException {
        return addText(fontName, size, centered, text, new Color(255, 18, 42));
    }

    private ImageSystem addText(String fontName, int size, boolean centered, String text, Color color) throws IOException {

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
        attributedString.addAttribute(TextAttribute.FOREGROUND, color);

        graphics.drawString(attributedString.getIterator(), xPos, yPos);

        return this;
    }

    public ImageSystem addLongText(String fontName, int size, String text) throws IOException {

        //Get calculation data
        Font font = new Font(fontName, Font.BOLD, size);
        //Split text by regex
        String[] splited = text.split("[\\s|\\n]");
        String tempString = "";
        String stringToAdd = "";
        for (String s : splited) {
            if(s.toLowerCase(Locale.ROOT).contains("http")){
                try {
                    String uri = new URI(s).getHost();
                    linkList = linkList.concat(uri).concat(" : ").concat(s).concat("\n");
                    s = uri;
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
            if(s.length() == 0){
                continue;
            }
            tempString = tempString.concat(s).concat(" ");
            if (textFits(font, draftImage, tempString)) {
                stringToAdd = stringToAdd.concat(s).concat(" ");
            } else {
                addText(fontName, size, true, stringToAdd);
                tempString = s.concat(" ");
                stringToAdd = tempString;
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
        if (!linkList.isEmpty()){
            try (FileWriter linkText = new FileWriter(dirPath.concat(fileName.concat("-link.txt")))) {
                linkText.write(linkList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
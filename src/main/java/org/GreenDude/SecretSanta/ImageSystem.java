package org.GreenDude.SecretSanta;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

import javax.imageio.ImageIO;
import javax.print.attribute.Attribute;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;

public class ImageSystem {

    public static void openImage(String path) throws IOException {
        //Read image
        BufferedImage image = ImageIO.read(new File(path));

        String text = "TEST";

        //New Font
        Font font = new Font("Arial", Font.BOLD, 18);

        //Center out text
        Graphics graphics = image.getGraphics();

        FontMetrics fontMetrics = graphics.getFontMetrics(font);
        int xPos = (image.getWidth() - fontMetrics.stringWidth(text)) / 2;
        int yPos = (image.getHeight() - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();

        FontMetrics ruler = graphics.getFontMetrics(font);
        GlyphVector glyphVector = font.createGlyphVector(ruler.getFontRenderContext(), text);

        Shape outline = glyphVector.getOutline(0,0);
        double expectedWith = outline.getBounds().getWidth();
        double expectedHeight = outline.getBounds().getHeight();

        boolean textFits = image.getWidth() >= expectedWith && image.getHeight() >= expectedHeight;

        if (!textFits){
            double adjustedWith = font.getSize2D() * image.getWidth()/expectedWith;
            double adjustedHeight = font.getSize2D() * image.getHeight()/expectedHeight;

            double newFontSize = Math.min(adjustedWith, adjustedHeight);
            font = font.deriveFont(font.getStyle(), (float) newFontSize);
        }

        //write string
        AttributedString attributedString = new AttributedString(text);
        attributedString.addAttribute(TextAttribute.FONT, font);
        attributedString.addAttribute(TextAttribute.FOREGROUND, Color.GREEN);

        graphics.drawString(attributedString.getIterator(), xPos, yPos);

        new ImagePlus("", image).show();
    }

}

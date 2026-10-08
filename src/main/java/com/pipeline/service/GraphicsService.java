package com.pipeline;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphicsService {

    public void generateImage() throws IOException {
        System.out.println("Generating image...");
        int width = 1280;
        int height = 720;

        // Create an image
        BufferedImage image = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D graphics = image.createGraphics();

        // Background
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, width, height);

        // Title
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Arial", Font.BOLD, 60));
        graphics.drawString("My Video Pipeline", 350, 300);

        // Subtitle
        graphics.setFont(new Font("Arial", Font.PLAIN, 30));
        graphics.drawString("Graphics2D Test", 490, 360);

        // Rectangle
        graphics.setColor(Color.BLUE);
        graphics.fillRect(490, 420, 300, 80);

        // Clean up
// Clean up
        graphics.dispose();

// Create output directory
        File outputDir = new File("output");
        outputDir.mkdirs();

// Save image
        File output = new File("output/test.png");
        ImageIO.write(image, "png", output);

        System.out.println("Image generated: " + output.getAbsolutePath());
    }
}
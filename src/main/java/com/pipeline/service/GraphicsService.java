
package com.pipeline;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class GraphicsService {

    public void generateImage() throws IOException {

        System.out.println("Generating 1000 frames...");

        int width = 1280;
        int height = 720;
        int totalFrames = 1000;

        Random random = new Random();

        // Create output directory
        File outputDir = new File("output/frames");
        outputDir.mkdirs();

        for (int frame = 1; frame <= totalFrames; frame++) {

            // Create an image
            BufferedImage image = new BufferedImage(
                    width,
                    height,
                    BufferedImage.TYPE_INT_RGB
            );

            Graphics2D graphics = image.createGraphics();

            // Random background color
            Color backgroundColor = new Color(
                    random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256)
            );

            graphics.setColor(backgroundColor);
            graphics.fillRect(0, 0, width, height);

            // Title
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("Arial", Font.BOLD, 60));
            graphics.drawString("My Video Pipeline", 350, 300);

            // Subtitle
            graphics.setFont(new Font("Arial", Font.PLAIN, 30));
            graphics.drawString("Frame: " + frame, 490, 360);

            // Random rectangle position, size, and color
            int rectWidth = 100 + random.nextInt(201);
            int rectHeight = 50 + random.nextInt(101);
            int x = random.nextInt(width - rectWidth);
            int y = random.nextInt(height - rectHeight);

            graphics.setColor(new Color(
                    random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256)
            ));

            graphics.fillRect(x, y, rectWidth, rectHeight);

            // Clean up
            graphics.dispose();

            // Save each frame with a sequential filename
            File output = new File(
                    outputDir,
                    String.format("frame%04d.png", frame)
            );

            ImageIO.write(image, "png", output);

            if (frame % 100 == 0) {
                System.out.println("Generated " + frame + " frames");
            }
        }

        System.out.println("All 1000 frames generated successfully!");
    }
}

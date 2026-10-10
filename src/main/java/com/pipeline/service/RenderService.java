package com.pipeline.service;

import java.io.IOException;
public class RenderService{
    public void createVideoByFrames() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffmpeg",
                "-y",
                "-framerate", "30",
                "-i", "output/frames/frame%04d.png",
                "-c:v", "libx264",
                "-pix_fmt", "yuv420p",
                "output/video.mp4"
        );

        Process process = processBuilder.start();

        int exitCode = process.waitFor();
        if(exitCode != 0){
            throw new RuntimeException("Process exited with code " + exitCode);
        }
        System.out.println("Video generated successfully! = output/video.mp4");
    }
}
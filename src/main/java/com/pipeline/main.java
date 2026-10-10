package com.pipeline;

import java.io.IOException;

public class main {

    public static void main(String[] args) throws Exception, InterruptedException {


        com.pipeline.service.Service Service = new com.pipeline.service.Service();
        Service.testApi();
//        com.pipeline.GraphicsService graphicsService = new com.pipeline.GraphicsService();
//        graphicsService.generateImage();
//
//        com.pipeline.service.RenderService renderService = new com.pipeline.service.RenderService();
//        renderService.createVideoByFrames();
    }
}
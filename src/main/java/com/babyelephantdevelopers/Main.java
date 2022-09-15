package com.babyelephantdevelopers;

import sun.security.util.SecurityConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");
       // checkScreenCaptureAllowed();
        UI ui = new UI();
        ui.startApplication();

    }
    private static void checkScreenCaptureAllowed() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new AWTPermission("readDisplayPixels"));
        }
    }
}
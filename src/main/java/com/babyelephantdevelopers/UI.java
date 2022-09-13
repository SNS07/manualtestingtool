package com.babyelephantdevelopers;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UI {

    private JFrame frame;

    public void startApplication() throws Exception{
        //Creating the Frame
        frame = new JFrame("Testing Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        JPanel panel = new JPanel();
        JButton screenshot = new JButton("TakeScreenShot");
        panel.add(screenshot);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
        screenshot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    takeScreenshot();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (AWTException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void takeScreenshot() throws IOException, AWTException {
        frame.setState(Frame.ICONIFIED);
        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(image, "png", new File("/Users/sreenaths/Desktop/screenshot.png"));
        frame.setState(Frame.NORMAL);
    }
}

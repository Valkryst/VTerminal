package com.valkryst.VTerminal.samples.printer;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.printer.ImagePrinter;

import javax.imageio.ImageIO;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SampleImagePrinter {
    public static void main(final String[] args) throws IOException {
        final Screen screen = new Screen();


        final String filePath = "src/test/resources/ImagePrinterTest.png";
        final BufferedImage image = ImageIO.read(new File(filePath));

        final ImagePrinter printer = new ImagePrinter(image);
        printer.print(screen.getTiles(), new Point(0, 0));


        screen.addCanvasToFrame();
    }
}
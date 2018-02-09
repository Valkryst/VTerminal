package com.valkryst.VTerminal.samples.printer;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.printer.ImagePrinter;

import javax.imageio.ImageIO;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleImagePrinter {
    public static void main(final String[] args) throws IOException, URISyntaxException {
        final Panel panel = new PanelBuilder().build();

        final String filePath = System.getProperty("user.dir") + "/res_test/ImagePrinterTest.png";
        final BufferedImage image = ImageIO.read(new File(filePath));

        final ImagePrinter printer = new ImagePrinter(image);
        printer.print(panel, new Point(0, 0));

        panel.draw();
    }
}
package component;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.builder.*;
import com.valkryst.VTerminal.component.Layer;
import com.valkryst.VTerminal.component.ProgressBar;
import com.valkryst.VTerminal.component.RadioButton;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.printer.RectanglePrinter;
import com.valkryst.VTerminal.printer.RectangleType;

import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.io.IOException;

public class SampleGUI {
    public static void main(final String[] args) throws IOException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1);

        final Dimension dimensions = new Dimension(80, 24);
        final Screen screen = new Screen(dimensions, font);

        // Construct the main layer:
        final LayerBuilder layerBuilder = new LayerBuilder();
        layerBuilder.setDimensions(new Dimension(screen.getWidth(), screen.getHeight()));
        final Layer layer = layerBuilder.build();


        // Print the section borders.
        final RectanglePrinter printer = new RectanglePrinter();
        printer.setRectangleType(RectangleType.HEAVY);

        printer.setWidth(23);
        printer.setHeight(24);
        printer.setTitle("Button");
        printer.print(layer.getTiles(), new Point(0, 0));

        printer.setHeight(22);
        printer.setTitle("Radio Buttons");
        printer.print(layer.getTiles(), new Point(0, 2));

        printer.setHeight(16);
        printer.setTitle("Check Boxes");
        printer.print(layer.getTiles(), new Point(0, 8));

        printer.setHeight(13);
        printer.setTitle("Progress Bar");
        printer.print(layer.getTiles(), new Point(0, 11));

        printer.setHeight(11);
        printer.setTitle("Label");
        printer.print(layer.getTiles(), new Point(0, 13));



        // Populate the Button section.
        final ButtonBuilder buttonBuilder = new ButtonBuilder();
        buttonBuilder.setPosition(1, 1);
        buttonBuilder.setText("<Click Me!>");
        buttonBuilder.setOnClickFunction(() -> System.out.println("You clicked the button."));

        layer.addComponent(buttonBuilder.build());



        // Populate the Radio Button section.
        final RadioButtonGroup groupA = new RadioButtonGroup();
        final RadioButtonGroup groupB = new RadioButtonGroup();
        final RadioButtonBuilder radioButtonBuilder = new RadioButtonBuilder();

        radioButtonBuilder.setGroup(groupA);
        radioButtonBuilder.setText("Group A - Button #1");
        radioButtonBuilder.setPosition(1, 3);
        layer.addComponent(radioButtonBuilder.build());

        radioButtonBuilder.setText("Group A - Button #2");
        radioButtonBuilder.setPosition(1, 4);
        layer.addComponent(radioButtonBuilder.build());


        radioButtonBuilder.setGroup(groupB);
        radioButtonBuilder.setText("Group B - Button #1");
        radioButtonBuilder.setPosition(1, 6);
        layer.addComponent(radioButtonBuilder.build());

        radioButtonBuilder.setText("Group B - Button #2");
        radioButtonBuilder.setPosition(1, 7);
        final RadioButton radioButton = radioButtonBuilder.build();
        groupB.setCheckedButton(radioButton);
        layer.addComponent(radioButton);



        // Populate the Check Boxes section.
        final CheckBoxBuilder checkBoxBuilder = new CheckBoxBuilder();

        checkBoxBuilder.setText("Check Box #1");
        checkBoxBuilder.setChecked(true);
        checkBoxBuilder.setPosition(1, 9);
        layer.addComponent(checkBoxBuilder.build());

        checkBoxBuilder.setText("Check Box #2");
        checkBoxBuilder.setChecked(false);
        checkBoxBuilder.setPosition(1, 10);
        layer.addComponent(checkBoxBuilder.build());



        // Populate the Progress Bar section.
        final ProgressBarBuilder progressBarBuilder = new ProgressBarBuilder();
        progressBarBuilder.setPosition(1, 12);
        progressBarBuilder.setDimensions(21, 1);

        final ProgressBar progressBar = progressBarBuilder.build();
        layer.addComponent(progressBar);

        final Timer timer = new Timer(1000, e -> {
            int pct = progressBar.getPercentComplete();

            if (pct < 100) {
                pct += 5;
            } else {
                pct = 0;
            }

            progressBar.setPercentComplete(pct);
        });
        timer.start();



        // Populate the Label section.
        final LabelBuilder labelBuilder = new LabelBuilder();
        labelBuilder.setText("This is a label.");
        labelBuilder.setPosition(1, 14);
        layer.addComponent(labelBuilder.build());


        // Add the layer to the Screen only after all components have been
        // added to the layer, or else the screen won't add the event
        // listeners of the components to itself.
        screen.addComponent(layer);

        //screen.addCanvasToFrame();
        screen.addCanvasToFullScreenFrame(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
    }
}

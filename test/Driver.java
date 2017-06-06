import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.builder.component.*;
import com.valkryst.VTerminal.component.Label;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URISyntaxException;

public class Driver {

    public static void main(String[] args) throws IOException {
        try {
            final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt");

            final Screen screen = new Screen(0, 0, 80, 48);

            final Panel terminal = new PanelBuilder().setWidthInCharacters(80)
                                                                .setHeightInCharacters(24)
                                                                .setAsciiFont(font)
                                                                .setCurrentScreen(screen)
                                                                .build();

            terminal.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP: {
                            screen.setTopRowIndex(screen.getTopRowIndex() - 1);
                            break;
                        }
                        case KeyEvent.VK_DOWN: {
                            screen.setTopRowIndex(screen.getTopRowIndex() + 1);
                            break;
                        }
                    }
                }
            });

            screen.setParentPanel(terminal);
            terminal.getCurrentScreen().clear('X');

            new TextFieldBuilder().setColumnIndex(0)
                                        .setRowIndex(4)
                                        .setWidth(10)
                                        .setRadio(terminal.getRadio())
                                        .setPanel(terminal)
                                        .build();


            final Label label = new LabelBuilder().setColumnIndex(0)
                                                            .setRowIndex(5)
                                                            .setText("This is a label.")
                                                            .setPanel(terminal)
                                                            .build();

            new ButtonBuilder().setColumnIndex(0)
                                    .setRowIndex(6)
                                    .setText("Click Me")
                                    .setOnClickFunction(() -> label.getStrings()[0].invertColors())
                                    .setRadio(terminal.getRadio())
                                    .setPanel(terminal)
                                    .build()
                                    .enableBlinkEffect((short) 1000);

            new CheckBoxBuilder().setColumnIndex(0)
                                        .setRowIndex(7)
                                        .setText("Check box label.")
                                        .setRadio(terminal.getRadio())
                                        .setPanel(terminal)
                                        .build();

            new LabelBuilder().setColumnIndex(0)
                                    .setRowIndex(8)
                                    .setText("This is a horizontally label.")
                                    .setPanel(terminal)
                                    .build()
                                    .getStrings()[0]
                                    .flipCharactersHorizontally();


            new LabelBuilder().setColumnIndex(0)
                                    .setRowIndex(9)
                                    .setText("This is a vertically label.")
                                    .setPanel(terminal)
                                    .build()
                                    .getStrings()[0]
                                    .flipCharactersVertically();

            new LabelBuilder().setColumnIndex(0)
                                    .setRowIndex(10)
                                    .setText("This is label flipped both ways.")
                                    .setPanel(terminal)
                                    .build()
                                    .getStrings()[0]
                                    .flipCharactersHorizontallyAndVertically();

            new LabelBuilder().setColumnIndex(0)
                                    .setRowIndex(11)
                                    .setText("This is an underlined label.")
                                    .setPanel(terminal)
                                    .build()
                                    .getStrings()[0]
                                    .underlineCharacters();


            final RadioButtonGroup radioButtonGroupOne = new RadioButtonGroup();

            new RadioButtonBuilder().setColumnIndex(0)
                                        .setRowIndex(12)
                                        .setText("Radio Button, Option A, Group 1")
                                        .setRadio(terminal.getRadio())
                                        .setPanel(terminal)
                                        .setRadioButtonGroup(radioButtonGroupOne)
                                        .build();

            new RadioButtonBuilder().setColumnIndex(0)
                                        .setRowIndex(13)
                                        .setText("Radio Button, Option B, Group 1")
                                        .setRadio(terminal.getRadio())
                                        .setPanel(terminal)
                                        .setRadioButtonGroup(radioButtonGroupOne)
                                        .build();

            new RadioButtonBuilder().setColumnIndex(0)
                                        .setRowIndex(14)
                                        .setText("Radio Button, Option C, Group 1")
                                        .setRadio(terminal.getRadio())
                                        .setPanel(terminal)
                                        .setRadioButtonGroup(radioButtonGroupOne)
                                        .build();

            final RadioButtonGroup radioButtonGroupTwo = new RadioButtonGroup();

            new RadioButtonBuilder().setColumnIndex(0)
                    .setRowIndex(15)
                    .setText("Radio Button, Option A, Group 2")
                    .setRadio(terminal.getRadio())
                    .setPanel(terminal)
                    .setRadioButtonGroup(radioButtonGroupTwo)
                    .build();

            new RadioButtonBuilder().setColumnIndex(0)
                    .setRowIndex(16)
                    .setText("Radio Button, Option B, Group 2")
                    .setRadio(terminal.getRadio())
                    .setPanel(terminal)
                    .setRadioButtonGroup(radioButtonGroupTwo)
                    .build();

            new RadioButtonBuilder().setColumnIndex(0)
                    .setRowIndex(17)
                    .setText("Radio Button, Option C, Group 2")
                    .setRadio(terminal.getRadio())
                    .setPanel(terminal)
                    .setRadioButtonGroup(radioButtonGroupTwo)
                    .build();

            terminal.getCurrentScreen().getStrings()[0].applyColorGradient(Color.PINK, Color.GREEN, true);
            terminal.getCurrentScreen().getStrings()[0].applyColorGradient(Color.GREEN, Color.PINK, false);

            terminal.getCurrentScreen().getStrings()[1].applyColorGradient(Color.BLACK, Color.WHITE, true);
            terminal.getCurrentScreen().getStrings()[1].applyColorGradient(Color.WHITE, Color.BLACK, false);

            terminal.getCurrentScreen().getStrings()[2].applyColorGradient(Color.RED, Color.BLUE, true);
            terminal.getCurrentScreen().getStrings()[2].applyColorGradient(Color.BLUE, Color.RED, false);

            // Add row markers:
            for (int i = 0 ; i < screen.getHeight() ; i++) {
                new LabelBuilder().setColumnIndex(73)
                                        .setRowIndex(i)
                                        .setText("Row " + i)
                                        .setPanel(terminal)
                                        .build();
            }

            terminal.draw();
        } catch (final IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}


import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.builder.LabelBuilder;
import com.valkryst.VTerminal.component.Label;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.shader.blur.FastMotionBlurShader;
import com.valkryst.VTerminal.shader.blur.GaussianBlurShader;
import com.valkryst.VTerminal.shader.blur.MotionBlurShader;
import com.valkryst.VTerminal.shader.character.*;
import com.valkryst.VTerminal.shader.misc.*;

import java.io.IOException;

public class SampleShaders {
    public static void main(final String[] args) throws IOException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final Screen screen = new Screen(100, 33, font);

        final LabelBuilder labelBuilder = new LabelBuilder();
        labelBuilder.setPosition(0, 0);

        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using No Shader.");
        Label label = labelBuilder.build();
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using FastMotionBlurShader (Angle 90, Distance 5).");
        label = labelBuilder.build();
        final FastMotionBlurShader fastMotionBlurShader = new FastMotionBlurShader();
        fastMotionBlurShader.setAngle(90);
        fastMotionBlurShader.setDistance(5);
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.addShaders(fastMotionBlurShader);
        }
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using GaussianBlurShader.");
        label = labelBuilder.build();
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.addShaders(new GaussianBlurShader());
        }
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using MotionBlurShader (Angle 90, Distance 5).");
        label = labelBuilder.build();
        final MotionBlurShader motionBlurShader = new MotionBlurShader();
        motionBlurShader.setAngle(90);
        motionBlurShader.setDistance(5);
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.addShaders(motionBlurShader);
        }
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using FlipShader.");
        label = labelBuilder.build();
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.setFlippedVertically(true);
        }
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using FlipShader.");
        label = labelBuilder.build();
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.setFlippedHorizontally(true);
        }
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using FlipShader.");
        label = labelBuilder.build();
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.setFlippedVertically(true);
            tile.setFlippedHorizontally(true);
        }
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using SharpenShader.");
        label = labelBuilder.build();
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.addShaders(new SharpenShader());
        }
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using TextShadowShader.");
        label = labelBuilder.build();
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.addShaders(new CharShadowShader());
        }
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using TextGlowShader.");
        label = labelBuilder.build();
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.addShaders(new CharGlowShader());
        }
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using TextBoldShader.");
        label = labelBuilder.build();
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.addShaders(new CharBoldShader());
        }
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using EmbossShader.");
        label = labelBuilder.build();
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.addShaders(new EmbossShader());
        }
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using OilPaintShader.");
        label = labelBuilder.build();
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.addShaders(new OilPaintShader());
        }
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using EdgeDetectionShader.");
        label = labelBuilder.build();
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.addShaders(new EdgeDetectionShader());
        }
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using CharEdgeDetectionShader.");
        label = labelBuilder.build();
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.addShaders(new CharEdgeDetectionShader());
        }
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using PixelateShader.");
        label = labelBuilder.build();
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.addShaders(new PixelateShader());
        }
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using ChromeShader.");
        label = labelBuilder.build();
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.addShaders(new ChromeShader());
        }
        screen.addComponent(label);


        labelBuilder.setPosition(0, labelBuilder.getYPosition() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using CharChromeShader.");
        label = labelBuilder.build();
        for (final Tile tile : label.getTiles().getRow(0)) {
            tile.addShaders(new CharChromeShader());
        }
        screen.addComponent(label);


        screen.addCanvasToJFrame();
        screen.draw();
    }
}

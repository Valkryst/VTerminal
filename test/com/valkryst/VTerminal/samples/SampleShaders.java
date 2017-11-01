package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.builder.component.LabelBuilder;
import com.valkryst.VTerminal.component.Label;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.shader.blur.FastMotionBlurShader;
import com.valkryst.VTerminal.shader.blur.GaussianBlurShader;
import com.valkryst.VTerminal.shader.blur.MotionBlurShader;
import com.valkryst.VTerminal.shader.character.CharBoldShader;
import com.valkryst.VTerminal.shader.character.CharGlowShader;
import com.valkryst.VTerminal.shader.character.CharShadowShader;
import com.valkryst.VTerminal.shader.misc.FlipShader;
import com.valkryst.VTerminal.shader.misc.GlowShader;
import com.valkryst.VTerminal.shader.misc.RayShader;
import com.valkryst.VTerminal.shader.misc.SharpenShader;

import java.io.IOException;
import java.net.URISyntaxException;

public class SampleShaders {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);
        builder.setWidthInCharacters(90);

        final Panel panel = builder.build();

        final Screen screen = panel.getScreen();

        Thread.sleep(50);

        final LabelBuilder labelBuilder = new LabelBuilder();
        labelBuilder.setColumnIndex(0);
        labelBuilder.setRowIndex(0);

        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using No Shader.");
        Label label = labelBuilder.build();
        screen.addComponent(label);


        labelBuilder.setRowIndex(labelBuilder.getRowIndex() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using FastMotionBlurShader (Angle 90, Distance 5).");
        label = labelBuilder.build();
        final FastMotionBlurShader fastMotionBlurShader = new FastMotionBlurShader();
        fastMotionBlurShader.setAngle(90);
        fastMotionBlurShader.setDistance(5);
        label.addShaders(fastMotionBlurShader);
        screen.addComponent(label);


        labelBuilder.setRowIndex(labelBuilder.getRowIndex() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using GaussianBlurShader.");
        label = labelBuilder.build();
        label.addShaders(new GaussianBlurShader());
        screen.addComponent(label);


        labelBuilder.setRowIndex(labelBuilder.getRowIndex() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using MotionBlurShader (Angle 90, Distance 5).");
        label = labelBuilder.build();
        final MotionBlurShader motionBlurShader = new MotionBlurShader();
        motionBlurShader.setAngle(90);
        motionBlurShader.setDistance(5);
        label.addShaders(motionBlurShader);
        screen.addComponent(label);


        labelBuilder.setRowIndex(labelBuilder.getRowIndex() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using FlipShader.");
        label = labelBuilder.build();
        FlipShader flipShader = new FlipShader();
        flipShader.setFlippedVertically(true);
        label.addShaders(flipShader);
        screen.addComponent(label);


        labelBuilder.setRowIndex(labelBuilder.getRowIndex() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using FlipShader.");
        label = labelBuilder.build();
        flipShader = new FlipShader();
        flipShader.setFlippedHorizontally(true);
        label.addShaders(flipShader);
        screen.addComponent(label);


        labelBuilder.setRowIndex(labelBuilder.getRowIndex() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using FlipShader.");
        label = labelBuilder.build();
        flipShader = new FlipShader();
        flipShader.setFlippedHorizontally(true);
        flipShader.setFlippedVertically(true);
        label.addShaders(flipShader);
        screen.addComponent(label);


        labelBuilder.setRowIndex(labelBuilder.getRowIndex() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using GlowShader.");
        label = labelBuilder.build();
        label.addShaders(new GlowShader());
        screen.addComponent(label);


        labelBuilder.setRowIndex(labelBuilder.getRowIndex() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using RayShader.");
        label = labelBuilder.build();
        label.addShaders(new RayShader());
        screen.addComponent(label);


        labelBuilder.setRowIndex(labelBuilder.getRowIndex() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using SharpenShader.");
        label = labelBuilder.build();
        label.addShaders(new SharpenShader());
        screen.addComponent(label);


        labelBuilder.setRowIndex(labelBuilder.getRowIndex() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using TextShadowShader.");
        label = labelBuilder.build();
        label.addShaders(new CharShadowShader());
        screen.addComponent(label);


        labelBuilder.setRowIndex(labelBuilder.getRowIndex() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using TextGlowShader.");
        label = labelBuilder.build();
        label.addShaders(new CharGlowShader());
        screen.addComponent(label);


        labelBuilder.setRowIndex(labelBuilder.getRowIndex() + 1);
        labelBuilder.setText("Sample text 123456789!@#$%^&*()_+-=. Using TextBoldShader.");
        label = labelBuilder.build();
        label.addShaders(new CharBoldShader());
        screen.addComponent(label);

        panel.draw();
    }
}

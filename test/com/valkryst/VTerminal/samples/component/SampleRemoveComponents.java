package com.valkryst.VTerminal.samples.component;

import com.valkryst.VJSON.VJSONLoader;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.builder.component.ScreenBuilder;
import com.valkryst.VTerminal.component.Component;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;

public class SampleRemoveComponents {
     public static void main (final String[] args) throws IOException, URISyntaxException, InterruptedException, ParseException {
         final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

         final PanelBuilder builder = new PanelBuilder();
         builder.setFont(font);

         final Panel panel = builder.build();

         Thread.sleep(100);

         final ScreenBuilder screenBuilder = new ScreenBuilder();
         VJSONLoader.loadFromJSON(screenBuilder, System.getProperty("user.dir") + "/res_test/Sample Screen.json");

         panel.swapScreen(screenBuilder.build());

         panel.draw();

         // Remove all components.
         Thread.sleep(3000);

         for (final Component component : panel.getScreen().getComponents()) {
             panel.removeComponent(component);
         }

         panel.draw();
     }
}

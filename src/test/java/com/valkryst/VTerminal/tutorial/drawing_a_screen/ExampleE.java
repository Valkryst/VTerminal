package com.valkryst.VTerminal.tutorial.drawing_a_screen;

import com.valkryst.VTerminal.component.VFrame;
import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class ExampleE {
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(VTerminalLookAndFeel.getInstance(24));
		} catch (final UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> {
			final var frame = new VFrame(40, 20);
			frame.setVisible(true);
			frame.pack();
			frame.setLocationRelativeTo(null);

			final var panel = frame.getContentPane();
			panel.setBackground(Color.MAGENTA);

			for (int y = 0 ; y < panel.getHeightInTiles() ; y++) {
				for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
					panel.setCodePointAt(x, y, getRandomCodePoint());
				}
			}
		});
	}

	private static int getRandomCodePoint() {
		return ThreadLocalRandom.current().nextInt(33, 126);
	}
}
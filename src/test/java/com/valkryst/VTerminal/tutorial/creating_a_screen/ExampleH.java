package com.valkryst.VTerminal.tutorial.creating_a_screen;

import com.valkryst.VTerminal.component.VPanel;
import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;

public class ExampleH {
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(VTerminalLookAndFeel.getInstance());
		} catch (final UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> {
			final var panel = new VPanel(40, 20);

			final var frame = new JFrame();
			frame.add(panel);
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

			frame.setVisible(true);
			frame.pack();
			frame.setLocationRelativeTo(null);

			panel.setCodePointAt(0, 0, 'A');
		});
	}
}

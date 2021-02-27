package com.valkryst.VTerminal.tutorial.drawing_a_screen;

import com.jhlabs.image.GaussianFilter;
import com.valkryst.VTerminal.component.VFrame;
import com.valkryst.VTerminal.image.SequentialOp;
import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;

public class ExampleF {
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

			final var sequentialOp = new SequentialOp(new GaussianFilter(6));

			final var panel = frame.getContentPane();
			panel.setCodePointAt(0, 0, 'A');
			panel.setSequentialImageOpAt(0, 0, sequentialOp);
		});
	}
}
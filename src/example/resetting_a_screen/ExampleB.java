package resetting_a_screen;

import com.valkryst.VTerminal.component.VFrame;
import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ExampleB {
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
			for (int y = 0 ; y < panel.getHeightInTiles() ; y++) {
				for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
					panel.setCodePointAt(x, y, getRandomCodePoint());
				}
			}

			Executors.newSingleThreadScheduledExecutor().schedule(() -> {
				panel.resetCodePoints();
				SwingUtilities.invokeLater(panel::repaint);
			}, 2, TimeUnit.SECONDS);
		});
	}

	private static int getRandomCodePoint() {
		return ThreadLocalRandom.current().nextInt(33, 127);
	}
}

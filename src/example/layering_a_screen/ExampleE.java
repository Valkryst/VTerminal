package layering_a_screen;

import com.valkryst.VTerminal.component.VPanel;
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
			final var layeredPane = new JLayeredPane();
			layeredPane.setLayout(new OverlayLayout(layeredPane));

			final var bottomPanel = new VPanel(40, 20);
			bottomPanel.setOpaque(true);
			layeredPane.add(bottomPanel, Integer.valueOf(0));

			final var topPanel = new VPanel(20, 20);
			topPanel.setOpaque(true);
			layeredPane.add(topPanel, Integer.valueOf(1));

			final var frame = new JFrame();
			frame.add(layeredPane);
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setVisible(true);
			frame.pack();
			frame.setLocationRelativeTo(null);

			for (int y = 0 ; y < bottomPanel.getHeightInTiles() ; y++) {
				for (int x = 0 ; x < bottomPanel.getWidthInTiles() ; x++) {
					bottomPanel.setCodePointAt(x, y, getRandomCodePoint());
				}
			}

			bottomPanel.setBackground(Color.RED);
			topPanel.setBackground(new Color(0, 0, 255, 100));
		});
	}

	private static int getRandomCodePoint() {
		return ThreadLocalRandom.current().nextInt(33, 127);
	}
}
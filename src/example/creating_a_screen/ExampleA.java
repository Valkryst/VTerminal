package creating_a_screen;

import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;

public class ExampleA {
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(VTerminalLookAndFeel.getInstance());
		} catch (final UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}

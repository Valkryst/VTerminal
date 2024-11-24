package com.valkryst.VTerminal.plaf;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;

public class VBasicArrowButton extends BasicArrowButton {
	/**
	 * Constructs a {@link VBasicArrowButton}.
	 *
	 * @param direction See {@link BasicArrowButton#BasicArrowButton(int)}.
	 */
	public VBasicArrowButton(final int direction) {
		super(direction);
		super.setBackground(UIManager.getColor("ScrollBar.background"));
		super.setForeground(UIManager.getColor("ScrollBar.foreground"));
	}
}

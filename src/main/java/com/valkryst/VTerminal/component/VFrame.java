package com.valkryst.VTerminal.component;

import lombok.Getter;
import lombok.NonNull;

import javax.swing.*;
import java.awt.*;

public class VFrame extends JFrame {
	/** Content pane of the frame. */
	@Getter private final VPanel contentPane;

	/** The preferred size of the frame, when it's not in full-screen mode. */
	private Dimension preferredSize;

	/** Whether the frame is in full-screen mode. */
	@Getter private boolean isFullScreen = false;

	/**
	 * Constructs a new instance of {@code VFrame}.
	 *
	 * @param widthInTiles Width of the frame, in tiles.
	 * @param heightInTiles Height of the frame, in tiles.
	 */
	public VFrame(final int widthInTiles, final int heightInTiles) {
		this(new VPanel(widthInTiles, heightInTiles));
	}

	/**
	 * Constructs a new instance of {@code VFrame}.
	 *
	 * @param contentPane A content pane for the frame.
	 */
	public VFrame(final @NonNull VPanel contentPane) {
		this.contentPane = contentPane;
		super.setContentPane(contentPane);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Retrieves the frame's graphics device.
	 *
	 * @return The frame's graphics device.
	 */
	public GraphicsDevice getGraphicsDevice() {
		return this.getGraphicsConfiguration().getDevice();
	}

	/**
	 * Retrieves all supported display modes.
	 *
	 * @return All supported display modes.
	 */
	public DisplayMode[] getDisplayModes() {
		return this.getGraphicsDevice().getDisplayModes();
	}

	/**
	 * Determines whether full-screen mode is supported.,
	 *
	 * @return Whether full-screen mode is supported.
	 */
	public boolean isFullScreenSupported() {
		return this.getGraphicsDevice().isFullScreenSupported();
	}

	/**
	 * Determines whether changing the display mode is supported.
	 *
	 * @return Whether changing the display mode is supported.
	 */
	public boolean isDisplayChangeSupported() {
		return this.getGraphicsDevice().isDisplayChangeSupported();
	}

	/**
	 * En/disables full-screen mode.
	 *
	 * @param fullScreen Whether to en/disable full-screen mode.
	 */
	public void setFullScreen(final boolean fullScreen) {
        this.setFullScreen(fullScreen, null);
	}

	/**
	 * En/disables full-screen mode and changes the display mode if one is
	 * given.
	 *
	 * @param fullScreen Whether to en/disable full-screen mode.
	 * @param displayMode A display mode.
	 */
	public void setFullScreen(final boolean fullScreen, final DisplayMode displayMode) {
		if (!this.isFullScreenSupported()) {
			throw new UnsupportedOperationException("The current graphics device does not support full-screen mode.");
		}

		if (displayMode != null) {
			if (!fullScreen) {
				throw new IllegalArgumentException("The display mode can only be changed when enabling to full-screen mode.");
			}

			if (!this.isDisplayChangeSupported()) {
				throw new UnsupportedOperationException("The current graphics device does not support changing the display mode.");
			}
		}

		super.dispose();
		super.setUndecorated(fullScreen);
        this.getGraphicsDevice().setFullScreenWindow(fullScreen ? this : null);

		if (displayMode != null) {
            this.getGraphicsDevice().setDisplayMode(displayMode);
		}

		if (fullScreen) {
			super.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
			super.revalidate();
		} else {
			super.setPreferredSize(preferredSize);
			super.setVisible(true);
			super.pack();
			super.setLocationRelativeTo(null);
		}

		isFullScreen = fullScreen;
		super.requestFocus();
	}

	@Override
	public void setPreferredSize(final @NonNull Dimension preferredSize) {
		this.preferredSize = preferredSize;

		if (!isFullScreen) {
			super.setPreferredSize(preferredSize);
		}
	}

	@Override
	public void setUndecorated(final boolean ignored) {
		throw new UnsupportedOperationException("This method is used internally and should not be called.");
	}
}

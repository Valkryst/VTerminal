package com.valkryst.VTerminal.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class VFrameTest {
	@Test
	public void canCreateFrame() {
		new VFrame(10, 10);
	}

	@Test
	public void cannotCreateFrameWithNonPositiveWidth() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new VFrame(0, 10);
		});
	}

	@Test
	public void cannotCreateFrameWithNonPositiveHeight() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new VFrame(10, 0);
		});
	}

	@Test
	public void canCreateFrameWithPanel() {
		final var panel = new VPanel(10, 10);
		final var frame = new VFrame(panel);
		Assertions.assertEquals(panel, frame.getContentPane());
	}

	@Test
	public void cannotCreateFrameWithNullVPanel() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			new VFrame(null);
		});
	}

	@Test
	public void canGetGraphicsDevice() {
		final var frame = new VFrame(10, 10);
		final var device = frame.getGraphicsDevice();
		Assertions.assertNotNull(device);
	}

	@Test
	public void canGetDisplayModes() {
		final var frame = new VFrame(10, 10);
		final var displayModes = frame.getDisplayModes();
		Assertions.assertNotNull(displayModes);
		Assertions.assertTrue(displayModes.length > 0);
	}

	@Test
	public void canDetermineIfFullScreenModeIsSupported() {
		final var frame = new VFrame(10, 10);
		frame.isFullScreenSupported();
	}

	@Test
	public void canDetermineIfDisplayChangeIsSupported() {
		final var frame = new VFrame(10, 10);
		frame.isDisplayChangeSupported();
	}

	@Test
	public void canEnableFullScreenMode() {
		final var frame = new VFrame(10, 10);
		if (frame.isFullScreenSupported()) {
			frame.setFullScreen(true);
		}
	}

	@Test
	public void canDisableFullScreenMode() {
		final var frame = new VFrame(10, 10);
		if (frame.isFullScreenSupported()) {
			frame.setFullScreen(true);
			frame.setFullScreen(false);
		}
	}

	@Test
	public void canEnableFullScreenModeWithDisplayMode() {
		final var frame = new VFrame(10, 10);
		if (frame.isFullScreenSupported()) {
			final var displayModes = frame.getDisplayModes();
			frame.setFullScreen(true, displayModes[0]);
		}
	}

	@Test
	public void canEnableFullScreenModeWithNullDisplayMode() {
		final var frame = new VFrame(10, 10);
		if (frame.isFullScreenSupported()) {
			frame.setFullScreen(true, null);
		}
	}

	@Test
	public void canDisableFullScreenModeWithNullDisplayMode() {
		final var frame = new VFrame(10, 10);
		if (frame.isFullScreenSupported()) {
			frame.setFullScreen(true, null);
			frame.setFullScreen(false, null);
		}
	}

	@Test
	public void cannotEnableFullScreenModeUnlessItIsSupported() {
		final var frame = Mockito.spy(new VFrame(10, 10));
		Mockito.when(frame.isFullScreenSupported()).thenReturn(false);

		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			frame.setFullScreen(true, null);
		});
	}

	@Test
	public void cannotDisableFullScreenModeWhileSpecifyingADisplayMode() {
		final var frame = Mockito.spy(new VFrame(10, 10));
		Mockito.when(frame.isDisplayChangeSupported()).thenReturn(false);

		final var displayModes = frame.getDisplayModes();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			frame.setFullScreen(false, displayModes[0]);
		});
	}

	@Test
	public void cannotEnableFullScreenModeWithADisplayModeUnlessDisplayChangingIsSupported() {
		final var frame = Mockito.spy(new VFrame(10, 10));
		Mockito.when(frame.isDisplayChangeSupported()).thenReturn(false);

		/*
		 * When running under xvfb, the getDisplayModes() function will
		 * return an empty array. I don't know a way around this, so we
		 * need a check to skip this test.
		 */
		final var displayModes = frame.getDisplayModes();
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			frame.setFullScreen(true, displayModes[0]);
		});
	}

	@Test
	public void cannotUseTheSetUndecoratedMethod() {
		final var frame = new VFrame(10, 10);
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			frame.setUndecorated(true);
		});
	}
}

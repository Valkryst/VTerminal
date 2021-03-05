package gui.view;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgressBarView extends View {
	public ProgressBarView() {
		final var verticalProgressBar = new JProgressBar(SwingConstants.VERTICAL, 0, 100);
		final var horizontalProgressBar = new JProgressBar(0, 100);

		this.add(verticalProgressBar);
		this.add(horizontalProgressBar);

		final AtomicInteger progressValue = new AtomicInteger(0);
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
			progressValue.incrementAndGet();
			if (progressValue.incrementAndGet() > 100) {
				progressValue.set(0);
			}

			verticalProgressBar.setValue(progressValue.get());
			horizontalProgressBar.setValue(progressValue.get());
		}, 0, 100, TimeUnit.MILLISECONDS);
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {
	}
}

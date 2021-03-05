package repainting_a_screen;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ExampleA {
	public static void main(final String[] args) {
		final var previousTime = new AtomicLong(System.nanoTime());

		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
			final var currentTime = System.nanoTime();
			final var elapsedTime = currentTime - previousTime.get();
			previousTime.set(currentTime);

			System.out.println("Time Elapsed (ms): " + TimeUnit.NANOSECONDS.toMillis(elapsedTime));
		}, 0, 16, TimeUnit.MILLISECONDS);
	}
}

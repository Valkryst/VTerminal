package resetting_a_screen;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExampleA {
	public static void main(final String[] args) {
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			System.out.println("Hello, World!");
			System.exit(0);
		}, 2, TimeUnit.SECONDS);
	}
}

package com.valkryst.VTerminal.example;

import com.valkryst.VTerminal.component.VFrame;
import com.valkryst.VTerminal.example.view.View;
import lombok.Getter;
import lombok.NonNull;

import javax.swing.*;
import java.awt.*;

public class Display {
    /** Singleton instance. */
    private final static Display INSTANCE = new Display();

    @Getter private final JFrame frame = new VFrame(80, 40);

    /** Constructs a new Display. */
    private Display() {
        frame.setBackground(Color.BLACK);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null); // Must be called after pack()
    }

    public void addView(final @NonNull View view) {
    	frame.add(view);
    	frame.revalidate();
    	view.requestFocusInWindow();
	}

    public void addView(final @NonNull View view, final @NonNull Object constraints) {
		frame.add(view, constraints);
		frame.revalidate();
		view.requestFocusInWindow();
	}

    /**
     * Removes a view from the frame.
     *
     * @param view The view to remove.
     */
    public void removeView(final View view) {
        if (view != null) {
            frame.remove(view);
            frame.revalidate();
        }
    }

    /**
     * Retrieves the singleton instance.
     *
     * @return The singleton instance.
     */
    public static Display getInstance() {
        return INSTANCE;
    }

    public void setLayout(final LayoutManager layout) {
    	frame.setLayout(layout);
	}
}

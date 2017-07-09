package com.valkryst.VTerminal.component;

import java.util.ArrayList;
import java.util.List;

public class RadioButtonGroup {
    /** The buttons in the group. */
    private final List<RadioButton> buttons = new ArrayList<>();

    /**
     * Sets the specified button as checked and un-checks all other buttons.
     *
     * @param button
     *         The button.
     */
    public void setCheckedButton(final RadioButton button) {
        if (button == null) {
            return;
        }

        for (int i = 0 ; i < buttons.size() ; i++) {
            if (buttons.get(i).equals(button)) {
                buttons.get(i).check();
            } else {
                buttons.get(i).uncheck();
            }
        }
    }

    /**
     * Adds a button to the group.
     *
     * @param button
     *         The button.
     */
    public void addRadioButton(final RadioButton button) {
        if (button == null) {
            return;
        }

        if (buttons.size() == 0) {
            buttons.add(button);
            button.check();
        } else {
            buttons.add(button);
        }
    }

    /** Removes a button from the group.
     *
     * @param button
     *         The button.
     */
    public void removeRadioButton(final RadioButton button) {
        if (button == null) {
            return;
        }

        buttons.remove(button);
    }
}

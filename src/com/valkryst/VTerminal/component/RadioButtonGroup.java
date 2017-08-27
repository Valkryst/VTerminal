package com.valkryst.VTerminal.component;

import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class RadioButtonGroup {
    /** The buttons in the group. */
    private final List<RadioButton> buttons = new ArrayList<>();

    /**
     * Sets the specified button as checked and un-checks all other buttons.
     *
     * @param button
     *         The button.
     *
     * @throws NullPointerException
     *         If the button is null.
     */
    public void setCheckedButton(final @NonNull RadioButton button) {
        for (int i = 0 ; i < buttons.size() ; i++) {
            if (buttons.get(i).equals(button)) {
                buttons.get(i).setChecked(true);
            } else {
                buttons.get(i).setChecked(false);
            }
        }
    }

    /**
     * Adds a button to the group.
     *
     * @param button
     *         The button.
     *
     * @throws NullPointerException
     *         If the button is null.
     */
    public void addRadioButton(final @NonNull RadioButton button) {
        if (buttons.size() == 0) {
            buttons.add(button);
            button.setChecked(true);
        } else {
            buttons.add(button);
        }
    }

    /** Removes a button from the group.
     *
     * @param button
     *         The button.
     *
     * @throws NullPointerException
     *         If the button is null.
     */
    public void removeRadioButton(final @NonNull RadioButton button) {
        buttons.remove(button);
    }
}

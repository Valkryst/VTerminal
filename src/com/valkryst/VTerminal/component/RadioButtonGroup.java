package com.valkryst.VTerminal.component;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     *
     * @throws NullPointerException
     *         If the button is null.
     */
    public void addRadioButton(final @NonNull RadioButton button) {
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
     *
     * @throws NullPointerException
     *         If the button is null.
     */
    public void removeRadioButton(final @NonNull RadioButton button) {
        buttons.remove(button);
    }
}

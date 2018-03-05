package com.valkryst.VTerminal.palette;

import com.valkryst.VTerminal.misc.ColorFunctions;
import lombok.Getter;

import java.awt.Color;

public class P24PhosphorColorPalette extends ColorPalette {
    @Getter private final Color defaultBackground = Color.BLACK;
    @Getter private final Color defaultForeground = new Color(51, 255, 51, 255);

    @Getter private final Color button_defaultBackground = defaultBackground;
    @Getter private final Color button_defaultForeground = defaultForeground;
    @Getter private final Color button_hoverBackground = defaultForeground;
    @Getter private final Color button_hoverForeground = ColorFunctions.shade(button_hoverBackground, 0.5);
    @Getter private final Color button_pressedBackground = ColorFunctions.shade(button_hoverBackground, 0.25);
    @Getter private final Color button_pressedForeground = ColorFunctions.shade(button_hoverForeground, 0.25);

    @Getter private final Color checkBox_defaultBackground = button_defaultBackground;
    @Getter private final Color checkBox_defaultForeground = button_defaultForeground;
    @Getter private final Color checkBox_hoverBackground = button_hoverBackground;
    @Getter private final Color checkBox_hoverForeground = button_hoverForeground;
    @Getter private final Color checkBox_checkedBackground = button_defaultBackground;
    @Getter private final Color checkBox_checkedForeground = defaultForeground;

    @Getter private final Color label_defaultBackground = defaultBackground;
    @Getter private final Color label_defaultForeground = defaultForeground;

    @Getter private final Color layer_defaultBackground = defaultBackground;
    @Getter private final Color layer_defaultForeground = defaultForeground;

    @Getter private final Color progressBar_incompleteBackground = defaultBackground;
    @Getter private final Color progressBar_incompleteForeground = ColorFunctions.shade(defaultForeground, 0.5);
    @Getter private final Color progressBar_completeBackground = defaultBackground;
    @Getter private final Color progressBar_completeForeground = defaultForeground;

    @Getter private final Color radioButton_defaultBackground = button_defaultBackground;
    @Getter private final Color radioButton_defaultForeground = button_defaultForeground;
    @Getter private final Color radioButton_hoverBackground = button_hoverBackground;
    @Getter private final Color radioButton_hoverForeground = button_hoverForeground;
    @Getter private final Color radioButton_pressedBackground = defaultBackground;
    @Getter private final Color radioButton_pressedForeground = defaultForeground;

    @Getter private final Color textArea_defaultBackground = ColorFunctions.tint(defaultBackground, 0.15);
    @Getter private final Color textArea_defaultForeground = defaultForeground;
    @Getter private final Color textArea_caretBackground = textArea_defaultForeground;
    @Getter private final Color textArea_caretForeground = textArea_defaultBackground;
}

package com.valkryst.VTerminal.palette;

import com.valkryst.VTerminal.misc.ColorFunctions;
import lombok.Getter;

import java.awt.Color;

public class P1PhosphorColorPalette extends ColorPalette {
    @Getter private Color defaultBackground = Color.BLACK;
    @Getter private Color defaultForeground = new Color(102, 255, 102, 255);

    @Getter private Color button_defaultBackground = defaultBackground;
    @Getter private Color button_defaultForeground = defaultForeground;
    @Getter private Color button_hoverBackground = defaultForeground;
    @Getter private Color button_hoverForeground = ColorFunctions.shade(button_hoverBackground, 0.5);
    @Getter private Color button_pressedBackground = ColorFunctions.shade(button_hoverBackground, 0.25);
    @Getter private Color button_pressedForeground = ColorFunctions.shade(button_hoverForeground, 0.25);

    @Getter private Color checkBox_defaultBackground = button_defaultBackground;
    @Getter private Color checkBox_defaultForeground = button_defaultForeground;
    @Getter private Color checkBox_hoverBackground = button_hoverBackground;
    @Getter private Color checkBox_hoverForeground = button_hoverForeground;
    @Getter private Color checkBox_checkedBackground = button_defaultBackground;
    @Getter private Color checkBox_checkedForeground = defaultForeground;

    @Getter private Color layer_defaultBackground = defaultBackground;
    @Getter private Color layer_defaultForeground = defaultForeground;

    @Getter private Color progressBar_incompleteBackground = defaultBackground;
    @Getter private Color progressBar_incompleteForeground = ColorFunctions.shade(defaultForeground, 0.5);
    @Getter private Color progressBar_completeBackground = defaultBackground;
    @Getter private Color progressBar_completeForeground = defaultForeground;

    @Getter private Color radioButton_defaultBackground = button_defaultBackground;
    @Getter private Color radioButton_defaultForeground = button_defaultForeground;
    @Getter private Color radioButton_hoverBackground = button_hoverBackground;
    @Getter private Color radioButton_hoverForeground = button_hoverForeground;
    @Getter private Color radioButton_pressedBackground = defaultBackground;
    @Getter private Color radioButton_pressedForeground = defaultForeground;

    @Getter private Color textArea_defaultBackground = new Color(0xFF68D0FF, true);
    @Getter private Color textArea_defaultForeground = new Color(0xFF8E999E, true);
    @Getter private Color textArea_caretBackground = textArea_defaultForeground;
    @Getter private Color textArea_caretForeground = textArea_defaultBackground;
}

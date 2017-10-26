package com.valkryst.VTerminal.component;


import com.valkryst.VTerminal.builder.component.TextFieldBuilder;
import lombok.NonNull;
import lombok.ToString;

@ToString
public class TextField extends TextArea {
    /**
     * Constructs a new AsciiTextField.
     *
     * @param builder
     *         The builder to use.
     *
     * @throws NullPointerException
     *         If the builder is null.
     */
    public TextField(final @NonNull TextFieldBuilder builder) {
        super(builder);
    }
}

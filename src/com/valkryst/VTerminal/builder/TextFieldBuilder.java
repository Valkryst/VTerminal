package com.valkryst.VTerminal.builder;

import com.valkryst.VTerminal.component.TextField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class TextFieldBuilder extends TextAreaBuilder {
    @Override
    public TextField build() {
        checkState();
        return new TextField(this);
    }

    /**
     * Checks the current state of the builder.
     *
     * @throws IllegalArgumentException
     *          If the width or max characters is less than one.
     */
    protected void checkState() throws NullPointerException {
        super.checkState();

        super.getDimensions().setSize(super.getDimensions().getWidth(), 1);

        if (super.getMaxHorizontalCharacters() < 1) {
            throw new IllegalArgumentException("The maximum characters cannot be less than one.");
        }

        if (super.getMaxHorizontalCharacters() < super.getDimensions().width) {
            super.setMaxHorizontalCharacters(super.getDimensions().width);
        }

        if (super.getMaxVerticalCharacters() != 1) {
            super.setMaxVerticalCharacters(1);
        }

        super.setPageUpKeyEnabled(false);
        super.setPageDownKeyEnabled(false);
        super.setUpArrowKeyEnabled(false);
        super.setDownArrowKeyEnabled(false);
        super.setEnterKeyEnabled(false);
    }

    @Override
    public void reset() {
        super.reset();

        super.getDimensions().setSize(4, 1);
        
        super.setMaxVerticalCharacters(1);

        super.setPageUpKeyEnabled(false);
        super.setPageDownKeyEnabled(false);
        super.setUpArrowKeyEnabled(false);
        super.setDownArrowKeyEnabled(false);
        super.setEnterKeyEnabled(false);
    }
}
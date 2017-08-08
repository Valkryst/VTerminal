package com.valkryst.VTerminal.misc;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class IntRange {
    /** The starting value of the range. */
    @Getter private int start;
    /** The ending value of the range. */
    @Getter private int end;

    /**
     * Constructs a new IntRange.
     *
     * @param start
     *        The starting value of the range.
     *
     * @param end
     *        The ending value of the range.
     *
     * @throws IllegalArgumentException
     *        If the begin value is larger than the end value.
     */
    public IntRange(final int start, final int end) {
        this.start = start;
        this.end = end;

        if (start > end) {
            throw new IllegalArgumentException("The start value (" + start + ") is larger than the end value ("
                                               + end + ").");
        }
    }

    @Override
    public String toString() {
        return "IntRange(" + start + ", " + end + ")";
    }
}

package com.valkryst.AsciiPanel.misc;

import lombok.Getter;

public class IntRange {
    /** The beginning value of the range. */
    @Getter private int begin;
    /** The ending value of the range. */
    @Getter private int end;

    /**
     * Constructs a new IntRange.
     *
     * @param begin
     *          The beginning value of the range.
     *
     * @param end
     *          The ending value of the range.
     */
    public IntRange(final int begin, final int end) {
        this.begin = begin;
        this.end = end;
        clampValuesToRange(begin, end);
    }

    /**
     * Ensures that the begin and end values are within the specified minimum and maximum values.
     *
     * If the begin or end values are below the minimum, then they are set to the minimum.
     * If the begin or end values are above the maximum, then they are set to the maximum.
     * If begin is greater than end, then begin is set to equal end.
     * If end is less than begin, then end is set to equal begin.
     *
     * @param minimum
     *         The minimum value.
     *
     * @param maximum
     *         The maximum value.
     */
    public void clampValuesToRange(final int minimum, final int maximum) {
        if (begin < minimum) {
            begin = minimum;
        }

        if (begin > maximum) {
            begin = maximum;
        }

        if (end < minimum) {
            end = minimum;
        }

        if (end > maximum) {
            end = maximum;
        }

        if (begin > end) {
            begin = end;
        }
    }
}

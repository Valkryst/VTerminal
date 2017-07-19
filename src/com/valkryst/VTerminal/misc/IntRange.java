package com.valkryst.VTerminal.misc;

import lombok.Getter;

import java.util.Objects;

public class IntRange {
    /** The starting value of the range. */
    @Getter private int begin;
    /** The ending value of the range. */
    @Getter private int end;

    /**
     * Constructs a new IntRange.
     *
     * @param begin
     *          The starting value of the range.
     *
     * @param end
     *          The ending value of the range.
     *
     * @throws IllegalArgumentException
     *         If the begin value is larger than the end value.
     */
    public IntRange(final int begin, final int end) {
        this.begin = begin;
        this.end = end;

        if (begin > end) {
            throw new IllegalArgumentException("The begin value (" + begin + ") is larger than the end value ("
                                               + end + ").");
        }
    }

    @Override
    public String toString() {
        return "IntRange(" + begin + ", " + end + ")";
    }

    @Override
    public boolean equals(final Object otherObj) {
        if (otherObj instanceof IntRange == false) {
            return false;
        }

        if (otherObj == this) {
            return true;
        }

        final IntRange otherRange = (IntRange) otherObj;
        boolean isEqual = begin == otherRange.getBegin();
        isEqual &= end == otherRange.getEnd();
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(begin, end);
    }
}

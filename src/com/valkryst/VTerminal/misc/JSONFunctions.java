package com.valkryst.VTerminal.misc;

public class JSONFunctions {
    /**
     * Attempts to parse an object as an integer.
     *
     * @param object
     *        The object.
     *
     * @return
     *        If the object is null, then null is returned.
     *        Else the object is cast to an int and returned.
     */
    public static Integer parseInt(final Object object) {
        if (object == null) {
            return null;
        } else {
            return (int) (long) object;
        }
    }
}

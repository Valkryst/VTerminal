package com.valkryst.VTerminal.misc;

import lombok.NonNull;
import org.json.simple.JSONObject;

public class JSONFunctions {
    /**
     * Retrieves an int element from JSON.
     *
     * @param jsonObject
     *        The object.
     *
     * @param element
     *        The element.
     *
     * @return
     *        If the element's object is null, then null is returned.
     *        Else the element's object is cast to int and returned.
     */
    public static Integer getIntElement(final @NonNull JSONObject jsonObject, final @NonNull String element) {
        final Object object = jsonObject.get(element);

        if (object == null) {
            return null;
        } else {
            return (int) (long) object;
        }
    }
}

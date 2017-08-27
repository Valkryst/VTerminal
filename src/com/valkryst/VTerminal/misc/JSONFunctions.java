package com.valkryst.VTerminal.misc;

import lombok.NonNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Color;

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

    /**
     * Loads a Color from a JSON array.
     *
     * The array is loaded as [red, green, blue] or a [red, green, blue, alpha].
     * Any values after alpha are ignored.
     *
     * @param jsonArray
     *         The array of color values.
     *
     * @return
     *         The Color or null if the jsonArray is null.
     *
     * @throws IllegalStateException
     *         If the array contains fewer than three values.
     */
    public Color loadColorFromJSON(final JSONArray jsonArray) {
        if (jsonArray == null) {
            return null;
        }

        if (jsonArray.size() >= 3) {
            final Integer red = (int) (long) jsonArray.get(0);
            final Integer green = (int) (long) jsonArray.get(1);
            final Integer blue = (int) (long) jsonArray.get(2);
            Integer alpha = jsonArray.size() >= 4 ? (int) (long) jsonArray.get(3) : 255;

            return new Color(red, green, blue, alpha);
        }

        throw new IllegalStateException("Cannot load a color with fewer than 3 values.");
    }
}

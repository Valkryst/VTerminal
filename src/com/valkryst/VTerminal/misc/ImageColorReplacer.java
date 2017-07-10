package com.valkryst.VTerminal.misc;

import java.awt.*;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ImageColorReplacer {
    /** The cache of LookupOps for each foreground/background combination encountered. */
    private final Map<Color, Map<Color, LookupOp>> cachedOperations = new ConcurrentHashMap<>();

    /**
     * Retrieves a color replacement LookupOp, corresponding to
     * a foreground/background color, from the cache.
     *
     * If the operation cannot be found within the cache, then it is
     * created, inserted into the cache, and returned.
     *
     * @param foregroundColor
     *        The foreground color for the operation.
     *
     * @param backgroundColor
     *        The background color for the operation.
     *
     * @return
     *        The color replacement operation.
     */
    public LookupOp retrieveOperation(final Color foregroundColor, final Color backgroundColor) {
        if (foregroundColor == null) {
            throw new IllegalArgumentException("The foreground color cannot be null.");
        }

        if (backgroundColor == null) {
            throw new IllegalArgumentException("The background color cannot be null");
        }

        return retrieveFromCache(foregroundColor, backgroundColor);
    }

    /**
     * Retrieves a color replacement LookupOp from the cache.
     *
     * @param foregroundColor
     *        The foreground color of the operation.
     *
     * @param backgroundColor
     *        The background color of the operation.
     *
     * @return
     *        The operation, if it exists within the cache.
     */
    private LookupOp retrieveFromCache(final Color foregroundColor, final Color backgroundColor) {
        try {
            final Map<Color, LookupOp> relatedOperations = cachedOperations.get(foregroundColor);
            return relatedOperations.get(backgroundColor);
        } catch (final NullPointerException e) {
            return createOperationInCache(foregroundColor, backgroundColor);
        }
    }

    /**
     * Creates a new color replacement LookupOp and inserts it
     * into the cache.
     *
     * @param foregroundColor
     *        The foreground color for the operation.
     *
     * @param backgroundColor
     *        The background color for the operation.
     *
     * @return
     *        The color replacement operation.
     */
    public LookupOp createOperationInCache(final Color foregroundColor, final Color backgroundColor) {
        Map<Color, LookupOp> relatedOperations = cachedOperations.get(foregroundColor);
        final LookupOp operation = newOperation(foregroundColor, backgroundColor);

        if (relatedOperations == null) {
            relatedOperations = new HashMap<>();
            relatedOperations.put(backgroundColor, operation);

            cachedOperations.put(foregroundColor, relatedOperations);
        } else {
            relatedOperations.put(backgroundColor, operation);
        }

        return operation;
    }

    private static LookupOp newOperation(final Color newForegroundColor, final Color newBackgroundColor) {
        byte[] a = new byte[256];
        byte[] r = new byte[256];
        byte[] g = new byte[256];
        byte[] b = new byte[256];

        byte bga = (byte) (newBackgroundColor.getAlpha());
        byte bgr = (byte) (newBackgroundColor.getRed());
        byte bgg = (byte) (newBackgroundColor.getGreen());
        byte bgb = (byte) (newBackgroundColor.getBlue());

        byte fga = (byte) (newForegroundColor.getAlpha());
        byte fgr = (byte) (newForegroundColor.getRed());
        byte fgg = (byte) (newForegroundColor.getGreen());
        byte fgb = (byte) (newForegroundColor.getBlue());

        for (int i = 0; i < 256; i++) {
            if (i == 0) {
                a[i] = bga;
                r[i] = bgr;
                g[i] = bgg;
                b[i] = bgb;
            } else {
                a[i] = fga;
                r[i] = fgr;
                g[i] = fgg;
                b[i] = fgb;
            }
        }

        byte[][] table = {r, g, b, a};
        return new LookupOp(new ByteLookupTable(0, table), null);
    }
}

/*
 * Copyright 2015 - 2018 Michael Rapp
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.tomclaw.bottomsheet.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import static com.tomclaw.bottomsheet.util.Condition.ensureAtLeast;
import static com.tomclaw.bottomsheet.util.Condition.ensureNotNull;

/**
 * An utility class, which provides static methods, which allow to create and edit bitmaps.
 *
 * @author Michael Rapp
 * @since 1.0.0
 */
public final class BitmapUtil {

    /**
     * Creates a new utility class, which provides static methods, which allow to create and edit
     * bitmaps.
     */
    private BitmapUtil() {
    }

    /**
     * Resizes a bitmap to a specific width and height. If the ratio between width and height
     * differs from the bitmap's original ratio, the bitmap is stretched.
     *
     * @param bitmap The bitmap, which should be resized, as an instance of the class {@link Bitmap}. The
     *               bitmap may not be null
     * @param width  The width, the bitmap should be resized to, as an {@link Integer} value in pixels.
     *               The width must be at least 1
     * @param height The height, the bitmap should be resized to, as an {@link Integer} value in pixels.
     *               The height must be at least 1
     * @return The resized bitmap as an instance of the class {@link Bitmap}
     */
    public static Bitmap resize(@NonNull final Bitmap bitmap, final int width, final int height) {
        ensureNotNull(bitmap, "The bitmap may not be null");
        ensureAtLeast(width, 1, "The width must be at least 1");
        ensureAtLeast(height, 1, "The height must be at least 1");
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    /**
     * Creates and returns a bitmap by overlaying it with a specific color.
     *
     * @param bitmap The bitmap, which should be tinted, as an instance of the class {@link Bitmap}. The
     *               bitmap may not be null
     * @param color  The color, which should be used for tinting, as an {@link Integer} value
     * @return The bitmap, which has been created, as an instance of the class {@link Bitmap}
     */
    public static Bitmap tint(@NonNull final Bitmap bitmap, @ColorInt final int color) {
        ensureNotNull(bitmap, "The bitmap may not be null");
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), paint);
        return bitmap;
    }

}
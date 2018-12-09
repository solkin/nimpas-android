/*
   Copyright 2017 Paul LeBeau, Cave Rock Software Ltd.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.caverock.androidsvg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Build;

import org.assertj.core.util.Strings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;

import java.util.List;

import static org.junit.Assert.assertEquals;

@Config(manifest = Config.NONE, sdk = Build.VERSION_CODES.JELLY_BEAN, shadows = {MockCanvas.class, MockPath.class})
@RunWith(RobolectricTestRunner.class)
public class RenderToCanvasWithRectTest {
    @Test
    public void getViewList() throws SVGParseException {
        String test = "<svg viewBox=\"0 0 200 100\">\n" +
                "  <rect width=\"200\" height=\"100\" fill=\"green\"/>\n" +
                "</svg>";
        SVG svg = SVG.getFromString(test);

        Bitmap bm1 = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas bmcanvas1 = new Canvas(bm1);
        svg.renderToCanvas(bmcanvas1);

        List<String> ops = ((MockCanvas) Shadow.extract(bmcanvas1)).getOperations();
        //System.out.println(Strings.concat(ops));
        assertEquals("concat(Matrix(1 0 0 1 0 50))", ops.get(1));
        assertEquals("drawPath('M 0 0 L 200 0 L 200 100 L 0 100 L 0 0', Paint())", ops.get(3));

        Bitmap bm2 = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas bmcanvas2 = new Canvas(bm2);
        svg.renderToCanvas(bmcanvas2, new RectF(50, 50, 150, 150));

        List<String> ops2 = ((MockCanvas) Shadow.extract(bmcanvas2)).getOperations();
        System.out.println(Strings.concat(ops2));
        assertEquals("concat(Matrix(0.5 0 0 0.5 50 75))", ops2.get(1));
        assertEquals("drawPath('M 0 0 L 200 0 L 200 100 L 0 100 L 0 0', Paint())", ops2.get(3));
    }
}

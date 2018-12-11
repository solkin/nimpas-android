package com.caverock.androidsvg;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;

public class SVGDrawable extends PictureDrawable {

    private SVGState svgState;

    public SVGDrawable(SVG svg) {
        super(svg.renderToPicture());
        this.svgState = new SVGState(svg);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
    }

    @Override
    public void draw(Canvas canvas) {
        if (getPicture() != null) {
            Rect bounds = getBounds();
            canvas.save();
            canvas.drawPicture(getPicture(), bounds);
            canvas.restore();
        }
    }

    @Override
    public ConstantState getConstantState() {
        svgState.changingConfigurations = super.getChangingConfigurations();
        return svgState;
    }

    final static class SVGState extends ConstantState {

        int changingConfigurations;
        private SVG svg;

        private SVGState(SVG svg) {
            this.svg = svg;
        }

        @Override
        public Drawable newDrawable() {
            return new SVGDrawable(svg);
        }

        @Override
        public int getChangingConfigurations() {
            return changingConfigurations;
        }

    }

}

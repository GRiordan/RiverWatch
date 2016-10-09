package com.vuw.project1.riverwatch.Report_functionality;


import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * Fixed by Alex on 18/07/2016.
 */
public class ScreenDimensions {
    private static float screenWidth;
    private static float screenHeight;
    private static float screenCenterX;
    private static float screenCenterY;

    public ScreenDimensions(Context context) {
        Point dimens = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getSize(dimens);

        screenWidth = dimens.x;
        screenHeight = dimens.y;
        screenCenterX = screenWidth / 2;
        screenCenterY = screenHeight / 2;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public float getScreenCenterX() {
        return screenCenterX;
    }

    public float getScreenCenterY() {
        return screenCenterY;
    }
}

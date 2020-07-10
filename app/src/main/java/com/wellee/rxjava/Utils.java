package com.wellee.rxjava;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Utils {

    /**
     * 给一张Bitmap添加水印文字。
     *
     * @param bitmap      源图片
     * @param content  水印文本
     * @param textSize 水印字体大小 ，单位pix。
     * @param color    水印字体颜色。
     * @param x        起始坐标x
     * @param y        起始坐标y
     *@param positionFlag  居左/居右
     * @param recycle  是否回收
     * @return 已经添加水印后的Bitmap。
     */
    public static Bitmap addTextWatermark(Bitmap bitmap, String content, int textSize, int color, float x, float y, boolean positionFlag, boolean recycle) {
        if (isEmptyBitmap(bitmap) || content == null)
            return null;
        Bitmap ret = bitmap.copy(bitmap.getConfig(), true);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(ret);
        paint.setColor(color);
        paint.setTextSize(textSize);
        Rect bounds = new Rect();
        paint.getTextBounds(content, 0, content.length(), bounds);
        canvas.drawText(content, bitmap.getWidth() - x - bounds.width() - bounds.left, bitmap.getHeight() - bounds.height() - bounds.top - y, paint);

        if (positionFlag) {
            canvas.drawText(content, x, bitmap.getHeight() - bounds.height() - bounds.top - y, paint);

        } else {
            canvas.drawText(content, bitmap.getWidth() - x - bounds.width() - bounds.left, bitmap.getHeight() - bounds.height() - bounds.top - y, paint);

        }
        if (recycle && !bitmap.isRecycled())
            bitmap.recycle();
        return ret;
    }

    /**
     * Bitmap对象是否为空。
     */
    public static boolean isEmptyBitmap(Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }
}

package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class DrawingTheCharacter extends View {

    protected Bitmap character;
    GamePractice gamepractice;
    float x; float y;
    Paint p;

    public DrawingTheCharacter(Context context) {
        super(context);
        character = BitmapFactory.decodeResource(gamepractice.getResources(), R.drawable.character);
        p = new Paint();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (x < canvas.getWidth()) {
            x += 50;
        }
        else {
            x = 10;
        }
        if (y < canvas.getHeight()) {
            y = 400;
        }
        else {
            y = 400;
        }

        canvas.drawBitmap(character, x, y, p);
        invalidate();
    }
}

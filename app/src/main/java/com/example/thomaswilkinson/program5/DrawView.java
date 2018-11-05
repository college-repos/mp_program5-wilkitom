package com.example.thomaswilkinson.program5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {
    Paint black, other;
    public Context myContext;
    public int board[][];
    int mheight = 0, mwidth = 0;
    int incr;
    int leftside, rightside, boardwidth;

    public DrawView(Context context) {
        super(context);
        myContext = context;
        build();
    }

    public DrawView(Context context, AttributeSet atribset) {
        super(context, atribset);
        myContext = context;
        build();
    }

    public DrawView(Context context, AttributeSet atribset, int defStyle) {
        super(context, atribset, defStyle);
        myContext = context;
        build();
    }

    public void build() {
        black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.STROKE);

        other = new Paint();
        other.setStyle(Paint.Style.FILL);

        board = null;
        board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 3; x++) {
                board[i][x] = Color.BLACK;
            }
        }

        if (mheight > 0) {
            setsizes();
        }
    }

    public void setsizes() {

        incr = mwidth / (5);
        leftside = incr - 1;
        rightside = incr * 9;
        boardwidth = incr * 3;
    }

    void clearboard() {
        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 3; x++) {
                board[i][x] = Color.BLACK;
            }
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = incr;
        int y = incr;
        canvas.drawColor(Color.WHITE);

        for (int yi = 0; yi < 3; yi++) {
            for (int xi = 0; xi < 3; xi++) {

                canvas.drawRect(x, y, x + incr, y + incr, black);  //draw black box.
                if (board[xi][yi] != Color.BLACK) {
                    other.setColor(board[xi][yi]);
                    canvas.drawRect(x + 1, y + 1, x + incr, y + incr, other);
                }

                x += incr;
            }
            x = incr;
            y += incr;
        }
    }

    boolean where(int x, int y, int color, boolean recolor) {
        int cx = -1, cy = -1;
        if (y >= leftside && y < rightside &&
                x >= leftside && x < rightside) {
            y -= incr;
            x -= incr; //simplifies the math here.
            cx = x / incr;
            cy = y / incr;
            if (cx < 3 && cy < 3) {
                if (board[cx][cy] == Color.BLACK) {
                    board[cx][cy] = color;
                } else if (recolor)
                    board[cx][cy] = color;
            }
            return true;
        }

        /*
         * If outside the lines, then popup a dialog and ask about reseting the board.
         * Interestingly, nothing is deprecated in the view
         */
        Dialog dialog = null;
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(myContext);
        builder.setMessage("Reset game?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        clearboard();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        dialog = builder.create();
        dialog.show();
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i("onTouchEvent", "Action_down");
                where(x, y, Color.BLUE, true);
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.i("MSW", "" + getMeasuredWidth());
        Log.i("MSH", "" + getMeasuredHeight());
        mwidth = getMeasuredWidth();
        mheight = getMeasuredHeight();
        if (mheight > 0 && mwidth > mheight) {
            mwidth = mheight;
        } else if (mheight == 0) {
            mheight = mwidth;
        }
        setsizes();

        setMeasuredDimension(mwidth, mheight);

    }
}

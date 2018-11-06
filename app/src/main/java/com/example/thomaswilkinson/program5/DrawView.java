package com.example.thomaswilkinson.program5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DrawView extends View {
    Paint black, other, myColor, myColor2;
    public Context myContext;
    public int board[][];
    int mheight = 0, mwidth = 0;
    int incr, playerNum=1;
    int leftside, rightside, boardwidth;
    int counter=0;
    Button button;
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
        myColor = new Paint();
        myColor.setColor(Color.RED);
        myColor.setStyle(Paint.Style.STROKE);
        myColor.setStrokeWidth(10);

        myColor2 = new Paint();
        myColor2.setColor(Color.BLUE);
        myColor2.setStyle(Paint.Style.STROKE);
        myColor2.setStrokeWidth(10);

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
        Dialog dialog = null;
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(myContext);
        builder.setTitle("Tic Tac Toe");
        builder.setMessage("Welcome!\nTake turns pressing boxes to play.\nPlayer X starts.\nPress Restart to restart the game. ")
                .setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        clearboard();
                    }
                });
        dialog = builder.create();
        dialog.show();
    }

    public void setsizes() {

        incr = mwidth / (5);
        leftside = incr - 1;
        rightside = incr * 9;
        boardwidth = incr * 3;
    }

    void resetButton(){
        Dialog dialog = null;
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(myContext);
        builder.setMessage("Reset game?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        counter=0;
                        playerNum=1;
                        clearboard();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        dialog = builder.create();
        dialog.show();
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
                canvas.drawRect(x, y, x + incr, y + incr, black);
                if (board[xi][yi] == 2) {
                    canvas.drawCircle(x+100, y+100, 80,myColor2);
                }
                else if (board[xi][yi] == 1)
                {
                    canvas.drawLine(x+20,y+20, x+180, y+180, myColor);
                    canvas.drawLine(x+20,y+180,x+180, y+20, myColor);
                }
                x += incr;
            }
            x = incr;
            y += incr;
        }
    }

    boolean where(int x, int y, int player) {
        int cx = -1, cy = -1;
        if (y >= leftside && y < rightside &&
                x >= leftside && x < rightside) {
            y -= incr;
            x -= incr;
            cx = x / incr;
            cy = y / incr;
            if (cx < 3 && cy < 3) {
                if (board[cx][cy] == Color.BLACK) {
                    board[cx][cy] = player;
                    if(playerNum==1) {playerNum =2; }
                    else if(playerNum==2) {playerNum=1;}
                    counter++;
                }
            }
            return true;
        }
        return false;
    }

    boolean checkWinner(){
        if((board[0][0] ==1 && board[0][1]==1 && board[0][2]==1)||(board[0][0] ==2 && board[0][1]==2 && board[0][2]==2)){
            declareWinner(board[0][0]);
            return true;
        }
        if((board[0][0] ==1 && board[1][0]==1 && board[2][0]==1)||(board[0][0] ==2 && board[1][0]==2 && board[2][0]==2)){
            declareWinner(board[0][0]);
            return true;
        }
        if((board[0][0] ==1 && board[1][1]==1 && board[2][2]==1)||(board[0][0] ==2 && board[1][1]==2 && board[2][2]==2)){
            declareWinner(board[0][0]);
            return true;
        }
        if((board[2][0] ==1 && board[2][1]==1 && board[2][2]==1)||(board[2][0] ==2 && board[2][1]==2 && board[2][2]==2)){
            declareWinner(board[2][0]);
            return true;
        }
        if((board[0][2] ==1 && board[1][2]==1 && board[2][2]==1)||(board[0][2] ==2 && board[1][2]==2 && board[2][2]==2)){
            declareWinner(board[0][2]);
            return true;
        }
        if((board[2][0] ==1 && board[1][1]==1 && board[0][2]==1)||(board[2][0] ==2 && board[1][1]==2 && board[0][2]==2)){
            declareWinner(board[2][0]);
            return true;
        }
        if((board[0][1] ==1 && board[1][1]==1 && board[2][1]==1)||(board[0][1] ==2 && board[1][1]==2 && board[2][1]==2)){
            declareWinner(board[0][1]);
            return true;
        }
        if((board[1][0] ==1 && board[1][1]==1 && board[1][2]==1)||(board[1][0] ==2 && board[1][1]==2 && board[1][2]==2)){
            declareWinner(board[1][0]);
            return true;
        }
        else if(counter == 9)
        {
            playerNum =1;
            counter = 0;
            Dialog dialog = null;
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(myContext);

            builder.setMessage("Tie game!\n\nPlay again?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            clearboard();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    System.exit(0);
                }
            });
            dialog = builder.create();
            dialog.show();
        }


        return false;
    }

    void declareWinner(int player){
        Dialog dialog = null;
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(myContext);
        if(player == 1){
            builder.setMessage("Player X Wins!\n\nPlay again?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            clearboard();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    System.exit(0);
                }
            });
        }
        else{
            builder.setMessage("Player O Wins!\n\nPlay again?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            clearboard();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    System.exit(0);
                }
            });
        }
        dialog = builder.create();
        dialog.show();
        playerNum=1;
        counter = 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                where(x, y, playerNum);
                invalidate();
                boolean temp = checkWinner();
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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

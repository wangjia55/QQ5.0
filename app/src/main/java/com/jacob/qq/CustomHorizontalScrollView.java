package com.jacob.qq;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Package : com.jacob.qq
 * Author : jacob
 * Date : 15-3-4
 * Description : 这个类是用来xxx
 */
public class CustomHorizontalScrollView extends HorizontalScrollView {
    private int screenWidth;
    private int menuWidth = 0;
    private int halfMenuWidth = 0;
    private boolean isOpen = false;
    private int menuPadding = dpToPx(80);


    public CustomHorizontalScrollView(Context context) {
        this(context, null);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        menuWidth = screenWidth - menuPadding;
        halfMenuWidth = menuWidth / 2;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LinearLayout linearLayout = (LinearLayout) getChildAt(0);
        ViewGroup menu = (ViewGroup) linearLayout.getChildAt(0);
        ViewGroup content = (ViewGroup) linearLayout.getChildAt(1);
        menu.getLayoutParams().width = menuWidth;
        content.getLayoutParams().width = screenWidth;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(menuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                int scrollx = getScrollX();
                if (scrollx < halfMenuWidth) {
                    smoothScrollTo(0, 0);
                    isOpen = false;
                } else {
                    smoothScrollTo(menuWidth, 0);
                    isOpen = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);

    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    public boolean isOpen() {
        return isOpen;
    }

    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    public void closeMenu() {
        if (!isOpen) {
            return;
        }
        smoothScrollTo(0, 0);
        isOpen = false;
    }


    public void openMenu() {
        if (isOpen) {
            return;
        }
        smoothScrollTo(menuWidth, 0);
        isOpen = true;
    }
}

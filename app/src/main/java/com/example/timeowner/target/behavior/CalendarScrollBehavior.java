package com.example.timeowner.target.behavior;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.example.timeowner.target.behavior.ViewOffsetBehavior;

import java.util.List;

/**
 * 日历 Behavior
 */

public class CalendarScrollBehavior extends ViewOffsetBehavior<RecyclerView> {

    private int calendarHeight;

    public CalendarScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent,
                                   @NonNull RecyclerView child,
                                   @NonNull View dependency) {
        return dependency instanceof MaterialCalendarView;
    }

    @Override
    protected void layoutChild(CoordinatorLayout parent, RecyclerView child, int layoutDirection) {
        super.layoutChild(parent, child, layoutDirection);
        if (calendarHeight == 0) {
            final List<View> dependencies = parent.getDependencies(child);
            for (int i = 0, z = dependencies.size(); i < z; i++) {
                View view = dependencies.get(i);
                if (view instanceof MaterialCalendarView) {
                    calendarHeight = view.getMeasuredHeight();
                }
            }
        }
        child.setTop(calendarHeight);
        child.setBottom(child.getBottom() + calendarHeight);
    }
}

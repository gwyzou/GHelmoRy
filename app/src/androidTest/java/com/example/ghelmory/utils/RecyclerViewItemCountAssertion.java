package com.example.ghelmory.utils;

import static org.hamcrest.Matchers.is;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import org.hamcrest.Matcher;
import org.junit.Assert;

public class RecyclerViewItemCountAssertion implements ViewAssertion {
    private final Matcher<Integer> matcher;

    public RecyclerViewItemCountAssertion(int expectedCount) {
        this.matcher = is(expectedCount);
    }

    public RecyclerViewItemCountAssertion(Matcher<Integer> matcher) {
        this.matcher = matcher;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        Assert.assertThat(adapter.getItemCount(), matcher);
    }
}

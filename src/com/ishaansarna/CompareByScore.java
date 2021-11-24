package com.ishaansarna;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class CompareByScore implements Comparator<Student> {
    @Override
    public int compare(@NotNull Student s1, @NotNull Student s2) {
        return Integer.compare(s2.getTotalScore(), s1.getTotalScore());
    }
}

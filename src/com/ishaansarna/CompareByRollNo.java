package com.ishaansarna;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class CompareByRollNo implements Comparator<Student> {
    @Override
    public int compare(@NotNull Student o1, @NotNull Student o2) {
        return Integer.compare(o1.getRollNo(), o2.getRollNo());
    }
}

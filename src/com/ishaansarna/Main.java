package com.ishaansarna;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Question question = new Question("Hi", 1, List.of(new String[]{"a", "b", "c"}), 1, 2, 3);
        int p = question.attemptQuestion();
        System.out.println(p);
    }
}

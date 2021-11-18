package com.ishaansarna;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Timer {
    public static String quizTimedQuestion(long startTime, long time) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            //noinspection StatementWithEmptyBody
            while ((System.currentTimeMillis() - startTime) < time * 1000L && !in.ready()) {
            }
            if (in.ready()) {
                return in.readLine();
            } else {
                return "0";
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
            return "-1";
        }
    }
}

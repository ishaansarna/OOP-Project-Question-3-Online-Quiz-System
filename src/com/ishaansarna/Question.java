package com.ishaansarna;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Question {
    private final String question;
    private final List<String> answers;
    private final int correctAnswer; // 1-index
    private final int points;
    private final int time;

    public Question(String question, List<String> answers, int correctAnswer, int points) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.points = points;
        this.time = Math.max(20*points, 180);
    }

    public Question(String question, List<String> answers, int correctAnswer, int points, int time) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.points = points;
        this.time = time == 0 ? Math.max(20*points, 180) : time;
    }

    public String getQuestion() {
        return question;
    }
//
//    public List<String> getAnswers() {
//        return answers;
//    }
//
//    public boolean isAnswerCorrect(@NotNull String answer) {
//        // compare string to all options and return whether it is correct
//        return answer.equalsIgnoreCase(answers.get(correctAnswer));
//    }
//
//    public boolean isAnswerCorrect(int optionNumber) {
//        return optionNumber == correctAnswer;
//    }

    public int attemptQuestion() {
        System.out.println("For " + points + " points with a time limit of " + time + " seconds");
        System.out.println("_______________");
        System.out.println(question);
        System.out.println("_______________");
        int i = 1;
        for (String answer : this.answers) {
            System.out.println(i + ") " + answer);
            i++;
        }
        Scanner answerScanner = new Scanner(System.in);
        int answerSelected;
        boolean repeat = true;
        int pointsWon = 0;
        while (repeat) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                long startTime = System.currentTimeMillis();
                // For timeout:
                //noinspection StatementWithEmptyBody
                while ((System.currentTimeMillis() - startTime) < time * 1000L && !in.ready()) {
                }
                if (in.ready()) {
                    answerSelected = Integer.parseInt(in.readLine());
                } else {
                    System.out.println("You ran out of time :(");
                    return 0;
                }

                if (answerSelected >= 1 && answerSelected <= answers.size()) {
                    pointsWon = answerSelected == this.correctAnswer ? points : 0;
                    repeat = false;
                }
                else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException | IOException e) {
                System.out.println("Please enter an integer between 1 and " + answers.size());
            }
        }
        if (pointsWon == 0) {
            System.out.println("You got it wrong! Better luck next time ;)");
        }
        else {
            System.out.println("Congratulations, you have got " + pointsWon + " points!");
        }
        return pointsWon;
    }
}

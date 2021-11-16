package com.ishaansarna;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Question {
    private String question = "";
    private int questionID;
    private List<String> answers;
    private int correctAnswer; // 1-index
    private int difficulty; // 1, 2 or 3
    private int points;

    public Question(String question, int questionID, List<String> answers, int correctAnswer, int difficulty, int points) {
        this.question = question;
        this.questionID = questionID;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
        this.points = points;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public boolean isAnswerCorrect(@NotNull String answer) {
        // compare string to all options and return whether it is correct
        return answer.equalsIgnoreCase(answers.get(correctAnswer));
    }

    public boolean isAnswerCorrect(int optionNumber) {
        return optionNumber == correctAnswer;
    }

    public int attemptQuestion() {
        System.out.println(question);
        int i = 1;
        for (String answer : this.answers) {
            System.out.println(i + ") " + answer);
            i++;
        }
        Scanner answerScanner = new Scanner(System.in);
        int answerSelected = 0;
        boolean repeat = true;
        int pointsWon = 0;
        while (repeat) {
            try {
                answerSelected = answerScanner.nextInt();
                if (answerSelected >= 1 && answerSelected <= answers.size()) {
                    pointsWon = answerSelected == this.correctAnswer ? points : 0;
                    repeat = false;
                }
                else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter an integer between 1 and " + answers.size());
                answerScanner.next();
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

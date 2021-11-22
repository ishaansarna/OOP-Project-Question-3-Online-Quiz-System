package com.ishaansarna;

import java.util.InputMismatchException;
import java.util.List;

public class Question {
    private final String question;
    private final List<String> answers;
    private final int correctAnswer; // 1-index
    private final int points;
    private final int time;
    private int answerSelected;

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
        boolean repeat = true;
        int pointsWon = 0;
        while (repeat) {
            try {
                long startTime = System.currentTimeMillis();

                answerSelected = Integer.parseInt(Timer.quizTimedQuestion(startTime, time));

                if (answerSelected >= 1 && answerSelected <= answers.size()) {
                    pointsWon = answerSelected == this.correctAnswer ? points : 0;
                    repeat = false;
                }
                else if (answerSelected == 0) {
                    System.out.println("You ran out of time :(");
                    return 0;
                }
                else if (answerSelected == -1) {
                    System.out.println("Error detected, proceeding to next question");
                    return 0;
                }
                else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException | NumberFormatException e) {
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

    public AttemptedQuestion returnAttemptedQuestion() {
        String chosenAnswer = answerSelected > 0 ? answers.get(answerSelected-1) : "Timeout";
        return new AttemptedQuestion(question, answers.get(correctAnswer-1), chosenAnswer);
    }

    public void viewQuestion() {
        System.out.println("The question is –");
        System.out.println(this.question);
        System.out.println("The options are –");
        for (int i = 1; i <= answers.size(); i++) {
            System.out.println(i + ") " + answers.get(i-1));
        }
        System.out.println("Out of which the correct option is \"" + answers.get(correctAnswer-1) + "\"");
    }
}

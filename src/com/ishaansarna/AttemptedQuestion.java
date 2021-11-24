package com.ishaansarna;

public class AttemptedQuestion {
    private final String question;
    private final String correctAnswerValue;
    private final String chosenAnswer;
    private final boolean isAnswerCorrect;

    public AttemptedQuestion(String question, String correctAnswerValue, String chosenAnswer) {
        this.question = question;
        this.correctAnswerValue = correctAnswerValue;
        this.chosenAnswer = chosenAnswer;
        this.isAnswerCorrect = correctAnswerValue.equalsIgnoreCase(chosenAnswer);
    }

    public void display() {
        if (isAnswerCorrect) {
            System.out.println("You got the question \"" + question + "\" correct!");
            System.out.println("The answer was " + correctAnswerValue);
        }
        else if (chosenAnswer.equalsIgnoreCase("Timeout")) {
            System.out.println("You timed out on the question \"" + question + "\"");
            System.out.println("The correct answer was " + correctAnswerValue);
        }
        else {
            System.out.println("You got the question \"" + question + "\" incorrect");
            System.out.println("The correct answer was " + correctAnswerValue + " while you answered " + chosenAnswer);
        }
        System.out.println();
    }
}

package com.ishaansarna;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Student implements Runnable {
    private final String name;
    private final int rollNo;
    private int totalScore;
    private int leaderBoardPosition;
    private final Admin admin;
    private boolean exit;
    public Thread thread;
    private final List<AttemptedQuestion> attemptedQuestions;

    public Student(String name, int rollNo, Admin admin) {
        this.name = name;
        this.rollNo = rollNo;
        this.totalScore = 0;
        this.leaderBoardPosition = -1;
        this.admin = admin;
        this.thread = new Thread(this, name + " Thread");
        this.attemptedQuestions = new ArrayList<>();
    }

    public void updateScore(int score) {
        this.totalScore += score;
    }

    public void setLeaderBoardPosition(int leaderBoardPosition) {
        this.leaderBoardPosition = leaderBoardPosition;
    }

    public int getLeaderBoardPosition() {
        return leaderBoardPosition;
    }

    public void attemptQuestion(@NotNull Question question) {
        System.out.println("Question for " + this.name + " with roll no. " + this.rollNo);
        updateScore(question.attemptQuestion());
        System.out.println("Your current score is " + this.totalScore);
        System.out.println("________________________");
        attemptedQuestions.add(question.returnAttemptedQuestion());
        try {
            wait(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        synchronized (this) {
            while (!exit){
                if (!admin.isQuizRunning()) {
                    System.out.println();
                    System.out.println(this.name + ", do you want to see your details? [y/n]");
                    Scanner scanner = new Scanner(System.in);
                    boolean repeat = true;
                    String choice = null;
                    while (repeat) {
                        try {
                            choice = scanner.next().toLowerCase();
                            if (!(choice.equals("y") || choice.equals("n"))) {
                                throw new InputMismatchException();
                            } else {
                                repeat = false;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Please enter either y or n");
                            scanner.nextLine();
                        }
                    }
                    if ("y".equals(choice)) {
                        showDetails();
                    }
                    System.out.println("Thank you for playing");
                } else {
                    attemptQuestion(admin.getCurrentQuestion());
                }
                setExit(true);
            }
        }
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public void showDetails() {
        showBasicDetails();
        System.out.println("Would you like to see your attempts? (y/n)");
        Scanner choiceScanner = new Scanner(System.in);
        String choiceInput = "n";
        boolean flag = true;
        while (flag) {
            choiceInput = choiceScanner.nextLine();
            if (choiceInput.equalsIgnoreCase("y") || choiceInput.equalsIgnoreCase("n")) {
                flag = false;
            }
            else {
                System.err.println("Please enter a valid input");
            }
        }
        if (choiceInput.equalsIgnoreCase("y")) {
            displayAttemptedQuestions();
        }
    }

    public String getName() {
        return name;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void createThread() {
        this.thread = new Thread(this, name + " Thread");
    }

    public void runThis() throws InterruptedException {
        createThread();
        setExit(false);
        this.thread.start();
        this.thread.join();
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void displayAttemptedQuestions() {
        for (AttemptedQuestion attemptedQuestion : attemptedQuestions) {
            attemptedQuestion.display();
        }
    }

    @Contract("_ -> new")
    public static @NotNull Student createStudentPrompt(Admin admin) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the name of the student");
        String name = scanner.nextLine();
        System.out.println("Please enter the roll number of the student");
        boolean flag = true;
        int rollNo = 0;
        while (flag) {
            try {
                rollNo = scanner.nextInt();
                flag = false;
            }
            catch (InputMismatchException e) {
                System.err.println("Please enter a valid integer");
            }
        }
        return new Student(name, rollNo, admin);
    }

    public void viewStudent() {
        showBasicDetails();
    }

    private void showBasicDetails() {
        System.out.println("Name: " + this.name);
        System.out.println("Roll No.: " + this.rollNo);
        System.out.println("Total Score: " + this.totalScore);
        if (this.leaderBoardPosition != -1) {
            System.out.println("Leaderboard Position: " + this.leaderBoardPosition);
        } else {
            System.out.println("Did not take part in a quiz yet so leaderboard position is not applicable");
        }
    }
}

package com.ishaansarna;

import org.jetbrains.annotations.NotNull;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Student implements Runnable {
    private String name;
    private int rollNo;
    private int totalScore;
    private int leaderBoardPosition;
    private boolean suspendFlag;
    private final Admin admin;
    private boolean exit;
    public Thread thread;

    public Student(String name, int rollNo, Admin admin) {
        this.name = name;
        this.rollNo = rollNo;
        this.totalScore = 0;
        this.leaderBoardPosition = -1;
        this.suspendFlag = true;
        this.admin = admin;
        this.thread = new Thread(this, name + " Thread");
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
        System.out.println("Current score is " + this.totalScore);
        System.out.println("________________________");
    }

    public void viewLeaderboardPosition() {
        System.out.println("Your leaderboard position is " + this.leaderBoardPosition);
    }

    @Override
    public void run() {
        synchronized (this) {
            while (!exit){
                if (!admin.isQuizRunning()) {
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
                            scanner.next();
                        }
                    }
                    switch (choice) {
                        case "y" -> showDetails();
                        case "n" -> System.out.println("Thank you for playing");
                        default -> System.err.println("Invalid input");
                    }
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
        System.out.println("Name: " + this.name);
        System.out.println("Roll No.: " + this.rollNo);
        System.out.println("Total Score: " + this.totalScore);
        System.out.println("Leaderboard Position " + this.leaderBoardPosition);
    }

    synchronized public void setSuspendFlag(boolean suspendFlag) {
        this.suspendFlag = suspendFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
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
}

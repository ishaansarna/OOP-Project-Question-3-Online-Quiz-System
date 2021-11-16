package com.ishaansarna;

public class Student implements Runnable {
    private String name = "";
    private int rollNo;
    private int totalScore;
    private int leaderBoardPosition;
    private boolean suspendFlag;
    private Admin admin;
    private Thread thread;

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

    public void updateLeaderBoardPosition(int leaderBoardPosition) {
        this.leaderBoardPosition = leaderBoardPosition;
    }

    public void attemptQuestion(Question question) {
        // TODO
    }

    public void viewLeaderboardPosition() {
        System.out.println("Your leaderboard position is " + this.leaderBoardPosition);
    }

    @Override
    public void run() {
        synchronized (this) {
            if (suspendFlag) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    synchronized void setSuspendFlag(boolean suspendFlag) {
        this.suspendFlag = suspendFlag;
    }
}

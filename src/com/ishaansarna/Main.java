package com.ishaansarna;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Admin admin = new Admin("ADMIN_NAME");
        admin.addDefaultSetOfStudentsAndQuestions();
        admin.conductQuiz();
        admin.displayLeaderboard();
    }
}

package com.ishaansarna;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class  Admin implements Runnable {

    private Question currentQuestion;
    private String name;
    private final List<Student> students;
    private final List<Question> questions;
    public boolean quizRunning;
    public Thread thread;

    public Admin(String name) {
        this.name = name;
        this.students = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.quizRunning = false;
        this.thread = new Thread(this, "Admin Thread");
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void addDefaultSetOfStudentsAndQuestions() {
        students.add(new Student("Ram", 1, this));
        students.add(new Student("Shyam", 2, this));
        students.add(new Student("Ghanshyam", 3, this));
        students.add(new Student("Radheshyam", 4, this));
        /*
        Questions courtesy of
        https://www.sawaal.com/general-knowledge/general-awareness-questions-and-answers.htm
        https://github.com/appbrewery/Quizzler-iOS13-Completed
         */
//        questions.add(new Question("Fathometer is used to measure?", List.of(new String[]{"Earthquakes", "Rainfall", "Ocean depth"}), 3, 3));
//        questions.add(new Question("Epsom(England) is the place associated with", List.of(new String[]{"Snooker", "Horse racing", "Shooting"}), 2, 3));
//        questions.add(new Question("Which is the largest organ in the human body?", List.of(new String[]{"Heart", "Skin", "Large Intestine"}), 2, 3));
        questions.add(new Question("Fathometer is used to measure?", List.of(new String[]{"Earthquakes", "Rainfall", "Ocean depth"}), 3, 3, 4));
        questions.add(new Question("Epsom(England) is the place associated with", List.of(new String[]{"Snooker", "Horse racing", "Shooting"}), 2, 3, 5));
        questions.add(new Question("Which is the largest organ in the human body?", List.of(new String[]{"Heart", "Skin", "Large Intestine"}), 2, 3, 6));
        questions.add(new Question("What do the letters in the GMT time zone stand for?", List.of(new String[]{"Global Meridian Time", "Greenwich Mean Time", "General Median Time"}), 2, 3));
        questions.add(new Question("What is the French word for 'hat'?", List.of(new String[]{"Chapeau", "Écharpe", "Bonnet"}), 1, 3));
        questions.add(new Question("In past times, what would a gentleman keep in his fob pocket?", List.of(new String[]{"Notebook", "Handkerchief", "Watch"}), 3, 3));
        questions.add(new Question("How would one say goodbye in Spanish?", List.of(new String[]{"Au Revoir", "Adiós", "Salir"}), 2, 3));
        questions.add(new Question("Which of these colours is NOT featured in the logo for Google?", List.of(new String[]{"Green", "Orange", "Blue"}), 2, 3));
        questions.add(new Question("Where is Tasmania located?", List.of(new String[]{"Indonesia", "Australia", "Scotland", "India"}), 2, 3));
        questions.add(new Question("Golf player Vijay Singh belongs to which country?", List.of(new String[]{"USA", "UK", "India", "Fiji"}), 4, 3));
        questions.add(new Question("FFC stands for", List.of(new String[]{"Federation of Football Council", "Film Finance Corporation", "Foreign Finance Corporation"}), 2, 3));
        questions.add(new Question("Which is a green planet in the solar system?", List.of(new String[]{"Pluto", "Venus", "Uranus", "Mars"}), 3, 3));
    }

    public boolean isQuizRunning() {
        return quizRunning;
    }

    public void conductQuiz() throws InterruptedException {
        quizRunning = true;
        for (int i = 0, j = 0; i < questions.size(); i++, j++) {
            if (j >= students.size()) {
                j = 0;
            }
            this.currentQuestion = questions.get(i);
            students.get(j).runThis();
        }
        sortStudentsByLeaderboardPosition();
        resetStudentsOrder();
        quizRunning = false;
        for (Student student : students) {
            student.runThis();
        }
    }

    public void sortStudentsByLeaderboardPosition() {
        students.sort(new compareByScore());
        for (int i = 0; i < students.size(); i++) {
            students.get(i).setLeaderBoardPosition(i+1);
        }
    }

    public void resetStudentsOrder() {
        students.sort(new compareByRollNo());
    }

    public void displayLeaderboard() {
        sortStudentsByLeaderboardPosition();
        System.out.println("\n\nThe Leaderboard");
        System.out.println("__________________________________________");
        for (Student student : students) {
            System.out.println("Rank " + student.getLeaderBoardPosition() + " is " + student.getName() +
                    " with a score of " + student.getTotalScore());
        }
        resetStudentsOrder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addQuestion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the question");
        String question = scanner.nextLine();
        System.out.println("What are the options? (Enter an option on each line, enter \"d\" to finish adding)");
        List<String> options = new ArrayList<>();
        while (true){
            String option = scanner.nextLine();
            if (option.equalsIgnoreCase("d")) {
                break;
            }
            options.add(option);
        }
        if (options.size() < 2) {
            System.err.println("Please enter at least two options");
            return;
        }
        int correctAnswer, points, time;
        try {
            System.out.println("Please enter the option number of the correct answer");
            correctAnswer = Integer.parseInt(scanner.nextLine());
            if (correctAnswer > options.size() || correctAnswer <= 0) {
                throw new InputMismatchException();
            }
            System.out.println("Please enter the number of points");
            points = Integer.parseInt(scanner.nextLine());
            System.out.println("Please enter the time for this question (enter 0 for default value)");
            time = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Please enter an integer");
            return;
        } catch (InputMismatchException e) {
            System.err.println("Please enter a valid option number");
            return;
        }
        questions.add(new Question(question, options, correctAnswer, points, time));
    }

    public void removeQuestion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the question to be removed");
        questions.removeIf(question -> question.getQuestion().equalsIgnoreCase(scanner.nextLine()));
    }

    public void addStudent() {
        // todo
    }

    public void removeStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the student to be removed");
        students.removeIf(student -> student.getName().equalsIgnoreCase(scanner.nextLine()));
    }

    public void viewQuestions() {
        // todo
    }

    public void viewStudents() {
        // todo
    }

    public void run() {
        // todo
        try {
            addDefaultSetOfStudentsAndQuestions();
            conductQuiz();
            displayLeaderboard();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

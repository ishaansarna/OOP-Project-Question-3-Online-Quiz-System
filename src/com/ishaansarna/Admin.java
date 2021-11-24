package com.ishaansarna;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin implements Runnable {

    private Question currentQuestion;
    private final List<Student> students;
    private final List<Question> questions;
    private boolean quizRunning;
    public final Thread thread;
    private boolean hasQuizBeenConducted;

    public Admin() {
        this.students = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.quizRunning = false;
        this.thread = new Thread(this, "Admin Thread");
        hasQuizBeenConducted = false;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void addDefaultSetOfStudentsAndQuestions() {
        students.add(new Student("John", 1, this));
        students.add(new Student("Amy", 2, this));
        students.add(new Student("Lisa", 3, this));
        students.add(new Student("Mark", 4, this));
        /*
        Questions courtesy of
        https://www.sawaal.com/general-knowledge/general-awareness-questions-and-answers.htm
        https://github.com/appbrewery/Quizzler-iOS13-Completed
         */
        questions.add(new Question("Fathometer is used to measure?", List.of(new String[]{"Earthquakes", "Rainfall", "Ocean depth"}), 3, 2));
        questions.add(new Question("Epsom(England) is the place associated with", List.of(new String[]{"Snooker", "Horse racing", "Shooting"}), 2, 3));
        questions.add(new Question("Which is the largest organ in the human body?", List.of(new String[]{"Heart", "Skin", "Large Intestine"}), 2, 1));
        questions.add(new Question("What do the letters in the GMT time zone stand for?", List.of(new String[]{"Global Meridian Time", "Greenwich Mean Time", "General Median Time"}), 2, 1));
        questions.add(new Question("What is the French word for 'hat'?", List.of(new String[]{"Chapeau", "Écharpe", "Bonnet"}), 1, 2));
        questions.add(new Question("In past times, what would a gentleman keep in his fob pocket?", List.of(new String[]{"Notebook", "Handkerchief", "Watch"}), 3, 2));
        questions.add(new Question("How would one say goodbye in Spanish?", List.of(new String[]{"Au Revoir", "Adiós", "Salir"}), 2, 1));
        questions.add(new Question("Which of these colours is NOT featured in the logo for Google?", List.of(new String[]{"Green", "Orange", "Blue"}), 2, 1));
        questions.add(new Question("Where is Tasmania located?", List.of(new String[]{"Indonesia", "Australia", "Scotland", "India"}), 2, 1));
        questions.add(new Question("Golf player Vijay Singh belongs to which country?", List.of(new String[]{"USA", "UK", "India", "Fiji"}), 4, 3));
        questions.add(new Question("FFC stands for", List.of(new String[]{"Federation of Football Council", "Film Finance Corporation", "Foreign Finance Corporation"}), 2, 3));
        questions.add(new Question("Which is a green planet in the solar system?", List.of(new String[]{"Pluto", "Venus", "Uranus", "Mars"}), 3, 2));
        System.out.println("Added default set of students and questions");
    }

    public boolean isQuizRunning() {
        return quizRunning;
    }

    public void conductQuiz() throws InterruptedException {
        hasQuizBeenConducted = true;
        if (students.isEmpty() || questions.isEmpty()) {
            System.err.println("Please add at least 1 student and question each");
            return;
        }
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
        displayLeaderboard();
    }

    public void sortStudentsByLeaderboardPosition() {
        if (hasQuizBeenConducted) {
            students.sort(new CompareByScore());
            for (int i = 0; i < students.size(); i++) {
                students.get(i).setLeaderBoardPosition(i+1);
            }
        }
    }

    public void resetStudentsOrder() {
        students.sort(new CompareByRollNo());
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

    public void addQuestion() {
        Question question = Question.addQuestion();
        if (question != null) {
            questions.add(question);
        }
    }

    public void addNQuestions() {
        System.out.println("How many questions would you like to add");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Please add question number " + (i+1));
            addQuestion();
        }
    }

    public void removeQuestion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the question to be removed");
        String questionText = scanner.nextLine();
        if (questions.removeIf(question -> question.getQuestion().equalsIgnoreCase(questionText))) {
            System.out.println("Successfully removed the question \"" + questionText + "\"");
        }
        else {
            System.err.println("No question matching \"" + questionText + "\"");
        }
    }

    public void addStudent() {
        students.add(Student.createStudentPrompt(this));
    }

    public void addNStudents() {
        System.out.println("How many students would you like to add");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Please add student number " + (i+1));
            addStudent();
        }
    }

    public void removeStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the student to be removed");
        String name = scanner.nextLine();
        if (students.removeIf(student -> student.getName().equalsIgnoreCase(name))) {
            System.out.println("Successfully removed " + name);
        }
        else {
            System.err.println("No student by the name " + name);
        }
    }

    public void viewQuestions() {
        if (questions.isEmpty()) {
            System.err.println("No questions added yet");
            return;
        }
        System.out.println("The questions are:- ");
        for (int i = 0; i < questions.size(); i++) {
            System.out.println((i+1) + ")");
            questions.get(i).viewQuestion();
        }
    }

    public void viewStudents() {
        if (students.isEmpty()) {
            System.err.println("No students added yet");
            return;
        }
        System.out.println("The students are:- ");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i+1) + ") ");
            students.get(i).viewStudent();
        }
    }

    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                System.out.println("Welcome to Quizzy–The Online Quiz System");
                //noinspection InfiniteLoopStatement
                while (true){
                    System.out.println("––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");
                    System.out.println("What would you like to do? Please select the appropriate option.");
                    System.out.println("1. Conduct quiz");
                    System.out.println("2. Add a student");
                    System.out.println("3. Add n students");
                    System.out.println("4. Add a question");
                    System.out.println("5. Add n questions");
                    System.out.println("6. View all students");
                    System.out.println("7. View all questions");
                    System.out.println("8. Remove a student");
                    System.out.println("9. Remove a question");
                    System.out.println("10. View the leaderboard");
                    System.out.println("11. Add default set of students and questions");
                    System.out.println("12. Exit");

                    Scanner scanner = new Scanner(System.in);
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1 -> conductQuiz();
                        case 2 -> addStudent();
                        case 3 -> addNStudents();
                        case 4 -> addQuestion();
                        case 5 -> addNQuestions();
                        case 6 -> viewStudents();
                        case 7 -> viewQuestions();
                        case 8 -> removeStudent();
                        case 9 -> removeQuestion();
                        case 10 -> displayLeaderboard();
                        case 11 -> addDefaultSetOfStudentsAndQuestions();
                        case 12 -> exit();
                        default -> System.err.println("Please enter a valid option");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.err.println("Please enter a valid integer");
            }
        }
    }

    public void exit() {
        System.out.println("Thank you for  using Quizzy–The Online Quiz System");
        System.exit(0);
    }
}

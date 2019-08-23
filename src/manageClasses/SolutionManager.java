package manageClasses;

import dao.ExerciseDao;
import dao.SolutionDao;
import dao.UserDao;
import models.Solution;
import util.ScannerUtil;

import java.util.Arrays;
import java.util.Scanner;

public class SolutionManager {
    private Scanner scanner = new Scanner(System.in);
    private SolutionDao solutionDao = new SolutionDao();

    public void solutionManagerMenu() {

        label:
        while (true) {

            System.out.println("Wybierz jedną z opcji: \n" +
                    "add – przypisywanie zadań do użytkowników, \n" +
                    "view – przeglądanie rozwiązań danego użytkownika, \n" +
                    "quit – zakończenie programu.");

            String option = scanner.nextLine();

            switch (option) {
                case "add":
                    assignSolution();
                    break;
                case "view":
                    viewSolutionsByUser();
                    break;
                case "quit":
                    break label;
            }
        }
    }

    private void assignSolution() {
        int userId = getUserId();
        int exerciseId = getExerciseId();

        Solution solution = new Solution();
        solution.setUserId(userId);
        solution.setExerciseId(exerciseId);

        solutionDao.create(solution);

    }

    private void viewSolutionsByUser() {
        int userId = getUserId();
        System.out.println(solutionDao.findAllByUserId(userId));
    }

    private int getUserId() {
        UserDao userDao = new UserDao();
        System.out.println("Wybierz użytkownika po numerze id");
        System.out.println(Arrays.toString(userDao.findAll()));
        int userId;

        while (true) {
            userId = ScannerUtil.returnIntGreaterThanZero();

            if (userDao.read(userId) == null) {
                System.out.println("Nie ma takiego użytkownika");
            } else {
                break;
            }
        }

        return userId;
    }

    private int getExerciseId() {
        ExerciseDao exerciseDao = new ExerciseDao();
        System.out.println("Wybierz zadanie po numerze id");
        System.out.println(Arrays.toString(exerciseDao.findAll()));
        int exerciseId;

        while (true) {
            exerciseId = ScannerUtil.returnIntGreaterThanZero();

            if (exerciseDao.read(exerciseId) == null) {
                System.out.println("Nie ma takiego zadania");
            } else {
                break;
            }
        }

        return exerciseId;
    }
}

import dao.ExerciseDao;
import dao.SolutionDao;
import models.Exercise;
import models.Solution;
import util.ScannerUtil;

import java.util.List;
import java.util.Scanner;

public class UserProgram {
    private int userId;
    private Scanner scanner = new Scanner(System.in);
    private ExerciseDao exerciseDao = new ExerciseDao();
    private SolutionDao solutionDao = new SolutionDao();

    public UserProgram(int userId) {
        this.userId = userId;
        startProgram();
    }

    private void startProgram() {

        label:
        while (true) {
            System.out.println("Wybierz jedną z opcji: \n" +
                    "add – dodawanie rozwiązania,\n" +
                    "view – przeglądanie swoich rozwiązań.");

            String option = scanner.nextLine();

            switch (option) {
                case "add":
                    addSolution();
                    break;
                case "view":
                    viewSolution();
                    break;
                case "quit":
                    break label;
            }
        }

    }


    private void addSolution() {
        List<Exercise> allThatAreNotSolved = exerciseDao.findAllThatAreNotSolved(userId);
        System.out.println(allThatAreNotSolved);

        System.out.println("Podaj id zadania, które chcesz rozwiązać");
        int exerciseId = ScannerUtil.returnIntGreaterThanZero();

        Exercise exercise = exerciseDao.read(exerciseId);
        if(exercise == null){
            System.out.println("Takie zadanie nie istnieje");
            return;
        }

        boolean choosenExerciseIsNotSolved = false;
        for(Exercise exercise1 : allThatAreNotSolved){
            if(exercise1.getId() == exerciseId){
                choosenExerciseIsNotSolved = true;
                break;
            }
        }

        if(!choosenExerciseIsNotSolved){
            System.out.println("Wybrane zadanie jest już rozwiązane");
            return;
        }

        Solution solution = new Solution();
        System.out.println("Podaj rozwiązanie zadania");
        String description = scanner.nextLine();
        solution.setDescription(description);
        solution.setExerciseId(exerciseId);
        solution.setUserId(userId);
        solutionDao.create(solution);

    }


    private void viewSolution() {
        System.out.println(solutionDao.findAllByUserId(userId));
    }
}

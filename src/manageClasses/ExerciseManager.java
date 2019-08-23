package manageClasses;

import dao.ExerciseDao;
import models.Exercise;
import util.ScannerUtil;

import java.util.Arrays;
import java.util.Scanner;

public class ExerciseManager {
    private ExerciseDao exerciseDao = new ExerciseDao();
    private Scanner scanner = new Scanner(System.in);


    public void exerciseManagerMenu() {
        label:
        while (true) {

            System.out.println(Arrays.toString(exerciseDao.findAll()));

            System.out.println("Wybierz jedną z opcji: \n" +
                    "add – dodanie ćwiczenia, \n" +
                    "edit – edycja ćwiczenia, \n" +
                    "delete – usunięcie ćwiczenia, \n" +
                    "quit – zakończenie programu.");

            String option = scanner.nextLine();

            switch (option) {
                case "add":
                    addExercise();
                    break;
                case "edit":
                    editExercise();
                    break;
                case "delete":
                    deleteExercise();
                    break;
                case "quit":
                    break label;
            }
        }
    }

    private void addExercise() {
        Exercise exercise = new Exercise();
        putDataIntoExercise(exercise);
        exerciseDao.create(exercise);
        System.out.println("Dodano nowe ćwiczenie \n");
    }

    private void editExercise() {
        System.out.println("Podaj id ćwiczenia");
        int exerciseId = ScannerUtil.returnIntGreaterThanZero();

        Exercise exercise = exerciseDao.read(exerciseId);

        if(exercise == null){
            System.out.println("Podano nieprawidłowy id");
            return;
        }

        putDataIntoExercise(exercise);
        exerciseDao.update(exercise);
    }

    private void putDataIntoExercise(Exercise exercise){
        System.out.println("Podaj nazwę ćwiczenia:");
        exercise.setTitle(scanner.nextLine());

        System.out.println("Podaj opis ćwiczenia");
        exercise.setDescription(scanner.nextLine());
    }

    private void deleteExercise() {
        System.out.println("Podaj id ćwiczenia");
        int exerciseId = ScannerUtil.returnIntGreaterThanZero();
        exerciseDao.delete(exerciseId);
    }
}

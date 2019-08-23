import dao.ExerciseDao;
import dao.SolutionDao;
import dao.UserDao;
import dao.UserGroupDao;
import manageClasses.ExerciseManager;
import manageClasses.SolutionManager;
import manageClasses.UserGroupManager;
import manageClasses.UserManager;
import models.Exercise;
import models.Solution;
import models.User;
import models.UserGroup;

import java.sql.Timestamp;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
//        UserManager userManager = new UserManager();
//        userManager.userManagerMenu();
//        ExerciseManager exerciseManager = new ExerciseManager();
//        exerciseManager.exerciseManagerMenu();
//        UserGroupManager userGroupManager = new UserGroupManager();
//        userGroupManager.userGroupManagerMenu();
//        SolutionManager solutionManager = new SolutionManager();
//        solutionManager.solutionManagerMenu();

        UserProgram userProgram = new UserProgram(5);
    }
}

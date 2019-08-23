package manageClasses;

import dao.UserDao;
import dao.UserGroupDao;
import models.User;
import util.ScannerUtil;

import java.util.Arrays;
import java.util.Scanner;

public class UserManager {

    private UserDao userDao = new UserDao();
    private Scanner scanner = new Scanner(System.in);


    public void userManagerMenu() {
        label:
        while (true) {

            System.out.println(Arrays.toString(userDao.findAll()));

            System.out.println("Wybierz jedną z opcji: \n" +
                    "add – dodanie użytkownika, \n" +
                    "edit – edycja użytkownika, \n" +
                    "delete – usunięcie użytkownika, \n" +
                    "quit – zakończenie programu.");

            String option = scanner.nextLine();

            switch (option) {
                case "add":
                    addUser();
                    break;
                case "edit":
                    editUser();
                    break;
                case "delete":
                    deleteUser();
                    break;
                case "quit":
                    break label;
            }
        }
    }

    private void addUser() {
        User user = new User();
        putDataIntoUser(user);
        userDao.create(user);
        System.out.println("Dodano nowego użytkownika \n");
    }

    private void editUser() {
        System.out.println("Podaj id użytkownika");
        int userId = ScannerUtil.returnIntGreaterThanZero();

        User user = userDao.read(userId);

        if(user == null){
            System.out.println("Podano nieprawidłowy id");
            return;
        }

        putDataIntoUser(user);
        userDao.update(user);
    }

    private void putDataIntoUser(User user){
        UserGroupDao userGroupDao = new UserGroupDao();

        System.out.println("Podaj nazwę użytkownika:");
        user.setUsername(scanner.nextLine());

        System.out.println("Podaj email");
        user.setEmail(scanner.nextLine());

        System.out.println("Podaj hasło");
        user.setPassword(scanner.nextLine());

        boolean invalidNumber = true;
        int groupId = -1;
        while (invalidNumber) {
            System.out.println("Podaj id istniejącej grupy");
            groupId = ScannerUtil.returnIntGreaterThanZero();
            invalidNumber = !userGroupDao.checkIfExists(groupId);
        }

        user.setUserGroupId(groupId);
    }

    private void deleteUser() {
        System.out.println("Podaj id użytkownika");
        int userId = ScannerUtil.returnIntGreaterThanZero();
        userDao.delete(userId);
    }

}

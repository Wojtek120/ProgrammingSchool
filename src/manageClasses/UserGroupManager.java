package manageClasses;

import dao.UserGroupDao;
import models.UserGroup;
import util.ScannerUtil;

import java.util.Arrays;
import java.util.Scanner;

public class UserGroupManager {
    private UserGroupDao userGroupDao = new UserGroupDao();
    private Scanner scanner = new Scanner(System.in);


    public void userGroupManagerMenu() {
        label:
        while (true) {

            System.out.println(Arrays.toString(userGroupDao.findAll()));

            System.out.println("Wybierz jedną z opcji: \n" +
                    "add – dodanie grupy, \n" +
                    "edit – edycja grupy, \n" +
                    "delete – usunięcie grupy, \n" +
                    "quit – zakończenie programu.");

            String option = scanner.nextLine();

            switch (option) {
                case "add":
                    addUserGroup();
                    break;
                case "edit":
                    editUserGroup();
                    break;
                case "delete":
                    deleteUserGroup();
                    break;
                case "quit":
                    break label;
            }
            
        }
    }

    private void addUserGroup() {
        UserGroup userGroup = new UserGroup();
        putDataIntoUserGroup(userGroup);
        userGroupDao.create(userGroup);
        System.out.println("Dodano nową grupę \n");
    }

    private void editUserGroup() {
        System.out.println("Podaj id grupy");
        int userGroupId = ScannerUtil.returnIntGreaterThanZero();

        UserGroup userGroup = userGroupDao.read(userGroupId);

        if(userGroup == null){
            System.out.println("Podano nieprawidłowy id");
            return;
        }

        putDataIntoUserGroup(userGroup);
        userGroupDao.update(userGroup);
    }

    private void putDataIntoUserGroup(UserGroup userGroup){
        System.out.println("Podaj nazwę grupy:");
        userGroup.setName(scanner.nextLine());
    }

    private void deleteUserGroup() {
        System.out.println("Podaj id grupy");
        int userGroupId = ScannerUtil.returnIntGreaterThanZero();
        userGroupDao.delete(userGroupId);
    }
}

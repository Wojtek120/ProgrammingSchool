package util;

import java.util.Scanner;

public class ScannerUtil {

    public static int returnIntGreaterThanZero(){
        Scanner scanner = new Scanner(System.in);
        String stringFromScanner = null;
        boolean isDifferentThanInt = true;

        while(isDifferentThanInt){
            System.out.println("Podaj dodatnią liczbę całkowitą");
            stringFromScanner = scanner.nextLine();
            isDifferentThanInt = !stringFromScanner.matches("^[1-9]\\d*$");
        }

        return Integer.parseInt(stringFromScanner);
    }
}

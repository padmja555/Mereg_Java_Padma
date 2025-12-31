package ParentTestCases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class presentdate {

    // ✅ Returns today's date in MM/dd/yyyy format
    public static String getPresentDate() {
        LocalDate today = LocalDate.now();  // Gets today's date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return today.format(formatter);
    }

    public static void main(String[] args) {
        System.out.println("Present Date: " + getPresentDate());
    }
}

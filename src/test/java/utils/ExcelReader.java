package utils;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

public class ExcelReader {

    // ✅ Read & print Excel content
    public static void readExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            System.out.println("=== Reading Excel file: " + filePath + " ===");

            for (Row row : sheet) {
                for (Cell cell : row) {
                    System.out.print(cell.toString() + "\t");
                }
                System.out.println();
            }

            System.out.println("✅ Excel file read successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error reading Excel: " + e.getMessage());
        }
    }

    // ✅ Get the latest downloaded Excel file from a folder
    public static File getLatestDownloadedFile(String downloadDir) {
        File dir = new File(downloadDir);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".xlsx"));
        if (files == null || files.length == 0) {
            System.out.println("⚠️ No Excel files found in " + downloadDir);
            return null;
        }

        Arrays.sort(files, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
        return files[0]; // latest Excel file
    }
}

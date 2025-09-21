package Utilities;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    public static List<String> readColumn(String filePath, String sheetName, String columnName) {
        List<String> values = new ArrayList<>();
        DataFormatter fmt = new DataFormatter();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new RuntimeException("Sheet not found: " + sheetName);

            // find header row (try 0, then 1)
            Row headerRow = sheet.getRow(0);
            if (headerRow == null || headerRow.getPhysicalNumberOfCells() == 0) {
                headerRow = sheet.getRow(1);
            }
            if (headerRow == null) throw new RuntimeException("Header row not found");

            int colIndex = -1;
            for (Cell cell : headerRow) {
                String head = fmt.formatCellValue(cell).trim();
                if (head.equalsIgnoreCase(columnName)) { colIndex = cell.getColumnIndex(); break; }
            }
            if (colIndex == -1) throw new RuntimeException("Column not found: " + columnName);

            // read values
            int startRow = headerRow.getRowNum() + 1;
            for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                Cell cell = row.getCell(colIndex);
                if (cell == null) continue;
                String v = fmt.formatCellValue(cell).trim();
                if (!v.isEmpty()) values.add(v);
            }
        } catch (Exception e) {
            throw new RuntimeException("Excel read failed: " + e.getMessage(), e);
        }

        return values;
    }
}

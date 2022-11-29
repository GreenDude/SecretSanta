package org.GreenDude.SecretSanta;

import org.GreenDude.SecretSanta.models.Participant;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {

    public List<Participant> getParticipantList(String path) {
        List<Participant> participants = new ArrayList<>();
        FileInputStream file = null;
        Workbook workbook;
        Sheet sheet;
        int nameID = -1;
        int emailID = -1;


        try {
            file = new FileInputStream(new File(path));
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet("Form1");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert file != null;
                file.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                for (Cell cell : row) {
                    if (cell.getStringCellValue().equalsIgnoreCase("Name")) {
                        nameID = cell.getColumnIndex();
                    }
                    if (cell.getStringCellValue().equalsIgnoreCase("Email")) {
                        emailID = cell.getColumnIndex();
                    }
                    if (nameID != -1 && emailID != -1) {
                        break;
                    }
                }
            } else {
                participants.add(new Participant
                        (row.getCell(nameID).getStringCellValue(), row.getCell(emailID).getStringCellValue()));
            }
        }

        return participants;
    }
}

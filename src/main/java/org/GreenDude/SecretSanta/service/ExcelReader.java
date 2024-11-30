package org.GreenDude.SecretSanta.service;

import org.GreenDude.SecretSanta.models.Participant;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ExcelReader {

    private final String nameFieldKey;
    private final String emailFieldKey;
    private final String favoriteFieldKey;
    private final String isWillingFieldKey;

    public ExcelReader() {
        this.nameFieldKey = "Name";
        this.emailFieldKey = "Email";
        this.favoriteFieldKey = "favorite";
        this.isWillingFieldKey = "Do you want";
    }

    public List<Participant> getParticipantList(InputStream survey, String sheetName) {
        List<Participant> participants = new ArrayList<>();
        Workbook workbook;
        Sheet sheet;
        int nameID = -1;
        int emailID = -1;
        int favoritesID = -1;
        int isWillingID = -1;

        try {
            workbook = new XSSFWorkbook(survey);
            sheet = workbook.getSheet(sheetName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                for (Cell cell : row) {
                    if (cell.getStringCellValue().equalsIgnoreCase(nameFieldKey)) {
                        nameID = cell.getColumnIndex();
                    }
                    if (cell.getStringCellValue().equalsIgnoreCase(emailFieldKey)) {
                        emailID = cell.getColumnIndex();
                    }
                    if (cell.getStringCellValue().contains(favoriteFieldKey)) {
                        favoritesID = cell.getColumnIndex();
                    }
                    if (cell.getStringCellValue().contains(isWillingFieldKey)) {
                        isWillingID = cell.getColumnIndex();
                    }

                    if (nameID != -1 && emailID != -1 && favoritesID != -1) {
                        System.out.println("Name ID: ".concat(String.valueOf(nameID)));
                        System.out.println("Email ID: ".concat(String.valueOf(emailID)));
                        System.out.println("Favorites ID: ".concat(String.valueOf(favoritesID)));
                        System.out.println("Is Willing ID: ".concat(String.valueOf(isWillingID)));
                        break;
                    }
                }
            } else {
                if (Objects.nonNull(row.getCell(nameID)) && (isWillingID == -1 || row.getCell(isWillingID).getStringCellValue().equalsIgnoreCase("yes"))
                        && !row.getCell(emailID).getStringCellValue().equalsIgnoreCase("anonymous")) {
                    participants.add(new Participant
                            (row.getCell(nameID).getStringCellValue(),
                                    row.getCell(emailID).getStringCellValue(),
                                    row.getCell(favoritesID).getStringCellValue()));
                }
            }
        }

        return participants;
    }
}

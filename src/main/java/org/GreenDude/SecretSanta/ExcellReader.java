package org.GreenDude.SecretSanta;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ExcellReader {

    String fileLocation = "";

    FileInputStream file = new FileInputStream(new File(fileLocation));
    Workbook workbook = new XSSFWorkbook(file);

    public ExcellReader() throws IOException {
    }

    public List<String> get
}

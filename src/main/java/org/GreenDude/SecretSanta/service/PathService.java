package org.GreenDude.SecretSanta.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;

@Service
public class PathService {

    private String templatePath;
    private String excelPath;
    private String outputPath;
    private String fontPath;

    public PathService(){
//        String projectRoot = new File("").getAbsolutePath();
        String projectRoot = "classpath:";

        String resourcesPath = projectRoot
                .concat(File.separator)
                .concat("src")
                .concat(File.separator)
                .concat("resources")
                .concat(File.separator);

//        File file = ResourceUtils.getFile("classpath:templates/index.html");

        templatePath = resourcesPath.concat("cardTemplates").concat(File.separator);
        excelPath = resourcesPath.concat("excelSource").concat(File.separator);
        fontPath = resourcesPath.concat("fonts").concat(File.separator);
        outputPath = "target".concat(File.separator).concat("SecretSantaCards").concat(File.separator);
    }

    public String getTemplatePath(String templateName) {
        return templatePath.concat(templateName).concat(".jpg");
    }

    public String getExcelPath(String excelName) {
        return excelPath.concat(excelName).concat(".xlsx");
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getFontPath() {
        return fontPath;
    }
}

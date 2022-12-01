package org.GreenDude.SecretSanta;

import java.io.File;

public class PathSystem {

    private String templatePath;
    private String excelPath;
    private String outputPath;

    public PathSystem (){
        String projectRoot = new File("").getAbsolutePath();

        String resourcesPath = projectRoot
                .concat(File.separator)
                .concat("src")
                .concat(File.separator)
                .concat("resources")
                .concat(File.separator);

        templatePath = resourcesPath.concat("cardTemplates").concat(File.separator);
        excelPath = resourcesPath.concat("excelSource").concat(File.separator);
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
}

package com.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVProcessor {
    public static void processCSV(String csvFilePath, String outputDirPath, int rowCount) {
        try (FileReader fileReader = new FileReader(csvFilePath);
             CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build()) {

            String[] columnNames = new String[]{"Nome",";","Celular"};

            createNewCSVFile(new ArrayList<>(), outputDirPath, "output" + 1 + ".csv", columnNames);

            String[] nextRecord;
            List<String[]> records = new ArrayList<>();
            int currentRowCount = 0;
            int fileCount = 1;

            try {
                while ((nextRecord = csvReader.readNext()) != null) {
                    records.add(nextRecord);
                    currentRowCount++;

                    if (currentRowCount == rowCount) {
                        createNewCSVFile(records, outputDirPath, "output" + fileCount + ".csv", columnNames);
                        records.clear();
                        currentRowCount = 0;
                        fileCount++;
                    }
                }
            } catch (CsvValidationException e) {
                e.printStackTrace();
            }

            if (!records.isEmpty()) {
                createNewCSVFile(records, outputDirPath, "output" + fileCount + ".csv", columnNames);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createNewCSVFile(ArrayList<Object> arrayList, String outputDirPath, String fileName,
            String[] columnNames) {
    }

    private static void createNewCSVFile(List<String[]> data, String outputDirPath, String fileName, String[] columnNames) {
        File outputDir = new File(outputDirPath);

        if (!outputDir.exists()) {
            if (outputDir.mkdirs()) {
                System.out.println("Diretório de saída criado: " + outputDirPath);
            } else {
                System.err.println("Falha ao criar o diretório de saída: " + outputDirPath);
                return;
            }
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(outputDirPath + File.separator + fileName))) {
            writer.writeNext(columnNames);

            for (String[] record : data) {
                writer.writeNext(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

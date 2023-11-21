package com.example;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.File;

public class CSVProcessorGUI {
    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione o arquivo CSV com mais de 500 linhas para ser subdividido");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos CSV", "csv"));

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String csvFilePath = selectedFile.getAbsolutePath();

            int rowCount = askUserForRowCount();

            JFileChooser outputDirectoryChooser = new JFileChooser();
            outputDirectoryChooser.setDialogTitle("Agora selecione o diretório de saída");
            outputDirectoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int outputDirSelection = outputDirectoryChooser.showOpenDialog(null);

            if (outputDirSelection == JFileChooser.APPROVE_OPTION) {
                File outputDirectory = outputDirectoryChooser.getSelectedFile();
                String outputDirPath = outputDirectory.getAbsolutePath();

                CSVProcessor.processCSV(csvFilePath, outputDirPath, rowCount);

                JOptionPane.showMessageDialog(null, "Processo concluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum diretório de saída selecionado. O processo foi cancelado.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum arquivo CSV selecionado. O processo foi cancelado.");
        }
    }

    public static int askUserForRowCount() {
        String input = JOptionPane.showInputDialog("Digite o número de linhas para separar os arquivos:");
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número inválido. Use um número inteiro válido.");
            return askUserForRowCount();
        }
    }
}

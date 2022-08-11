package Modules;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileWriter {
    private ArrayList<String> lines = new ArrayList<>();
    private String fileName;

    public FileWriter(String fileName) {
        this.fileName = fileName;
    }

    public void addLine(String line) {
        lines.add(line);
    }

    public void write() {
        try {
            BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(fileName));
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

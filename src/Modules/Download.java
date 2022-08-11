package Modules;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Download {
    public Download(String FILE_URL, String FILE_NAME, String type) {
        try {
            switch (type) {
                case "--java":
                    InputStream in = new URL(FILE_URL).openStream();
                    Files.copy(in, Paths.get(FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
                    break;
                case "--wget":
                    Runtime.getRuntime().exec("powershell wget " + FILE_URL + " -OutFile " + FILE_NAME);
                    break;
                default:
                    Runtime.getRuntime().exec("powershell (new-object System.Net.WebClient).DownloadFile('" + FILE_URL + "','" + FILE_NAME + "');");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
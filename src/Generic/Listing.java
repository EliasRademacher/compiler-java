package Generic;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Elias on 3/8/2017.
 */
public final class Listing {

    /* listing output text file */
    private FileWriter fileWriter;

    private static Listing instance = null;

    private Listing() throws IOException {
        fileWriter = new FileWriter("./listing.txt", true);
    }

    public static Listing getInstance()  {
        if (instance == null) {
            try {
                instance = new Listing();
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

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
        fileWriter = new FileWriter("./listing.txt");
    }

    public static Listing getInstance() throws IOException {
        if (instance == null) {
            instance = new Listing();
        }
        return instance;
    }

    public void write(char[] cbuf) {
        try {
            fileWriter.write(cbuf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

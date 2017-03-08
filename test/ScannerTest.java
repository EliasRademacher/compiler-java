import junit.framework.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by Elias on 3/7/2017.
 *
 */
public class ScannerTest extends TestCase {


    private Scanner scanner;

    @Override
    public void setUp() throws Exception {
        this.scanner = new Scanner();
    }

    public void testGetNextChar() throws FileNotFoundException {
        FileReader fileReader = new FileReader("testResources/program1.cm");

        Integer linePosition = scanner.getNextChar(fileReader);

        assertNotNull(linePosition);
    }
}
import java.io.FileWriter;
import java.io.IOException;


public final class Globals {

    /* source line number for listing */
    public static int lineno;
    public static final int EOF = -1;

    /* source code text file */
    private FileWriter source;

    private static Globals instance = null;

    private Globals() {
        try {
            this.source = new FileWriter("./source.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Globals getInstance() {
        if (instance == null) {
            instance = new Globals();
        }
        return instance;
    }







    /**************************************************/
    /***********   Flags for tracing       ************/
    /**************************************************/

    /* echoSource = TRUE causes the source program to
     * be echoed to the listing file with line numbers
     * during parsing
     */
    public static boolean echoSource;


    /* traceScan = TRUE causes token information to be
     * printed to the listing file as each token is
     * recognized by the scanner
     */
    public static boolean traceScan;



}

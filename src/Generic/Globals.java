package Generic;

public final class Globals {

    /* source line number for listing */
    public static int lineno;

    /* Records if End of File has been reached. */
    public static final int EOF = -1;

    /* Error = true prevents further passes if an error occurs */
    public static boolean Error;



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

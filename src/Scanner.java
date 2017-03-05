import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Elias on 3/4/2017.
 */
public class Scanner {

    /* States in scanner DFA */
    public enum StateType {
        START, INASSIGN, INCOMMENT, INNUM, INID, DONE
    }

    /* The maximum size of a token */
    public final int MAXTOKENLEN = 40;

    /* The length of the input buffer for source code lines */
    public final int BUFLEN = 256;

    /* lexeme of identifier or reserved word */
    char tokenString[] = new char[MAXTOKENLEN];

    /* holds the current line */
    private char lineBuf[] = new char[BUFLEN];

    /* current position in LineBuf */
    private int linepos = 0;

    /* current size of buffer string */
    private int bufsize = 0;

    /* corrects ungetNextChar behavior on EOF */
    private boolean EOF_flag = false;

    /* getNextChar fetches the next non-blank character from
    lineBuf, reading in a new line if lineBuf is exhausted */
    private int getNextChar() {
        if (!(linepos < bufsize)) {
            Globals.lineno++;

            FileReader fileReader;
            try {
                fileReader = new FileReader("../test/resources/program1.cm");
                try {
                    Returns:
                    /* FileReader.read() returns the number of characters read,
                    or -1 if the end of the stream has been reached */
                    if (fileReader.read(lineBuf, 0, BUFLEN - 1) > 0) {
                        if (Globals.EchoSource) {
                            /* TODO: print the output to a file "listing". */
                            System.out.println(Globals.lineno + ": " + lineBuf);
                        }
                        bufsize = lineBuf.length;
                        linepos = 0;
                        return lineBuf[linepos++];
                    } else {
                        EOF_flag = true;
                        return Globals.EOF;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return lineBuf[linepos++];
    }


}

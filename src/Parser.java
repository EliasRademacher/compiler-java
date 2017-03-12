import java.io.FileNotFoundException;
import java.io.FileReader;

public class Parser {

    /* holds current token */
    private Token token;

    private Scanner scanner = new Scanner();

    private void syntaxError(String message) {
        Listing.getInstance().write(
                "\n>>> Syntax error at line" + Globals.lineno + ": " + message
        );
        Globals.Error = true;
    }

    /* TODO: pass in token as parameter to match()? */
    private void match(Token expectedToken, FileReader fileReader) throws FileNotFoundException {
        if (token == expectedToken) {
            token = scanner.getToken(fileReader);
        }

        else {
            syntaxError("unexpected token -> ");

        /* TODO: how can I associate a tokenString with a Token?
         * Should I create a Token class? */
            Utils.printToken(token, "replace with tokenString");
            Listing.getInstance().write("      ");
        }
    }

}

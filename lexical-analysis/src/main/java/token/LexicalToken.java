package token;

/**
 * Lexical Tokens are the tokens accepted by the state machine and land on a non-error final state.
 */

public class LexicalToken extends AbstractToken {

    // Store next and previous tokens
    AbstractToken next, previous;

    public LexicalToken(String token, String value, int row, int col, int position) {
        super(token, value, row, col, position);
    }

    public AbstractToken getNext() {
        return next;
    }

    public void setNext(AbstractToken next) {
        this.next = next;
    }

    public AbstractToken getPrevious() {
        return previous;
    }

    public void setPrevious(AbstractToken previous) {
        this.previous = previous;
    }
}

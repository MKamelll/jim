import static java.lang.Character.*;

public class Lexer {
    private String mSource;
    private int mCurrent;

    Lexer(String src) {
        mSource = src;
        mCurrent = 0;
    }

    private char curr() {
        return mSource.charAt(mCurrent);
    }

    private void advance() {
        mCurrent++;
    }

    private boolean isAtEnd() {
        if (mCurrent < mSource.length()) return false;
        return true;
    }

    public Token next() throws Exception {
        if (isAtEnd()) return new Token(TokenType.EOF, "EOF");
        switch (curr()) {
            case '(':
            { 
                advance();
                return new Token(TokenType.LEFT_PAREN, '(');
            }
            case ')':
            {
                advance();
                return new Token(TokenType.RIGHT_PAREN, ')');
            }
            case '+':
            { 
                advance();
                return new Token(TokenType.PLUS, '+');
            }
            case '-': 
            {
                advance();
                return new Token(TokenType.MINUS, '-');
            } 
            case '*': {
                advance();
                return new Token(TokenType.STAR, '*');
            }
            case '/':
            {
                advance();
                return new Token(TokenType.SLASH, '/');
            }
            case ',':
            {
                advance();
                return new Token(TokenType.COMMA, ',');
            }
            case '=':
            {
                advance();
                return new Token(TokenType.EQUAL, '=');
            }
            case '{':
            {
                advance();
                return new Token(TokenType.LEFT_BRACE, '{');
            }
            case '}':
            {
                advance();
                return new Token(TokenType.RIGHT_BRACE, '}');
            }
            case '[':
            {
                advance();
                return new Token(TokenType.LEFT_SQUARE, '[');
            }
            case ']':
            {
                advance();
                return new Token(TokenType.RIGHT_SQUARE, ']');
            }
            case '%':
            {
                advance();
                return new Token(TokenType.MOD, '%');
            }
            case '^':
            {
                advance();
                return new Token(TokenType.CARROT, '^');
            }
            case ' ': case '\t': case '\n':
            {
                advance();
                return next();
            }
            default: {
                if (Character.isDigit(curr())) {
                    var num = number();
                    return new Token(TokenType.DOUBLE, num);
                } else if (Character.isAlphabetic(curr())) {
                    var ident = identifier();
                    if (ident.equals("function")) {
                        return new Token(TokenType.FUNCTION, ident);
                    }
                    return new Token(TokenType.IDENTIFIER, ident);
                }
                
                throw new Exception(String.format("Unknown Token %s", curr()));
            }
        }
    }

    private Number number() {
        var result = new StringBuilder();
        while (!isAtEnd()) {
            if (curr() == '.') {
                result.append(curr());
                advance();
                continue;
            }
            if (!isDigit(curr())) break;;

            result.append(curr());
            advance();
        }

        return Double.valueOf(result.toString());
    }

    private String identifier() {
        var result = new StringBuilder();
        while (!isAtEnd()) {
            if (!isAlphabetic(curr())) break;
            result.append(curr());
            advance();
        }

        return result.toString();
    }
}

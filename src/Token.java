public class Token {
    private TokenType mType;
    private Object mLexeme;
    
    Token(TokenType type, Object lexeme) {
        mType = type;
        mLexeme = lexeme;
    }

    TokenType getType() {
        return mType;
    }

    Object getLexeme() {
        return mLexeme;
    }

    @Override
    public String toString() {
        if (mLexeme instanceof String || mLexeme instanceof Character)
            return "Token(" + mType.name() + ", " + "'" + mLexeme + "'" + ")";

        return "Token(" + mType.name() + ", " + mLexeme + ")";
    }
}

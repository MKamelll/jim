import java.util.ArrayList;

public class Parser {
    private Lexer mLexer;
    private Token mCurrentToken;
    private Token mPrevToken;
    private ArrayList<Expression> mTrees;

    Parser(Lexer lexer) throws Exception {
        mLexer = lexer;
        mCurrentToken = mLexer.next();
        mPrevToken = mCurrentToken;
        mTrees = new ArrayList<Expression>();
    }

    private void advance() throws Exception {
        mPrevToken = mCurrentToken;
        mCurrentToken = mLexer.next();
    }

    private Token curr() {
        return mCurrentToken;
    }

    private Token prev() {
        return mPrevToken;
    }

    private boolean isAtEnd() {
        if (mCurrentToken.getType() == TokenType.EOF) return true;
        return false;
    }

    private boolean match(TokenType... types) throws Exception {
        for (var type : types) {
            if (curr().getType() == type) {
                advance();
                return true;
            }
        }
        return false;
    }

    private void consume(String ch) throws Exception {
        if (!mCurrentToken.getLexeme().toString().equals(ch)) {
            throw new Exception("Expected '" + ch + "', instead got '" + curr().getLexeme().toString() + "'");
        }
        advance();
    }

    private void consume(String ch, String hint) throws Exception {
        if (!mCurrentToken.getLexeme().toString().equals(ch)) {
            throw new Exception("Expected '" + ch
                + "', instead got '" + curr().getLexeme().toString() + "', \n hint: " + hint);
        }
        advance();
    }

    private OpInfo getOpInfo(String op) {
        switch (op) {
            case "=": return new OpInfo(3, Associativity.RIGHT);
            case "+": return new OpInfo(4, Associativity.LEFT);
            case "-": return new OpInfo(4, Associativity.LEFT);
            case "*": return new OpInfo(5, Associativity.LEFT);
            case "/": return new OpInfo(5, Associativity.LEFT);
            case "%": return new OpInfo(5, Associativity.LEFT);
            case "^": return new OpInfo(6, Associativity.RIGHT);
            default: break;
        }
        return new OpInfo(-1, Associativity.NONE);
    }

    ArrayList<Expression> parse() throws Exception {
        if (isAtEnd()) return mTrees;
        if (match(TokenType.LET)) {
            Expression expr = parseLet();
            consume(";");
            mTrees.add(expr);
            return parse();
        }
        Expression expr = parseExpr(0);
        mTrees.add(expr);
        return parse();
    }

    private Expression parseLet() throws Exception {
        Expression expr = parseExpr(0);
        return new StmtExpr.Let(expr);
    }

    private Expression parseExpr(int minPrec) throws Exception {
        Expression lhs = parsePrimary();

        while (true) {
            Token op = curr();
            var opInfo = getOpInfo(op.getLexeme().toString());
            var opPrec = opInfo.getPrec();
            var opAssoc = opInfo.getAssoc();

            if (opPrec == -1 || opPrec < minPrec) break;

            var nextMinPrec = opAssoc == Associativity.LEFT ? opPrec + 1 : opPrec;

            advance();
            Expression rhs = parseExpr(nextMinPrec);
            lhs = new Binary(op.getLexeme().toString(), lhs, rhs);
        }

        return lhs;
    }

    private Expression parsePrimary() throws Exception {
        return parseNumber();
    }
    
    private Expression parseNumber() throws Exception {
        if (match(TokenType.DOUBLE)) {
            return new Primary.Number(prev().getLexeme());
        } else if (match(TokenType.LEFT_PAREN)) {
            Expression expr = parseExpr(0);
            consume(")");
            return expr;
        }

        return parseIdentifier();
    }
    
    private Expression parseIdentifier() throws Exception {
        if (match(TokenType.IDENTIFIER)) {
            return new Primary.Identifier(prev().getLexeme());
        }
        
        throw new RuntimeException("Expected primary instead got, " + curr().getLexeme().toString());
    }

}

import java.util.ArrayList;

public class Interpreter implements Visitor {
    
    private ArrayList<Expression> mTree;
    private int mCurrIndex;
    private Object mResult;
    private ArrayList<Symbol> mSymbols;
    private int mCurrDepth;

    Interpreter(ArrayList<Expression> tree) {
        mTree = tree;
        mCurrIndex = 0;
        mSymbols = new ArrayList<Symbol>();
        mCurrDepth = 0;
    }

    private boolean isAtEnd() {
        if (mCurrIndex < mTree.size()) return false;
        return true;
    }

    private void advance() {
        mCurrIndex++;
    }

    private Expression curr() {
        return mTree.get(mCurrIndex);
    }

    private Expression prev() {
        return mTree.get(mCurrIndex - 1);
    }

    private void incCurrDepth() {
        mCurrDepth++;
    }

    private void decCurrDepth() {
        mCurrDepth--;
    }

    private Object getSymbol(String name) throws Exception {
        for (int i = mSymbols.size() - 1; i >= 0; i--) {
            var symbol = mSymbols.get(i);
            if (symbol.getName().equals(name) && symbol.getDepth() <= mCurrDepth)
                return symbol.getValue();
        }

        throw new Exception("Undefined symbol '" + name + "'");
    }

    private void addSymbol(String name, Object value) {
        mSymbols.add(new Symbol(name, value, mCurrDepth));
    }

    private void addSymbol(String name, Object value, int depth) {
        mSymbols.add(new Symbol(name, value, depth));
    }

    public Object interpret() throws Exception {
        if (isAtEnd()) { System.out.println(mSymbols); return mResult; }
        curr().accept(this);
        advance();
        return interpret();
    }

    @Override
    public void visit(Primary.Number node) throws Exception {
        mResult = node.getValue();
    }

    @Override
    public void visit(Primary.Identifier node) throws Exception {
        mResult = getSymbol(node.getValue().toString());
    }

    @Override
    public void visit(Binary node) throws Exception {
        switch (node.getOp()) {
            case "+":
            {
                node.getLhs().accept(this);
                var lhs = Double.valueOf(mResult.toString());
                node.getRhs().accept(this);
                var rhs = Double.valueOf(mResult.toString());
                mResult = lhs + rhs;
                break;
            }
            case "-":
            {
                node.getLhs().accept(this);
                var lhs = Double.valueOf(mResult.toString());
                node.getRhs().accept(this);
                var rhs = Double.valueOf(mResult.toString());
                mResult = lhs - rhs;
                break;
            }
            case "*":
            {
                node.getLhs().accept(this);
                var lhs = Double.valueOf(mResult.toString());
                node.getRhs().accept(this);
                var rhs = Double.valueOf(mResult.toString());
                mResult = lhs * rhs;
                break;
            }         
            case "/":
            {
                node.getLhs().accept(this);
                var lhs = Double.valueOf(mResult.toString());
                node.getRhs().accept(this);
                var rhs = Double.valueOf(mResult.toString());
                mResult = lhs / rhs;
                break;
            }         
            case "%":
            {
                node.getLhs().accept(this);
                var lhs = Double.valueOf(mResult.toString());
                node.getRhs().accept(this);
                var rhs = Double.valueOf(mResult.toString());
                mResult = lhs % rhs;
                break;
            }
            case "^":
            {
                node.getLhs().accept(this);
                var lhs = Double.valueOf(mResult.toString());
                node.getRhs().accept(this);
                var rhs = Double.valueOf(mResult.toString());
                mResult = Math.pow(lhs, rhs);
                break;
            }         
        }
    }

    @Override
    public void visit(StmtExpr.Let node) throws Exception {
        Expression expr = node.getExpr();
        if (!(expr instanceof Binary)) {
            throw new Exception("Expected a variable definition after let");
        }

        Expression lhs = ((Binary) expr).getLhs();
        if (!(lhs instanceof Primary.Identifier)) {
            throw new Exception("Expected an identifier after let");
        }

        String identifier = ((Primary.Identifier) lhs).getValue().toString();
        ((Binary) expr).getRhs().accept(this);
        addSymbol(identifier, mResult);
    }

    @Override
    public void visit(StmtExpr.Block node) throws Exception {
        incCurrDepth();
        try {
            ArrayList<Expression> list = node.getExprs();
            for (var expr : list) {
                expr.accept(this);
            }
        } finally {
            decCurrDepth();
        }
    }

    @Override
    public void visit(StmtExpr.Function node) throws Exception {
        String identifier = ((Primary.Identifier) node.getIdentifier()).getValue().toString();
        addSymbol(identifier, node);
    }

    @Override
    public void visit(StmtExpr.Return node) throws Exception {
        node.getExpr().accept(this);
        throw new Return(mResult);
    }

    @Override
    public void visit(StmtExpr.Call node) throws Exception {
        var identifier = node.getIdentifier();
        if (!(identifier instanceof Primary.Identifier)) throw new Exception("Expected an identifier");
        var function = (StmtExpr.Function) getSymbol(((Primary.Identifier) identifier).getValue().toString());
        var params = function.getParams();
        var args = node.getArgs();
        if (args.size() != params.size())
            throw new Exception("Expected '" + params.size() + "' parameters, indstead got '" + args.size() + "'");
        
        incCurrDepth();
        for (int i = 0; i < params.size(); i++) {
            args.get(i).accept(this);
            addSymbol(((Primary.Identifier) params.get(i)).getValue().toString(), mResult);
        }
        decCurrDepth();

        try {
            function.getBlock().accept(this);
        } catch (Return ex) {
            mResult = ex.getValue();
            return;
        }

        mResult = null;
    }
}

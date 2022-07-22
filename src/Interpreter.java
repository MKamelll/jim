import java.util.ArrayList;

public class Interpreter implements Visitor {
    
    private ArrayList<Expression> mTree;
    private int mCurrIndex;
    private Object mResult;
    private Environment mGlobals;
    private Environment mCurrEnv;

    Interpreter(ArrayList<Expression> tree) {
        mTree = tree;
        mCurrIndex = 0;
        mGlobals = new Environment();
        mCurrEnv = mGlobals;
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

    public Object interpret() throws Exception {
        if (isAtEnd()) return mResult;
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
        mResult = mCurrEnv.get(node.getValue().toString());
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
        mGlobals.define(identifier, Double.valueOf(mResult.toString()));
    }

    public void excuteBlock(StmtExpr.Block node, Environment environment) throws Exception {
        Environment previous = mCurrEnv;
        
        mCurrEnv = environment;
        ArrayList<Expression> list = node.getExprs();
        for (var expr : list) {
            expr.accept(this);
        }
        mCurrEnv = previous;
    }

    @Override
    public void visit(StmtExpr.Block node) throws Exception {
        excuteBlock(node, mCurrEnv);
    }

    @Override
    public void visit(StmtExpr.Function node) throws Exception {
        String identifier = ((Primary.Identifier) node.getIdentifier()).getValue().toString();
        mGlobals.define(identifier, node);
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
        var function = (StmtExpr.Function) mGlobals.get(((Primary.Identifier) identifier).getValue().toString());
        var params = function.getParams();
        var args = node.getArgs();
        if (args.size() != params.size())
            throw new Exception("Expected '" + params.size() + "' parameters, indstead got '" + args.size() + "'");
        
        for (int i = 0; i < params.size(); i++) {
            args.get(i).accept(this);
            mGlobals.define(((Primary.Identifier) params.get(i)).getValue().toString(), mResult);
        }

        try {
            function.getBlock().accept(this);
        } catch (Return ex) {
            mResult = ex.getValue();
            return;
        }

        mResult = null;
    }
}

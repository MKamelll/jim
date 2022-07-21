import java.util.ArrayList;

public class Interpeter implements Visitor {
    
    private ArrayList<Expression> mTree;
    private int mCurrIndex;
    private Object mResult;
    private Environment mGlobals;

    Interpeter(ArrayList<Expression> tree) {
        mTree = tree;
        mCurrIndex = 0;
        mGlobals = new Environment();
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
    public void visit(Primary node) throws Exception {
        if (node instanceof Primary.Number) {
            mResult = node.getValue();
        } else if (node instanceof Primary.Identifier) {
            mResult = mGlobals.get(node.getValue().toString());
        }
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

    @Override
    public void visit(StmtExpr.Block node) throws Exception {
        ArrayList<Expression> list = node.getExprs();
        for (var expr : list) {
            expr.accept(this);
        }
    }
}

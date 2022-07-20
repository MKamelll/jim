import java.util.ArrayList;

public class Interpeter implements Visitor {
    
    private ArrayList<Expression> mTree;
    private int mCurrIndex;
    private Object mResult;

    Interpeter(ArrayList<Expression> tree) {
        mTree = tree;
        mCurrIndex = 0;
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

    public Object interpret() {
        if (isAtEnd()) return mResult;
        curr().accept(this);
        advance();
        return interpret();
    }

    @Override
    public void visit(Primary node) {
        if (node instanceof Primary.Number) {
            mResult = node.getValue();
        } else if (node instanceof Primary.Identifier) {
            mResult = node.getValue();
        }
    }

    @Override
    public void visit(Binary node) {
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
}

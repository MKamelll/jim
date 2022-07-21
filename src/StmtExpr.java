import java.util.ArrayList;

abstract public class StmtExpr extends Expression {
    public abstract void accept(Visitor v) throws Exception;
    
    public static class Let extends StmtExpr
    {
        Expression mExpr;
        
        Let(Expression expr) {
            mExpr = expr;
        }

        Expression getExpr() {
            return mExpr;
        }

        @Override
        public String toString() {
            return "let(" + mExpr.toString() + ")";
        } 

        @Override
        public void accept(Visitor v) throws Exception {
            v.visit(this);
        }
    }

    public static class Block extends StmtExpr
    {
        ArrayList<Expression> mExprs;
        
        Block(ArrayList<Expression> list) {
            mExprs = list;
        }

        ArrayList<Expression> getExprs() {
            return mExprs;
        }

        @Override
        public String toString() {
            return "Block(" + mExprs.toString() + ")";
        }

        @Override
        public void accept(Visitor v) throws Exception {
            v.visit(this);
        }
    }
}

import java.util.ArrayList;

abstract public class StmtExpr extends Expression {
    public abstract void accept(Visitor v) throws Exception;
    
    public static class Let extends StmtExpr
    {
        private Expression mExpr;
        
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
        private ArrayList<Expression> mExprs;
        
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

    public static class Function extends StmtExpr
    {
        private Expression mIdentifier;
        private ArrayList<Expression> mParams;
        private Expression mBlock;

        Function(Expression identifier, ArrayList<Expression> params, Expression block) {
            mIdentifier = identifier;
            mParams = params;
            mBlock = block;
        }

        Expression getIdentifier() {
            return mIdentifier;
        }

        ArrayList<Expression> getParams() {
            return mParams;
        }

        Expression getBlock() {
            return mBlock;
        }

        @Override
        public String toString() {
            return "Function(params: " + mParams.toString() + ", block: " + mBlock.toString() + ")";
        }
        
        @Override
        public void accept(Visitor v) throws Exception {
            v.visit(this);
        }
    }

    public static class Return extends StmtExpr
    {
        private Expression mExpr;
        
        Return(Expression expr) {
            mExpr = expr;
        }

        Expression getExpr() {
            return mExpr;
        }

        @Override
        public String toString() {
            return "Return(" + mExpr.toString() + ")";
        }

        @Override
        public void accept(Visitor v) throws Exception {
            v.visit(this);
        }
    }
}

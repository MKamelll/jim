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
}

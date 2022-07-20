public class Binary extends Expression {
    private String mOp;
    private Expression mLhs;
    private Expression mRhs;

    Binary(String op, Expression lhs, Expression rhs) {
        mOp = op;
        mLhs = lhs;
        mRhs = rhs;
    }

    String getOp() {
        return mOp;
    }

    Expression getLhs() {
        return mLhs;
    }

    Expression getRhs() {
        return mRhs;
    }

    @Override
    public String toString() {
        return "Binary(op: '" + mOp + "', lhs: " + mLhs.toString() + ", rhs: " + mRhs.toString() + ")";
    }

    @Override
    public void accept(Visitor v) throws Exception {
        v.visit(this);
    }

}
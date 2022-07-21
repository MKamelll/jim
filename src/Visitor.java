public interface Visitor {
    public void visit(Primary.Number node) throws Exception;
    public void visit(Primary.Identifier node) throws Exception;
    public void visit(Binary node) throws Exception;
    public void visit(StmtExpr.Let node) throws Exception;
    public void visit(StmtExpr.Block node) throws Exception;
    public void visit(StmtExpr.Function node) throws Exception;
    public void visit(StmtExpr.Return node) throws Exception;
    public void visit(StmtExpr.Call node) throws Exception;

}

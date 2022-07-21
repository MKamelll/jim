public interface Visitor {
    public void visit(Primary node) throws Exception;
    public void visit(Binary node) throws Exception;
    public void visit(StmtExpr.Let node) throws Exception;
    public void visit(StmtExpr.Block node) throws Exception;
}

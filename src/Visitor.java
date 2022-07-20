public interface Visitor {
    public void visit(Primary node) throws Exception;
    public void visit(Binary node) throws Exception;
}

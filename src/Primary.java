abstract public class Primary extends Expression {
    protected Object mValue;
    abstract Object getValue();

    static class Number extends Primary {
        Number(Object value) {
           mValue = value;
        }
        
        @Override
        Object getValue() {
            return mValue;
        }

        @Override
        public String toString() {
            return "Number(" + mValue.toString() + ")";
        }

        @Override
        public void accept(Visitor v) {
            v.visit(this);
        }
    }

    static class Identifier extends Primary {
        Identifier(Object value) {
           mValue = value;
        }

        @Override
        Object getValue() {
            return mValue;
        }

        @Override
        public String toString() {
            return "Identifier(" + mValue.toString() + ")";
        }

        @Override
        public void accept(Visitor v) {
            v.visit(this);
        }
    }
}
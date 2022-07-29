package jim.src;

public class Symbol {
    private String mName;
    private Object mValue;
    private int mDepth;
    
    Symbol(String nama, Object value, int depth) {
        mName = nama;
        mValue = value;
        mDepth = depth;
    }

    String getName() {
        return mName;
    }

    Object getValue() {
        return mValue;
    }

    int getDepth() {
        return mDepth;
    }

    @Override
    public String toString() {
        return "symbol(name: " + mName + ", value: " + mValue.toString() + ", depth: " + mDepth + ")";
    }
}

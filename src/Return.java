public class Return extends RuntimeException {
    private Object mValue;
    
    Return(Object value) {
        super(null, null, false, false);
        mValue = value;
    }

    Object getValue() {
        return mValue;
    }
}
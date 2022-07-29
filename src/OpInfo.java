package jim.src;

public class OpInfo {
    private int mPrec;
    private Associativity mAssoc;

    OpInfo(int prec, Associativity assoc) {
        mPrec = prec;
        mAssoc = assoc;
    }

    int getPrec() {
        return mPrec;
    }

    Associativity getAssoc() {
        return mAssoc;
    }
}

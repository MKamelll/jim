import java.util.HashMap;

public class Environment {
    HashMap<String, Object> mClosure;
    Environment mEnclosing;

    Environment() {
        mClosure = new HashMap<String, Object>();
        mEnclosing = null;
    }

    Environment(Environment env) {
        mClosure = new HashMap<String, Object>();
        mEnclosing = env;
    }

    void define(String identifier, Object value) {
        mClosure.put(identifier, value);
    }

    Object get(String identifier) throws Exception {
        if (mClosure.containsKey(identifier)) { 
            return mClosure.get(identifier);
        } else if (mEnclosing != null) {
            mEnclosing.get(identifier);
        }

        throw new Exception("Undefined variable '" + identifier + "'");

    }
}

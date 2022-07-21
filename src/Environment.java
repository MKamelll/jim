import java.util.HashMap;

public class Environment {
    HashMap<String, Object> mData;
    Environment mEnclosing;

    Environment() {
        mData = new HashMap<String, Object>();
        mEnclosing = null;
    }

    Environment(Environment env) {
        mData = new HashMap<String, Object>();
        mEnclosing = env;
    }

    void define(String identifier, Object value) {
        mData.put(identifier, value);
    }

    Object get(String identifier) throws Exception {
        if (mData.containsKey(identifier)) { 
            return mData.get(identifier);
        } else if (mEnclosing != null) {
            mEnclosing.get(identifier);
        }

        throw new Exception("Undefined variable '" + identifier + "'");

    }
}

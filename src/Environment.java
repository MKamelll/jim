import java.util.HashMap;

public class Environment {
    HashMap<String, Double> mData;
    Environment mEnclosing;

    Environment() {
        mEnclosing = null;
    }

    Environment(Environment env) {
        mEnclosing = env;
    }

    void define(String identifier, Double value) {
        mData.put(identifier, value);
    }

    Double get(String identifier) throws Exception {
        if (mData.containsKey(identifier)) { 
            return mData.get(identifier);
        } else if (mEnclosing != null) {
            mEnclosing.get(identifier);
        }

        throw new Exception("Undefined variable '" + identifier + "'");

    }
}

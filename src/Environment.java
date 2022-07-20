import java.util.HashMap;

public class Environment {
    HashMap<String, Double> mData;
    Environment mEnclosing;

    Environment() {
        mData = new HashMap<String, Double>();
        mEnclosing = null;
    }

    Environment(Environment env) {
        mData = new HashMap<String, Double>();
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

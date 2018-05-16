package dijkspicy.demo.antlr4.json;

import java.util.HashMap;

/**
 * pralgo
 *
 * @author dijkspicy
 * @date 2018/5/13
 */
public class JSONObject extends HashMap<String, Object> implements JSON {
    public static final JSONObject EMPTY = new JSONObject();
}

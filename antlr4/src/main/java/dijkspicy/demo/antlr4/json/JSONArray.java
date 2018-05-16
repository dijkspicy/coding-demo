package dijkspicy.demo.antlr4.json;

import java.util.LinkedList;

/**
 * pralgo
 *
 * @author dijkspicy
 * @date 2018/5/13
 */
public class JSONArray extends LinkedList<Object> implements JSON {
    public static final JSONArray EMPTY = new JSONArray();
}

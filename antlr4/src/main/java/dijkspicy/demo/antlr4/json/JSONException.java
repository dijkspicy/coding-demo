package dijkspicy.demo.antlr4.json;

/**
 * pralgo
 *
 * @author dijkspicy
 * @date 2018/5/13
 */
public class JSONException extends RuntimeException {
    private static final long serialVersionUID = -7553571376204297480L;

    public JSONException(String msg, Throwable e) {
        super(msg, e);
    }

    public JSONException(String msg) {
        super(msg);
    }
}

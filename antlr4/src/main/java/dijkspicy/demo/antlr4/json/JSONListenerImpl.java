package dijkspicy.demo.antlr4.json;

import dijkspicy.demo.antlr4.parser.JSONBaseListener;
import dijkspicy.demo.antlr4.parser.JSONParser;
import javafx.util.Pair;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.math.BigDecimal;

/**
 * pralgo
 *
 * @author dijkspicy
 * @date 2018/5/13
 */
public class JSONListenerImpl extends JSONBaseListener {
    private final JSONParser.JsonContext context;
    private ParseTreeProperty<Object> objectProps = new ParseTreeProperty<>();

    public JSONListenerImpl(JSONParser.JsonContext context) {
        this.context = context;
    }

    private static String trim(String text, char c) {
        if (text.isEmpty()) {
            return text;
        }

        int start = -1;
        while (start++ < text.length()) {
            if (text.charAt(start) != c) {
                break;
            }
        }

        if (start >= text.length()) {
            return "";
        }

        int end = text.length();
        while (end-- > start) {
            if (text.charAt(end) != c) {
                end++;
                break;
            }
        }
        return text.substring(start, end);
    }

    @Override
    public void exitJson(JSONParser.JsonContext ctx) {
        if (ctx.array() != null) {
            this.objectProps.put(ctx, this.objectProps.get(ctx.array()));
        } else if (ctx.object() != null) {
            this.objectProps.put(ctx, this.objectProps.get(ctx.object()));
        } else {
            this.objectProps.put(ctx, JSON.NULLABLE);
        }
    }

    @Override
    public void exitObject_normal(JSONParser.Object_normalContext ctx) {
        JSONObject json = new JSONObject();
        ctx.pair().forEach(it -> {
            Object value = this.objectProps.get(it);
            if (value instanceof Pair) {
                Pair pair = (Pair) value;
                json.put(String.valueOf(pair.getKey()), pair.getValue());
            }
        });
        this.objectProps.put(ctx, json);
    }

    @Override
    public void exitObject_empty(JSONParser.Object_emptyContext ctx) {
        this.objectProps.put(ctx, JSONObject.EMPTY);
    }

    @Override
    public void exitArray_normal(JSONParser.Array_normalContext ctx) {
        JSONArray array = new JSONArray();
        ctx.value().forEach(it -> {
            Object value = objectProps.get(it);
            array.add(value);
        });
        this.objectProps.put(ctx, array);
    }

    @Override
    public void exitArray_empty(JSONParser.Array_emptyContext ctx) {
        this.objectProps.put(ctx, JSONArray.EMPTY);
    }

    @Override
    public void exitPair(JSONParser.PairContext ctx) {
        Object key = objectProps.get(ctx.key());
        Object value = objectProps.get(ctx.value());
        this.objectProps.put(ctx, new Pair<>(key, value));
    }

    @Override
    public void exitKey_string(JSONParser.Key_stringContext ctx) {
        this.objectProps.put(ctx, trim(ctx.getText(), '"'));
    }

    @Override
    public void exitKey_id(JSONParser.Key_idContext ctx) {
        this.objectProps.put(ctx, ctx.getText());
    }

    @Override
    public void exitValue_string(JSONParser.Value_stringContext ctx) {
        objectProps.put(ctx, trim(ctx.getText(), '"'));
    }

    @Override
    public void exitValue_number(JSONParser.Value_numberContext ctx) {
        BigDecimal number = new BigDecimal(ctx.getText());
        if (number.subtract(BigDecimal.valueOf(Double.MAX_VALUE)).doubleValue() > 0
                || BigDecimal.valueOf(Double.MIN_VALUE).subtract(number).doubleValue() > 0) {
            objectProps.put(ctx, number);
        } else {
            Double value = Double.valueOf(ctx.getText());
            if (value == value.intValue()) {
                objectProps.put(ctx, value.intValue());
            } else {
                objectProps.put(ctx, value);
            }
        }
    }

    @Override
    public void exitValue_non_space(JSONParser.Value_non_spaceContext ctx) {
        this.objectProps.put(ctx, ctx.getText());
    }

    @Override
    public void exitValue_object(JSONParser.Value_objectContext ctx) {
        this.objectProps.put(ctx, objectProps.get(ctx.object()));
    }

    @Override
    public void exitValue_array(JSONParser.Value_arrayContext ctx) {
        this.objectProps.put(ctx, objectProps.get(ctx.array()));
    }

    @Override
    public void exitValue_true(JSONParser.Value_trueContext ctx) {
        objectProps.put(ctx, true);
    }

    @Override
    public void exitValue_false(JSONParser.Value_falseContext ctx) {
        objectProps.put(ctx, false);
    }

    @Override
    public void exitValue_null(JSONParser.Value_nullContext ctx) {
        objectProps.put(ctx, JSON.NULLABLE);
    }

    public JSON getJson() {
        return (JSON) this.objectProps.get(context);
    }
}

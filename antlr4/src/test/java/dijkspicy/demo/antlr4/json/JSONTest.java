package dijkspicy.demo.antlr4.json;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * pralgo
 *
 * @author dijkspicy
 * @date 2018/5/13
 */
public class JSONTest {

    @Test
    public void testFromString_stringwithblank() {
        var input = "{\n" +
                "    \"adf fads\": 111\n" +
                "}";
        var json = JSON.fromString(input);
        assertEquals(json.getClass(), JSONObject.class);

        var object = (JSONObject) json;
        assertEquals(object.get("adf fads"), 111);
    }

    @Test
    public void testError() {
        var input = "{1}";
        try {
            var json = JSON.fromString(input);
        } catch (JSONException e) {
            assertTrue(true);
        }
    }
}
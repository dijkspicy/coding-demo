package dijkspicy.demo.antlr4.expr;

import org.testng.annotations.Test;

/**
 * pralgo
 *
 * @author dijkspicy
 * @date 2018/5/13
 */
public class ExpressionTest {

    @Test
    public void testCalculate() {
        Number result = Expression.calculate("1+2+3+4+5+6-7*(8+9)/10--2");
        System.out.println(result);
    }
}
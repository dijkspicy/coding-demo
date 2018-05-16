package dijkspicy.demo.antlr4.expr;

import dijkspicy.demo.antlr4.parser.ExprBaseListener;
import dijkspicy.demo.antlr4.parser.ExprParser;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * djkspcy.antlr4.parser.expr
 *
 * @author t00321127
 * @date 2018/5/12
 */
public class ExprListenerImpl extends ExprBaseListener {
    private final ExprParser.ExprContext parseTree;
    private ParseTreeProperty<BigDecimal> numbers = new ParseTreeProperty<>();

    public ExprListenerImpl(ExprParser.ExprContext parseTree) {
        this.parseTree = parseTree;
    }

    @Override
    public void exitMul_Div_Mod(ExprParser.Mul_Div_ModContext ctx) {
        BigDecimal left = this.numbers.get(ctx.expr(0));
        BigDecimal right = this.numbers.get(ctx.expr(1));
        if (ctx.MUL() != null) {
            numbers.put(ctx, left.multiply(right));
        } else if (ctx.DIV() != null) {
            if (!right.equals(BigDecimal.ZERO)) {
                numbers.put(ctx, left.divide(right, 2, RoundingMode.HALF_UP));
            }
        } else if (ctx.MOD() != null) {
            numbers.put(ctx, left.remainder(right));
        }
    }

    @Override
    public void exitNegativeNumber(ExprParser.NegativeNumberContext ctx) {
        this.numbers.put(ctx, new BigDecimal(ctx.getText()));
    }

    @Override
    public void exitNumber(ExprParser.NumberContext ctx) {
        this.numbers.put(ctx, new BigDecimal(ctx.getText()));
    }

    @Override
    public void exitAdd_Sub(ExprParser.Add_SubContext ctx) {
        BigDecimal left = this.numbers.get(ctx.expr(0));
        BigDecimal right = this.numbers.get(ctx.expr(1));
        if (ctx.ADD() != null) {
            numbers.put(ctx, left.add(right));
        } else if (ctx.SUB() != null) {
            numbers.put(ctx, left.subtract(right));
        }
    }

    @Override
    public void exitPow(ExprParser.PowContext ctx) {
        BigDecimal left = this.numbers.get(ctx.expr(0));
        BigDecimal right = this.numbers.get(ctx.expr(1));
        if (ctx.POW() != null) {
            numbers.put(ctx, left.pow(right.intValue()));
        }
    }

    @Override
    public void exitParentheses(ExprParser.ParenthesesContext ctx) {
        this.numbers.put(ctx, this.numbers.get(ctx.expr()));
    }

    public Number getResult() {
        return this.numbers.get(this.parseTree);
    }
}

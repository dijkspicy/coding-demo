package dijkspicy.demo.antlr4.expr;

import dijkspicy.demo.antlr4.json.JSONException;
import dijkspicy.demo.antlr4.parser.ExprLexer;
import dijkspicy.demo.antlr4.parser.ExprParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * pralgo
 *
 * @author dijkspicy
 * @date 2018/5/13
 */
public class Expression {
    private static final ANTLRErrorListener THROW_OUT_LISTENER = new ConsoleErrorListener() {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            throw new RuntimeException("line " + line + ":" + charPositionInLine + " " + msg, e);
        }
    };
    private static final ANTLRErrorStrategy THROW_OUT_STRATEGY = new DefaultErrorStrategy() {
    };

    static Number calculate(String expression) throws JSONException {
        if (expression == null || expression.trim().isEmpty()) {
            throw new RuntimeException("invalid expression string");
        }
        var lexer = new ExprLexer(CharStreams.fromString(expression));
        var parser = new ExprParser(new CommonTokenStream(lexer));
        parser.setErrorHandler(THROW_OUT_STRATEGY);
        parser.addErrorListener(THROW_OUT_LISTENER);
        var parseTree = parser.expr();
        var listener = new ExprListenerImpl(parseTree);
        ParseTreeWalker.DEFAULT.walk(listener, parseTree);
        return listener.getResult();
    }
}

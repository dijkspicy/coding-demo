package dijkspicy.demo.antlr4.json;

import dijkspicy.demo.antlr4.parser.JSONLexer;
import dijkspicy.demo.antlr4.parser.JSONParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * pralgo
 *
 * @author dijkspicy
 * @date 2018/5/13
 */
public interface JSON {
    JSON NULLABLE = new JSON() {
    };
    ANTLRErrorListener THROW_OUT_LISTENER = new ConsoleErrorListener() {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            throw new JSONException("line " + line + ":" + charPositionInLine + " " + msg, e);
        }
    };
    ANTLRErrorStrategy THROW_OUT_STRATEGY = new DefaultErrorStrategy() {
    };

    static JSON fromString(String json) throws JSONException {
        if (json == null || json.trim().isEmpty()) {
            return JSON.NULLABLE;
        }
        json = json.trim();
        if (!json.startsWith("{") && !json.startsWith("[")) {
            throw new JSONException("json doesn't match first token: '{', '['");
        }
        JSONLexer lexer = new JSONLexer(CharStreams.fromString(json));
        JSONParser parser = new JSONParser(new CommonTokenStream(lexer));
        parser.setErrorHandler(THROW_OUT_STRATEGY);
        parser.addErrorListener(THROW_OUT_LISTENER);
        JSONParser.JsonContext parseTree = parser.json();
        JSONListenerImpl listener = new JSONListenerImpl(parseTree);
        ParseTreeWalker.DEFAULT.walk(listener, parseTree);
        return listener.getJson();
    }
}

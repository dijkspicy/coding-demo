grammar JSON;

@header {
package dijkspicy.demo.antlr4.parser;
}

// parser
json: object | array
    ;
object
    : '{' pair (',' pair)* '}'  #object_normal
    | '{' '}'                   #object_empty
    ;
array
    : '[' value (',' value)* ']'    #array_normal
    | '[' ']'                       #array_empty
    ;
pair: key ':' value
    ;
key: STRING     #key_string
    | ID        #key_id
    ;
value
    : STRING    #value_string
    | NUMBER    #value_number
    | NON_SPACE #value_non_space
    | object    #value_object
    | array     #value_array
    | 'true'    #value_true
    | 'false'   #value_false
    | 'null'    #value_null
    ;

// lexer
ID: [a-zA-Z$_]+[a-zA-Z0-9$_]*
    ;
NON_SPACE: ~[\\/\b\f\n\r\t ]+';'
    ;
STRING : '"' (ESC | ~["\\])* '"'
    ;
fragment ESC: '\\' ([\\/bfnrt] | UNICODE)
    ;
fragment UNICODE: 'u' HEX HEX HEX HEX
    ;
fragment HEX: [0-9a-fA-F]
    ;
NUMBER: '-'? INT '.' INT EXP?
    | '-'? INT EXP
    | '-'? INT
    ;
fragment INT: '0'
    | [1-9][0-9]*
    ;
fragment EXP: [Ee] [+\-]? INT
    ;
WS: [ \t\r\n]+ -> skip
    ;
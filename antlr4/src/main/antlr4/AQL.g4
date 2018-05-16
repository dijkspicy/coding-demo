grammar AQL;

@header {
package dijkspicy.demo.antlr4.parser;
}

// parser
multi_query: query+
    ;
query: source_name pipe*
    ;
source_name: ID (',' ID)*
    ;
pipe: '|' pipe_clause
    ;
pipe_clause: where_clause
    | limit_clause
    | group_clause
    ;
where_clause: 'where' where_atom (('and'|'or'|'not') where_atom)*
    ;
where_atom: ID ('>'|'>='|'<'|'<='|'!='|'~='|'=') (ID|INT)
    ;
limit_clause: 'limit' INT (','INT)? #limit
    | 'limit' INT 'offset' INT      #limit_offset
    ;
group_clause: 'group' ID;

// lexer
ID: [a-zA-Z$_]+INT?
    ;
INT: [0-9]+
    ;
WS: [ \t\r\n]+ -> skip
    ;
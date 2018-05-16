grammar Expr;

@header {
package dijkspicy.demo.antlr4.parser;
}

// parser
expr: SUB NUMBER                #NegativeNumber
    | expr POW expr             #Pow
    | expr (MUL|DIV|MOD) expr   #Mul_Div_Mod
    | expr (ADD|SUB) expr       #Add_Sub
    | NUMBER                    #Number
    | '(' expr ')'              #Parentheses
    ;

// lexer
MUL: '*';
DIV: '/';
ADD: '+';
SUB: '-';
POW: '^';
MOD: '%';

fragment DIGIT: [0-9];
//Fragment修饰的方法，到时候在程序里不会生成 DIGIT()。
//而下面的NUMBER没有fragment, 则可以通过 NUMBER() 来访问。

fragment S_QUOTE: '\'';
fragment QUOTE: '"';

NUMBER: DIGIT+('.'DIGIT+)? ; //数字类型，包括浮点和整型

// 两种模式，一种是单引号，一种是双引号
STRING: S_QUOTE (~'\'')* S_QUOTE //单引号字符串
    | QUOTE ('\\"'|~'"')* QUOTE //双引号字符串
    ;

WS: [ \r\t\n]+ -> skip; // Skip all the white spaces.

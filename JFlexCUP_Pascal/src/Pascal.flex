import java_cup.runtime.*;

%%

%class LexicalAnalysisCalculator
%column
%line
%cup

%{
	   
	   private Symbol symbol(int type) {
		   return new Symbol(type, yyline, yycolumn);
   	   }
   	
 	   private Symbol symbol(int type, Object val) {
		   return new Symbol(type, yyline, yycolumn, val);
   	   }
%}

digit = [0-9]
letter = [A-Za-z]

A = [aA]
B = [bB]
C = [cC]
D = [dD]
E = [eE]
F = [fF]
G = [gG]
H = [hH]
I = [iI]
J = [jJ]
K = [kK]
L = [lL]
M = [mM]
N = [nN]
O = [oO]
P = [pP]
Q = [qQ]
R = [rR]
S = [sS]
T = [tT]
U = [uU]
V = [vV]
W = [wW]
X = [xX]
Y = [yY]
Z = [zZ]
NQUOTE = [^']

leftbrace          = \{
rightbrace         = \}
nonrightbrace      = [^}]
comment_body       = {nonrightbrace}*
comment            = {leftbrace}{comment_body}{rightbrace}

keyword_external   = {E}{X}{T}{E}{R}{N} | {E}{X}{T}{E}{R}{N}{A}{L} 

string_character   = '({NQUOTE}|'')+'

lineTerminator = \r|\n|\r\ns
whitespace     = {lineTerminator} | [ \t\f]


%%
/*RESERVED WORDS*/

"program" { return symbol(sym.PROGRAM, new Integer(yyline + 1)); }
"label" { return symbol(sym.LABEL, new Integer(yyline + 1)); }
"const" { return symbol(sym.CONST, new Integer(yyline + 1)); }
"type" { return symbol(sym.TYPE, new Integer(yyline + 1)); }
"var" { return symbol(sym.VAR, new Integer(yyline + 1)); }
"forward" { return symbol(sym.FORWARD, new Integer(yyline + 1)); }
"begin" { return symbol(sym.BEGIN, new Integer(yyline + 1)); }
"end" { return symbol(sym.END, new Integer(yyline + 1)); }
"procedure" { return symbol(sym.PROCEDURE, new Integer(yyline + 1)); }
"function" { return symbol(sym.FUNCTION, new Integer(yyline + 1)); }
"packed" { return symbol(sym.PACKED, new Integer(yyline + 1)); }
"array" { return symbol(sym.ARRAY, new Integer(yyline + 1)); }
"of" { return symbol(sym.OF, new Integer(yyline + 1)); }
"goto" { return symbol(sym.GOTO, new Integer(yyline + 1)); }
"while" { return symbol(sym.WHILE, new Integer(yyline + 1)); }
"do" { return symbol(sym.DO, new Integer(yyline + 1)); }
"repeat" { return symbol(sym.REPEAT, new Integer(yyline + 1)); }
"until" { return symbol(sym.UNTIL, new Integer(yyline + 1)); }
"for" { return symbol(sym.FOR, new Integer(yyline + 1)); }
"to" { return symbol(sym.TO, new Integer(yyline + 1)); }
"downto" { return symbol(sym.DOWNTO, new Integer(yyline + 1)); }
"if" { return symbol(sym.IF, new Integer(yyline + 1)); }
"then" { return symbol(sym.THEN, new Integer(yyline + 1)); }
"else" { return symbol(sym.ELSE, new Integer(yyline + 1)); }
"case" { return symbol(sym.CASE, new Integer(yyline + 1)); }
"with" { return symbol(sym.WITH, new Integer(yyline + 1)); }
"nil" { return symbol(sym.NIL, new Integer(yyline + 1)); }
"not" { return symbol(sym.NOT, new Integer(yyline + 1)); }
"div" { return symbol(sym.DIVEXTENSION, new Integer(yyline + 1)); }
"or" { return symbol(sym.OR, new Integer(yyline + 1)); }
"and" { return symbol(sym.AND, new Integer(yyline + 1)); }
"record" { return symbol(sym.RECORD, new Integer(yyline + 1)); }
"set" { return symbol(sym.SET, new Integer(yyline + 1)); }
"file" { return symbol(sym.FILE, new Integer(yyline + 1)); }


/*OPERATORS*/

"-" { return symbol(sym.MINUS, new Integer(yyline + 1)); }
"+" { return symbol(sym.PLUS, new Integer(yyline + 1)); }
"/" { return symbol(sym.DIV, new Integer(yyline + 1)); }
"*" { return symbol(sym.TIMES, new Integer(yyline + 1)); }
"mod" { return symbol(sym.MOD, new Integer(yyline + 1)); }
":" { return symbol(sym.COLON, new Integer(yyline + 1)); }
".." { return symbol(sym.DOUBLEDOT, new Integer(yyline + 1)); }
"=" { return symbol(sym.EQUALS, new Integer(yyline + 1)); }
"<>" { return symbol(sym.DIFF, new Integer(yyline + 1)); }
"<" { return symbol(sym.LESSTHEN, new Integer(yyline + 1)); }
"<=" { return symbol(sym.LESSTHENEQUALS, new Integer(yyline + 1)); }
">" { return symbol(sym.GREATERTHEN, new Integer(yyline + 1)); }
">=" { return symbol(sym.GREATERTHENEQUALS, new Integer(yyline + 1)); }
"in" { return symbol(sym.IN, new Integer(yyline + 1)); }
"^" { return symbol(sym.CIRCUNFLEX, new Integer(yyline + 1)); }
"'" { return symbol(sym.SINGLEQUOTE, new Integer(yyline + 1)); }
\"	{return symbol(sym.DOUBLEQUOTE, new Integer(yyline + 1));}


/*SEPARATORS*/

"(" { return symbol(sym.LPARENT); }
")" { return symbol(sym.RPARENT); }
"[" { return symbol(sym.LBRACKET); }
"]" { return symbol(sym.RBRACKET); }
";" { return symbol(sym.SEMICOLON); }
"," { return symbol(sym.COMMA); }
"." { return symbol(sym.DOT); }


/*ASSIGMENT*/
":=" { return symbol(sym.ASSIGMENT); }

'({NQUOTE}|'')*' {return symbol(sym.STRINGCHARACTER);}
/*string_character* {return symbol(sym.STRINGCHARACTERCLOSURE);}*/


{E} { return symbol(sym.SCALEFACTOR); }

{keyword_external}	{return symbol(sym.EXTERNAL);}

{digit}+ { return symbol(sym.DIGITSEQUENCE, new String(yytext())); }

letter { return symbol(sym.LETTER); }

{letter} ({letter}|{digit})* { return symbol(sym.IDENTIFIER, new String(yytext())); }

/* Comentario */
{comment} { System.out.println("Comment: " + yytext()); }
	
/* Espacos */
{whitespace} { /*ignore*/ }


<<EOF>> { return symbol(sym.EOF); }
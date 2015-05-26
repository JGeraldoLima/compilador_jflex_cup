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

letter          = [A-Za-z]
digit           = [0-9]
identifier      = {letter}({letter}|{digit})*
identifier_list      = identifier [COLON identifier]*


%%
/*Aqui estao definidos os possiveis tokens que podem ser encontrados numa calculadora simples*/

"program" { return symbol(sym.PROGRAM); }
"Begin" { return symbol(sym.BEGIN); }
"End" { return symbol(sym.END); }

"(" { return symbol(sym.LPAREN); }
")" { return symbol(sym.RPAREN); }
"-" { return symbol(sym.MINUS); }
"+" { return symbol(sym.PLUS); }
"/" { return symbol(sym.DIV); }
"*" { return symbol(sym.TIMES); }
":" { return symbol(sym.COLON); }
";" { return symbol(sym.SEMICOLON); }
"," { return symbol(sym.COMMA); }
"." { return symbol(sym.DOT); }
digit { return symbol(sym.DIGIT); }
[0-9]+ { return symbol(sym.NUMBER); }
[ \r\n\t\f] { /*nao faz nada*/ }
[A-Za-z] { return symbol(sym.LETTER); }
[A-Za-z]* { return symbol(sym.STRING); }

{identifier} {return symbol(sym.IDENTIFIER); }
{identifier_list} {return symbol(sym.IDENTIFIERLIST); }

<<EOF>> { return symbol(sym.EOF); }
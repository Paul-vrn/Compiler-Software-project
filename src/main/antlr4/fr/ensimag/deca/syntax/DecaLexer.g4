lexer grammar DecaLexer;

options {
   language=Java;
   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;
}

@members {
}

// Deca lexer rules.

//IDENTIFICATEURS
fragment LETTER : 'a'..'z' | 'A'..'Z';
fragment DIGIT : '0'..'9';
OBRACE : '{';
CBRACE : '}';
COMMA : ',';
DOT : '.';
SEMI : ';';
COLON : ':';
OPARENT : '(';
CPARENT : ')';
//les mots réservés ne sont pas des identificateurs
ASM : 'asm';
CLASS : 'class';
EXTENDS : 'extends';
ELSE : 'else';
FALSE : 'false';
IF : 'if';
INSTANCEOF : 'instanceof';
NEW : 'new';
NULL : 'null';
READINT : 'readInt';
READFLOAT : 'readFloat';
PRINT : 'print';
PRINTLN : 'println';
PRINTLNX : 'printlnx';
PRINTX : 'printx';
PROTECTED : 'protected';
RETURN : 'return';
THIS : 'this';
TRUE : 'true';
WHILE : 'while';

// On met IDENT après RESERVED_WORDS pour que RESERVED_WORDS soit prioritaire
IDENT : (LETTER | '$' | '_')(LETTER | DIGIT | '$' | '_')*;


//SYMBOLES SPÉCIAUX
EQUALS : '=';
EXCLAM : '!';
AND : '&&';
OR : '||';
EQEQ : '==';
NEQ : '!=';
LEQ : '<=';
GEQ : '>=';
GT : '>';
LT : '<';

//OPERATEURS
PLUS : '+';
MINUS : '-';
TIMES : '*';
SLASH : '/';
PERCENT : '%';
//les symboles spéciaux en question

//LITTERAUX ENTIERS
fragment POSITIVE_DIGIT : '1'..'9';
INT : '0' | POSITIVE_DIGIT DIGIT* {
    if (Integer.parseInt(getText()) > 2e31-1) {
        throw new IllegalArgumentException("Integer overflow");
    }
};
// TODO : changer l'erreur en erreur de compilation
//erreur de compilation levée si littéral entier pas codable comme un entier signé positif sur 32 bits

//LITTERAUX FLOTTANTS

fragment NUM : DIGIT+;
fragment SIGN : '+' | '-' | ;
fragment EXP : ('E' | 'e') SIGN NUM;
fragment DEC : NUM '.' NUM;
fragment FLOATDEC : (DEC | DEC EXP)('F' | 'f' | );
fragment DIGITHEX : '0'..'9' | 'A'..'F' | 'a'..'f';
fragment NUMHEX : DIGITHEX+;
fragment FLOATHEX : ('0x' | '0X') NUMHEX '.' NUMHEX ('P' | 'p') SIGN NUM ('F' | 'f' |);
FLOAT : FLOATDEC | FLOATHEX;

//Les littéraux flottants sont convertis en arrondissant si besoin au flottant IEEE-754 simple précision
//le plus proche. Une erreur de compilation est levée si un littéral est trop grand et que l’arrondi se fait
//vers l’infini, ou bien qu’un littéral non nul est trop petit et que l’arrondi se fait vers zéro.

//Le suffixe f est autorisé mais ignoré, pour permettre une meilleure compatibilité de Deca avec Java.

//CHAÎNE DE CARACTÈRES

//STRING_CAR est l'ensemble de tous les caractères sauf ' " ', '\' et de EOL (fin de ligne)
fragment STRING_CAR: ~('"' | '\\' | '\n')+;
fragment EOL : '\n';
STRING : '"' (STRING_CAR | '\\"' | '\\\\')* '"';
MULTI_LINE_STRING : '"' (STRING_CAR | EOL | '\\"' | '\\\\')* '"';

// Gestion de l'inclusion de fichiers
fragment FILENAME : (LETTER | DIGIT | '_' | '-' | '.')+;
DOINCLUDE: '#include' (' ')* '"' FILENAME '"' {doInclude(getText());};

// COMMENTAIRES
fragment COMMENT_OPENING : '/*';
fragment COMMENT_CLOSING : '*/';
COMMENT : '//' (~('\n')*) {skip();};
BLOCK_COMMENT : COMMENT_OPENING .*? COMMENT_CLOSING {skip();};

// ESPACES
SEPARATEUR : (' ' | '\t' | '\r' | '\n') {skip();};

DEFAULT: . ;
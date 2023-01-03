






























































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
//DUMMY_TOKEN: .; // A FAIRE : Règle bidon qui reconnait tous les caractères.
                // A FAIRE : Il faut la supprimer et la remplacer par les vraies règles.


//IDENTIFICATEURS
LETTER : 'a'..'z' + 'A'..'Z';
DIGIT : '0'..'9';
IDENT : (LETTER + '$' + '_')(LETTER + DIGIT + '$' + '_')*;

//les mots réservés ne sont pas des identificateurs
RESERVED_WORDS : 'asm' + 'class' + 'extends' + 'else' + 'false' +
'if' + 'instanceof' + 'new' + 'null' + 'readInt' + 'readFloat' + 'print'
+  'println' + 'printlnx' + 'printx' + 'protected' + 'return' + 'this'
+  'true' + 'while';

//SYMBOLES SPÉCIAUX
LESSER_THAN : '<';
GREATER_THAN : '>';
ASSIGN : '=';
PLUS : '+';
MINUS : '-';

AND : '&&';


//les symboles spéciaux en question

//LITTERAUX ENTIERS
POSITIVE_DIGIT : '1'..'9';
INT_ANY_VALUE : '0' + POSITIVE_DIGIT DIGIT*;
INT : INT_ANY_VALUE {
    if(INT_ANY_VALUE.int > 2147483647){
    throw new IllegalArgumentException("Valeur d'un entier ne peut pas excéder 2³¹-1");
    }
};

//erreur de compilation levée si littéral entier pas codable comme un entier signé positif sur 32 bits

//LITTERAUX FLOTTANTS

NUM : DIGIT+;
SIGN : '+' + '-' + ;
EXP : ('E' + 'e') SIGN NUM;
DEC : NUM '.' NUM;
FLOATDEC : (DEC + DEC EXP)('F' + 'f' + );
DIGITHEX : '0'..'9' + 'A'..'F' + 'a'..'f';
NUMHEX : DIGITHEX+;
FLOATHEX : ('0x' + '0X')NUMHEX '.' NUMHEX ('P' + 'p') SIGN NUM ('F' + 'f' +);
FLOAT : FLOATDEC + FLOATHEX;

//Les littéraux flottants sont convertis en arrondissant si besoin au flottant IEEE-754 simple précision
//le plus proche. Une erreur de compilation est levée si un littéral est trop grand et que l’arrondi se fait
//vers l’infini, ou bien qu’un littéral non nul est trop petit et que l’arrondi se fait vers zéro.

//Le suffixe f est autorisé mais ignoré, pour permettre une meilleure compatibilité de Deca avec Java.


//CHAÎNE DE CARACTÈRES

//STRING_CAR est l'ensemble de tous les caractères sauf ' " ', '\' et de EOL (fin de ligne)

//STRING : '"'(STRING_CAR + '\\"' + '\\\\')* '"';
//MULTI_LINE_STRING : '"' (STRING_CAR + EOL + '\\"' + '\\\\')* '"';
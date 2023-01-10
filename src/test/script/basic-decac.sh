#! /bin/bash

# Test de l'interface en ligne de commande de decac.
# On ne met ici qu'un test trivial, a vous d'en ecrire de meilleurs.

DECAC_HOME=$(cd "$(dirname "$0")"/../../../ && pwd)
TEST_FILE="$DECAC_HOME"/src/test/deca/syntax/valid/provided/hello.deca
PATH=./src/main/bin:"$PATH"

decac_without_args=$(decac)

if [ "$?" -ne 0 ]; then
    echo "ERREUR: decac a termine avec un status different de zero."
    exit 1
fi

if [ "$decac_without_args" = "" ]; then
    echo "ERREUR: decac n'a produit aucune sortie"
    exit 1
fi

if echo "$decac_without_args" | grep -i -e "erreur" -e "error"; then
    echo "ERREUR: La sortie de decac contient erreur ou error"
    exit 1
fi

echo "Pas de probleme detecte avec decac sans arguments."

decac_moins_b=$(decac -b)

if [ "$?" -ne 0 ]; then
    echo "ERREUR: decac -b a termine avec un status different de zero."
    exit 1
fi

if [ "$decac_moins_b" = "" ]; then
    echo "ERREUR: decac -b n'a produit aucune sortie"
    exit 1
fi

if echo "$decac_moins_b" | grep -i -e "erreur" -e "error"; then
    echo "ERREUR: La sortie de decac -b contient erreur ou error"
    exit 1
fi

echo "Pas de probleme detecte avec decac -b."

DECAC_HOME=$(cd "$(dirname "$0")"/../../../ && pwd)
TEST_FILE="$DECAC_HOME"/src/test/deca/syntax/valid/provided/hello.deca
ORACLE_FILE="$DECAC_HOME"/src/test/deca/syntax/valid/hello_decompiled_oracle.txt
decac_moins_p=$(decac -p "$TEST_FILE")


if [ "$?" -ne 0 ]; then
    echo "ERREUR: decac -p a termine avec un status different de zero."
    exit 1
fi

if [ "$decac_moins_p" = "" ]; then
    echo "ERREUR: decac -p n'a produit aucune sortie"
    exit 1
fi

if echo "$decac_moins_p" | grep -i -e "erreur" -e "error"; then
    echo "ERREUR: La sortie de decac -p contient erreur ou error"
    exit 1
fi

diff_output_moins_p=$(diff  "$ORACLE_FILE" <(echo "$decac_moins_p"))

if [ "$diff_output_moins_p" != "" ]; then
    echo "ERREUR: La sortie de decac -p est differente de hello_decompiled_oracle.txt"
    exit 1
fi

echo "Pas de probleme detecte avec decac -p."

decac_moins_v=$(decac -v "$TEST_FILE")

if [ "$decac_moins_v" != "" ]; then
    echo "ERREUR: decac -v a produit une sortie à partir d'un programme correct"
    exit 1
fi

WRONG_TEST_FILE="$DECAC_HOME"/src/test/deca/syntax/invalid/provided/chaine_incomplete.deca

decac_moins_v=$(decac -v "$WRONG_TEST_FILE" 2>&1)

if [ "$decac_moins_v" = "" ]; then
    echo "ERREUR: decac -v n'a produit aucune sortie à partir d'un programme faux"
    exit 1
fi

echo "Pas de probleme detecte avec decac -v. pas sûr de l'implémentation par contre"

ARITH_EXPRESS_TEST="$DECAC_HOME"/src/test/deca/codegen/valid/test_expression_arith.deca

decac_moins_r_4=$(decac -r 4 "$ARITH_EXPRESS_TEST")
decac_moins_r=$(decac "$ARITH_EXPRESS_TEST")

if [ "$decac_moins_r_4" != "" ]; then
    echo "ERREUR: decac -r a produit une sortie à partir d'un programme correct"
    exit 1
fi

echo "Pas de probleme detecte avec decac -r.(besoin de plus de test)"




echo "Reste des options pas encore implémenter."



# ... et ainsi de suite.





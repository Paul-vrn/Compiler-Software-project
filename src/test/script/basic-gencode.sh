#! /bin/sh

# Auteur : gl21
# Version initiale : 01/01/2023

# Set the color variable
green='\033[0;92m'
# Clear the color after that
clear='\033[0m'

red='\033[0;31m'

cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:./src/main/bin:"$PATH"

# On récupère la liste de tous les fichiers .deca dans le dossier valid
for fichier in ./src/test/deca/codegen/valid/*.deca
do
  # On enlève l'extension .deca du nom du fichier
  nom_fichier=${fichier%.*}
  filename=$(basename "$nom_fichier")


  # On supprime le fichier .ass correspondant (s'il existe)
  rm -f "$nom_fichier.ass" 2>/dev/null

  # On compile le fichier .deca
  decac "$fichier" || exit 1
  if [ ! -f "$nom_fichier.ass" ]; then
      echo "Fichier $nom_fichier.ass non généré."
      exit 1
  fi

  # On récupère le résultat de l'exécution du fichier .ass
  resultat=$(ima "$nom_fichier.ass") || exit 1

  echo "$resultat"

  # On supprime le fichier .ass
  rm -f "$nom_fichier.ass"

  nom_fichier_oracle="$nom_fichier"_oracle.txt
  oracle="_oracle.txt"


  # On récupère le résultat attendu pour ce fichier (à partir du fichier de même nom dans le dossier oracles)
  attendu=$(cat "./src/test/deca/codegen/valid/oracles/$filename$oracle")

  if [ "$resultat" = "$attendu" ]; then
      echo "${green}Tout va bien pour $filename${clear}"
  else
      echo "${red}Résultat inattendu de ima pour $filename:${clear}"
      echo "${red}$resultat${clear}"
      exit 1
  fi
done

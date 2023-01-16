#! /bin/sh

# Auteur : gl21
# Version initiale : 01/01/2023

# Set the color variable
green='\033[0;92m'
# Clear the color after that
clear='\033[0m'

yellow='\033[0;33m'

cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:./src/main/bin:"$PATH"

for fichier in ./src/test/deca/codegen/valid/*.deca
do
  nom_fichier=${fichier%.*}
  filename=$(basename "$nom_fichier")
  if [ "$fichier" == "./src/test/deca/codegen/valid/to_be_included.deca" ]
  then
    continue
  fi
  rm -f "$nom_fichier.ass" 2>/dev/null

  decac "$fichier"
  if [ $? -ne 0 ]; then
      echo "${red} Error : decac failed for $filename"
      continue
  fi

  if [ ! -f "$nom_fichier.ass" ]; then
      echo "Fichier $nom_fichier.ass non généré."
      continue
  fi

  resultat=$("../global/bin/ima" "$nom_fichier.ass")
  if [ $? -ne 0 ]; then
      echo "${yellow} Error : ima failed for $filename${clear}"
      continue
  fi

  rm -f "$nom_fichier.ass"

  nom_fichier_oracle="$nom_fichier"_oracle.txt
  oracle="_oracle.txt"

  attendu=$(cat "./src/test/deca/codegen/valid/$filename$oracle")

  if [ "$resultat" = "$attendu" ]; then
      echo "${green}Tout va bien pour $filename${clear}"
  else
      echo "${yellow}Résultat inattendu de ima pour $filename:${clear}"
      echo "${yellow}$resultat${clear}"
  fi
done


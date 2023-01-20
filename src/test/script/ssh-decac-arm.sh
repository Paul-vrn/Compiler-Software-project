#!/bin/bash
_remote=192.168.192.242
_user=pi

echo 'Warning: ssh-key must be added'

ping -c 1 $_remote > /dev/null 2>&1

if [ $? -ne 0 ]; then
    echo "Skip: $_remote is unreachable, arm tests are skipped"
    exit 0
fi

cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:./src/main/bin:"$PATH"

for fichier in ./src/test/deca/codegen/arm/*.deca
do
  nom_fichier=${fichier%.*}
  filename=$(basename "$nom_fichier")

  rm -f "$nom_fichier.s" 2>/dev/null

  decac -ARM "$fichier"
  if [ $? -ne 0 ]; then
      echo "Error : decac failed for $filename"
      continue
  fi

  if [ ! -f "$nom_fichier.s" ]; then
      echo "Fichier $nom_fichier.s non généré."
      continue
  fi

  scp "$nom_fichier.s" $_user@$_remote:~/deca/

  resultat=$(ssh -T $_user@$_remote "gcc -mcpu=cortex-a53 ~/deca/$filename.s && ~/a.out")

  nom_fichier_oracle="$nom_fichier"_oracle.txt
  oracle="_oracle.txt"

  attendu=$(cat "./src/test/deca/codegen/arm/$filename$oracle")

  if [ "$resultat" = "$attendu" ]; then
      echo "Tout va bien pour $filename"
  else
      echo "Résultat inattendu de ima pour $filename"
      echo "$resultat"
  fi
done


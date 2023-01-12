#!/bin/bash

for i in ./*.deca
do
	echo $i | sed 's/.deca/_oracle\.txt/g' | sed 's/.\///g' | xargs touch

done

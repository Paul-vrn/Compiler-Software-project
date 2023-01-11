(0.1) **identifier**
Raison: Opération partielle -- $env\_exp(name)$
Message: "Identificateur non déclaré"

(0.2) **type**
Raison: Condition -- $(\_, type) \triangleq env\_types(name)$
Message: "Identificateur de type non déclaré"


(3.5) **decl_class**
Raison: Condition -- $(\underline{class}(\_, env\_exp), \_) \triangleq env\_types(class)$
Message: "Identificateur de classe non déclaré"

(3.7) **decl_field**
Raison: 

(3.12) **list_decl_param**
Raison: Opération partielle --  $env\_exp_r \oplus env\_expr$
Message: "Nom de paramètre déjà déclaré"

(3.13) **decl_param** 
Raison: Condition -- $name \rightarrow (\underline{param}, type)$
Message: "Identificateur de paramètre attendu"

(3.17) **decl_var**
Raison: Condition -- $name \rightarrow (\underline{var}, type)$
Message: "Identificateur de variable attendu"

Raison: Opération partielle
Message: "Nom de variable déjà déclaré"

Raison: Condition -- $type \neq \underline{void}$
Message: "Une variable ne peut être de type void"

(3.24) **inst**
Raison: Condition -- $return \neq \underline{void}$
Message: "Impossible de retourner une valeur lorsque le type de retour est void"

(3.28) **rvalue**
Raison: Condition -- $assign\_compatible(env\_types, type_1, type_2)$
Message: "\$type_1 et \$type_2 incompatibles pour l'affectation"

(3.29) **condition**
Raison: Filtrage d'un attribut synthétisé
Message: "Expression booléenne attendue"

(3.31) **exp_print**
Raison: Condition
Message: "Expression de type int, float ou string attendue"

(3.32) **expr $\rightarrow$ op_bin**
Raison: Opération partielle -- $type\_binary\_op(op, type_1, type_2)$
Message: "L'opération binaire \$op est non définie pour les types \$type_1 et \$type_2"

(3.37) **expr $\rightarrow$ op_un**
Raison: Opération partielle -- $type\_unary\_op(op, type_1)$
Message: "L'opération unaire \$op est non définie pour le type \$type_1"

(3.39) **expr $\rightarrow \underline{Cast}$**
Raison: Condition -- $cast\_compatible(env\_types, type_2, type)$
Message: "Impossible de convertir \$type_2 vers \$type"

(3.40) **expr $\rightarrow \underline{InstanceOf}$**
Raison: Opération partielle -- $type\_instanceof\_op(type_1, type_2)$
Message: "Opération instanceof indéfinie pour les types \$type_1 et \$type_2"

*2 ajouts persos pour plus de clarté*
Raison: Condition -- $type_1 = \underline{type\_class}(\_)$ ou $type_1=null$
Message: "Un type de class ou null est attendu à gauche de instanceof"

Raison: Condition -- $type_2 = \underline{type\_class}(\_)$
Message: "Un type de class est attendu à droite de instanceof"

(3.42) **expr $\rightarrow \underline{New}$**
Raison: Condition -- $type = \underline{type\_class(_)}$
Message: "\$type n'est pas un type de classe, donc incompatible avec New."

(3.43) **expr $\rightarrow \underline{This}$**
Raison: Condition -- $class \neq 0$
Message: "Mot-clef this invalide en dehors d'une classe"

(3.65) **lvalue $\rightarrow \underline{Selection}$**
Raison: Filtrage attribut synthétisé -- **expr** synthétise $\underline{type\_class}(class_2)$
Message: erreur interne ? "Expression attendue de type classe"

Raison: Filtrage attribut synthétise -- **field_ident** synthétise $\underline{public}$
Message: "Visibilité de champ public attendue"

Raison: Condition -- $(\underline{class}(\_, env\_exp_2), \_) \triangleq env\_types(class_2)$
Message: "Identificateur de classe attendu"

Raison: Opération partielle -- $env\_types(class_2)$
Message: "Identificateur de classe non déclaré"

(3.66) **lvalue $\rightarrow \underline{Selection}$**
Raison: Filtrage attribut synthétisé -- **expr** synthétise $\underline{type\_class}(class_2)$
Message: erreur interne ? "Expression attendue de type classe"

Raison: Filtrage attribut synthétisé -- **field_ident** synthétise $\underline{protected}$
Message: "Visibilité de champ protected attendue"

Raison: Opération partielle -- $env\_types(class_2)$
Message: "Identificateur de classe non déclaré"

Raison: Condition -- $(\underline{class}(\_, env\_exp_2), \_) \triangleq env\_types(class_2)$
Message: "Identificateur de classe attendu"

Raison: Condition -- $subtype(env\_types, \underline{type\_class}(class_2), \underline{type\_class}(class))$ 
Message: "\$class_2 n'est pas un sous-type de \$class"

Raison: Condition -- $subtype(env\_types, \underline{type\_class}(class), \underline{type\_class}(class\_field))$ 
Message: "\$class n'est pas un sous-type de \$class_field"
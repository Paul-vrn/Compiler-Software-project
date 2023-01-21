package fr.ensimag.deca.context;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.syntax.AbstractDecaLexer;
import fr.ensimag.deca.syntax.DecaLexer;
import fr.ensimag.deca.syntax.DecaParser;
import fr.ensimag.deca.tree.AbstractProgram;
import fr.ensimag.deca.tree.LocationException;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class ContTest {
    @Test
    void test1() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/arith_modulo_op_01.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/arith_modulo_op_01_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test2() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/arith_modulo_op_02.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/arith_modulo_op_02_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test3() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/arith_op_type_mismatch_01.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/arith_op_type_mismatch_01_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test4() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/arith_op_type_mismatch_02.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/arith_op_type_mismatch_02_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test5() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/arith_op_type_mismatch_03.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/arith_op_type_mismatch_03_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test6() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/assign_type_problem_01.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/assign_type_problem_01_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test7() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/assign_type_problem_02.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/assign_type_problem_02_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test8() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/bool_op_mismatch.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/bool_op_mismatch_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test9() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/compare_op_mismatch_01.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/compare_op_mismatch_01_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test10() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/compare_op_mismatch_02.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/compare_op_mismatch_02_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test11() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/compare_op_mismatch_03.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/compare_op_mismatch_03_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test12() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/ifelse_wrong_argument_01.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/ifelse_wrong_argument_01_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test13() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/ifelse_wrong_argument_02.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/ifelse_wrong_argument_02_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test14() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/init_type_01.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/init_type_01_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test15() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/init_type_02.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/init_type_02_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test16() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/init_type_03.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/init_type_03_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test17() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/init_type_04.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/init_type_04_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test18() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/missing_type_declaration.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/missing_type_declaration_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test19() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/op_not_mismatch.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/op_not_mismatch_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test20() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/op_unaryMinus_mismatch.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/op_unaryMinus_mismatch_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test21() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/print_argument_type.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/print_argument_type_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test22() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/type_void.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/type_void_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test23() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/unknown_type.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/unknown_type_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test24() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/var_already_defined.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/var_already_defined_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test25() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/init_undef_value.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/init_undef_value_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test26() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/assign_undef_value.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/assign_undef_value_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test27() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/arith_undef_operand.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/arith_undef_operand_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test28() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/while_wrong_argument.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/while_wrong_argument_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test29() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/assign_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/assign_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test30() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/assign_mult_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/assign_mult_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test31() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/bool_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/bool_pars_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test32() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/cond_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/cond_pars_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test33() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/condition_modulo_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/condition_modulo_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test34() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/exemple_sans_objet.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/exemple_sans_objet_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test35() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/expr_alone_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/expr_alone_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test36() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/for_nested_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/for_nested_pars_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test37() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/hw_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/hw_pars_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test38() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/if_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/if_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test39() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/if_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/if_pars_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test40() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/init_cont_val_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/init_cont_val_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test41() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/init_cont_val_02.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/init_cont_val_02_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test42() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/intint_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/intint_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test43() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/minmin_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/minmin_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test44() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/mod_cont_val_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/mod_cont_val_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test45() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/name_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/name_pars_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test46() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/negative_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/negative_pars_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test47() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/negative_pars_02.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/negative_pars_02_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test48() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/not_cont_val_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/not_cont_val_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test49() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/op_arith_cont_val_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/op_arith_cont_val_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test50() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/op_comp_valid_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/op_comp_valid_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test51() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/print_assign_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/print_assign_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test52() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/print_nest_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/print_nest_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test53() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/read_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/read_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test54() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/while_pars_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/while_pars_01_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test55() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/cast_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/cast_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test56() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/cast_cont_02.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/cast_cont_02_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test57() throws IOException {
        String[] args = {"src/test/deca/context/valid/sans_objet/cast_cont_03.deca"};
        String file2 = "src/test/deca/context/valid/sans_objet/cast_cont_03_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test58() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/cast_inva_cont_01.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/cast_inva_cont_01_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test59() throws IOException {
        String[] args = {"src/test/deca/context/invalid/sans_objet/cast_inva_cont_02.deca"};
        String file2 = "src/test/deca/context/invalid/sans_objet/cast_inva_cont_02_oracle.txt";
        generalTestInvalid(args, file2);
    }

    /* ----------------------------------------------------------------------------------------- */

    @Test
    void test60() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/cast_type_invalid.deca"};
        String file2 = "src/test/deca/context/invalid/objet/cast_type_invalid_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test61() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/classname_already_used.deca"};
        String file2 = "src/test/deca/context/invalid/objet/classname_already_used_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test62() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/compare_type_mismatch.deca"};
        String file2 = "src/test/deca/context/invalid/objet/compare_type_mismatch_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test63() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/extends_inexistant_class.deca"};
        String file2 = "src/test/deca/context/invalid/objet/extends_inexistant_class_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test64() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/field_already_defined1.deca"};
        String file2 = "src/test/deca/context/invalid/objet/field_already_defined1_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test65() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/field_already_defined2.deca"};
        String file2 = "src/test/deca/context/invalid/objet/field_already_defined2_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test66() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/instanceof.deca"};
        String file2 = "src/test/deca/context/invalid/objet/instanceof_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test67() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/method_already_defined.deca"};
        String file2 = "src/test/deca/context/invalid/objet/method_already_defined_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test68() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/method_parameter_void.deca"};
        String file2 = "src/test/deca/context/invalid/objet/method_parameter_void_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test69() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/methodCall_mustbe_method.deca"};
        String file2 = "src/test/deca/context/invalid/objet/methodCall_mustbe_method_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test70() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/must_be_field.deca"};
        String file2 = "src/test/deca/context/invalid/objet/must_be_field_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test71() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/name_conflict_superclass.deca"};
        String file2 = "src/test/deca/context/invalid/objet/name_conflict_superclass_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test72() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/new_problem.deca"};
        String file2 = "src/test/deca/context/invalid/objet/new_problem_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test73() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/param_already_defined.deca"};
        String file2 = "src/test/deca/context/invalid/objet/param_already_defined_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test74() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/protected_field1.deca"};
        String file2 = "src/test/deca/context/invalid/objet/protected_field1_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test75() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/return_class.deca"};
        String file2 = "src/test/deca/context/invalid/objet/return_class_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test76() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/returnType_incompatible.deca"};
        String file2 = "src/test/deca/context/invalid/objet/returnType_incompatible_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test77() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/selection_not_class_instance.deca"};
        String file2 = "src/test/deca/context/invalid/objet/selection_not_class_instance_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test78() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/subtype_condition_returnType.deca"};
        String file2 = "src/test/deca/context/invalid/objet/subtype_condition_returnType_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test79() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/subtype_condition_signature.deca"};
        String file2 = "src/test/deca/context/invalid/objet/subtype_condition_signature_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test80() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/subtype_problem1.deca"};
        String file2 = "src/test/deca/context/invalid/objet/subtype_problem1_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test81() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/type_void_field.deca"};
        String file2 = "src/test/deca/context/invalid/objet/type_void_field_oracle.txt";
        generalTestInvalid(args, file2);
    }
    @Test
    void test82() throws IOException {
        String[] args = {"src/test/deca/context/invalid/objet/wrong_return_type.deca"};
        String file2 = "src/test/deca/context/invalid/objet/wrong_return_type_oracle.txt";
        generalTestInvalid(args, file2);
    }

    @Test
    void test83() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/field_class_01.deca"};
        String file2 = "src/test/deca/context/valid/objet/field_class_01_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test84() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/field_class_02.deca"};
        String file2 = "src/test/deca/context/valid/objet/field_class_02_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test85() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/get_01.deca"};
        String file2 = "src/test/deca/context/valid/objet/get_01_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test86() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/ident_class_01.deca"};
        String file2 = "src/test/deca/context/valid/objet/ident_class_01_oracle.txt";
        generalTestValid(args, file2);
    }

    @Test
    void test87() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/instanceof_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/objet/instanceof_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test88() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/instanceof_cont_02.deca"};
        String file2 = "src/test/deca/context/valid/objet/instanceof_cont_02_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test89() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/instanceof_cont_03.deca"};
        String file2 = "src/test/deca/context/valid/objet/instanceof_cont_03_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test90() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/method_ovrlap_class_01.deca"};
        String file2 = "src/test/deca/context/valid/objet/method_ovrlap_class_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test91() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/nested_method_param_01.deca"};
        String file2 = "src/test/deca/context/valid/objet/nested_method_param_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test92() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/nested_select_class_01.deca"};
        String file2 = "src/test/deca/context/valid/objet/nested_select_class_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test93() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/new_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/objet/new_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test94() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/object_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/objet/object_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test95() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/op_comp_valid_class_01.deca"};
        String file2 = "src/test/deca/context/valid/objet/op_comp_valid_class_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test96() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/param_class_01.deca"};
        String file2 = "src/test/deca/context/valid/objet/param_class_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test97() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/param_class_02.deca"};
        String file2 = "src/test/deca/context/valid/objet/param_class_02_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test98() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/protected_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/objet/protected_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test99() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/return_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/objet/return_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test100() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/subclass_cont_01.deca"};
        String file2 = "src/test/deca/context/valid/objet/subclass_cont_01_oracle.txt";
        generalTestValid(args, file2);
    }
    @Test
    void test101() throws IOException {
        String[] args = {"src/test/deca/context/valid/objet/this_01.deca"};
        String file2 = "src/test/deca/context/valid/objet/this_01_oracle.txt";
        generalTestValid(args, file2);
    }



    void generalTestValid(String[] args, String fileOracle) throws IOException {
        Logger.getRootLogger().setLevel(Level.WARN);
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParser parser = new DecaParser(tokens);
        File file = new File(args[0]);
        DecacCompiler compiler = new DecacCompiler(new CompilerOptions(), file);
        parser.setDecacCompiler(compiler);
        AbstractProgram prog = parser.parseProgramAndManageErrors(System.err);
        if (prog == null) {
            System.exit(1);
            return; // Unreachable, but silents a warning.
        }
        try {
            prog.verifyProgram(compiler);
        } catch (LocationException e) {
            e.display(System.err);
            System.exit(1);
        }
        String file2 = fileOracle;
        String toCompare = new String(Files.readAllBytes(Paths.get(file2)));
        assertEquals(prog.prettyPrint(), toCompare);
    }

    void generalTestInvalid(String[] args, String fileOracle) throws IOException{
        Logger.getRootLogger().setLevel(Level.WARN);
        DecaLexer lex = AbstractDecaLexer.createLexerFromArgs(args);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParser parser = new DecaParser(tokens);
        File file = new File(args[0]);
        DecacCompiler compiler = new DecacCompiler(new CompilerOptions(), file);
        parser.setDecacCompiler(compiler);
        AbstractProgram prog = parser.parseProgramAndManageErrors(System.err);
        if (prog == null) {
            System.exit(1);
            return; // Unreachable, but silents a warning.
        }
        try {
            prog.verifyProgram(compiler);
            fail("Test not passed : didn't return expected Exception error");
        } catch (ContextualError e) {
            String file2 = fileOracle;
            String toCompare = new String(Files.readAllBytes(Paths.get(file2)));
            assertEquals(e.getMessage(), toCompare);
        }
    }

}

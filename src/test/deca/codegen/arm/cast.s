.data
int:	.asciz "%d"
float:	.asciz "%f"
ln:	.asciz "\n"
.text
.global main
main:
float_1:	.float 1.5
float_0:	.float 1.5
@ start main program
@ Main program
@ Beginning of main instructions:
	PUSH {FP, LR}
	ADDS FP, SP, #4
	SUBS SP, #8
	VLDR S2, float_0
	VCVT.S32.F32 S2, S2
	VMOV R4, S2
	VLDR S3, float_1
	VCVT.S32.F32 S3, S3
	VMOV R5, S3
	ADDS R4, R5
	STR R4, [FP, #-8]
	LDR R4, [FP, #-8]
	VMOV S2, R4
	VCVT.F32.S32 S2, S2
	LDR R5, [FP, #-8]
	VMOV S3, R5
	VCVT.F32.S32 S3, S3
	VADD.F32 S2, S3
	VSTR S2, [FP, #-12]
	LDR R0, =int
	LDR R1, [FP, #-8]
	BL printf
	LDR R0, =ln
	BL printf
	LDR R0, =float
	VLDR S16, [FP, #-12]
	VCVT.F64.F32 D0, S16
	VMOV R2, R3, D0
	BL printf
	LDR R0, =ln
	BL printf
	ADDS SP, #8
	POP {FP, PC}
	MOV R0, #0
	BL exit
@ end main program

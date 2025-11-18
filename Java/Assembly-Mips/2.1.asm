	.data
	
N:	.word 9
Result:	.word 0
		
	.text
	.globl main
	

main:
	lw 	$t1, N 
	lw	$s0, Result
	
	addi	$t3, $t3, 2	# Checks if input < 2
	slt 	$t0, $t3, $t1	
	beqz	$t0, fin	# If <2, end task

	andi 	$t3, $t1, 1	#Using the LSB to check even vs odd
	beqz 	$t3, loopi	
	
	addi 	$t1, $t1, -1	#If odd, subtract 1 and start and add t1
	add	$s0, $s0, $t1
	
loopi:	
	addi	$t1, $t1, -2	#add all even positive values of N
	add	$s0, $s0, $t1
	bnez	$t1, loopi 	

fin:
	sw 	$s0, Result	#Save result to memory
	
	addi	$a0, $s0, 0	#Prints the Result
	li	$v0, 1
	syscall
	
	li	$v0, 10
	syscall	
						
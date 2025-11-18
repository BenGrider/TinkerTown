	 .data
	 
N:	 .word 0
Result:	 .word 0
	
input:   .asciiz"Please enter an integer N\n"
output:  .asciiz"The result is "			
OOB: 	 .asciiz"N < 0, invalid for this program!”

	 .text
	
  	 .globl main
	

main:	
	lw 	$s0, Result
	
	li	$v0, 4		# Prints the string for input
	la 	$a0, input
	syscall
	
	li	$v0, 5		# Takes input and saves it in a tempVar and a variable
	syscall
	addi	$s1, $v0, 0
	addi	$t1, $s1, 0
	
	slt	$t0, $zero, $t1	# Checks if input <= 0
	beqz	$t0, OutOfBounds

	andi 	$t3, $t1, 1	#LSB to check even vs odd
	beqz 	$t3, loopi	
	
	addi 	$t1, $t1, -1	#If odd, subtract 1 and start and add t1
	add	$s0, $s0, $t1
	
loopi:	
	addi	$t1, $t1, -2	#add all even positive values of N
	add	$s0, $s0, $t1
	bnez	$t1, loopi 	
	beqz	$t1, fin


OutOfBounds: 	
	addi	$s0, $zero, -1	#Stores result as -1
	li	$v0, 4		#Prints the string for OOB
	la 	$a0, OOB
	syscall
	
	sw $s0, Result
	sw $s1, N
	
	li	$v0, 10		#End of Program
	syscall		

fin:
	sw $s0, Result		#Saves result and N to Memory
	sw $s1, N
	
	li	$v0, 4		#Prints the string for output
	la 	$a0, output
	syscall
	
	addi	$a0, $s0, 0	#Prints the Result
	li	$v0, 1
	syscall
	
	li	$v0, 10		#End of Program
	syscall	
		
			

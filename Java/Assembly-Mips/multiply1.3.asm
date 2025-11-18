	.data

input: .asciiz"Please enter an integer\n"
result:	.asciiz"The Product is "
		
		
arg:	.word			# Part 1) The program takes an argument and multiplies it by itself. arg^2
				
	.text	
	.globl main
main:

	li	$v0, 4		# Prints the string for input
	la 	$a0, input
	syscall
	
	li	$v0, 5		# Takes first input
	syscall
	addi	$t1, $v0, 0
	
	slt	$t2, $zero, $t1	# Checks if input <= 0
	beqz	$t2, LessEqualZero
	
	li	$v0, 4		# Prints the string for input
	la 	$a0, input
	syscall
	
	li	$v0, 5		# Takes second input
	syscall
	addi	$s1, $v0, 0
	
	slt	$t2, $zero, $s1	# Checks if input <= 0
	beqz	$t2, LessEqualZero
	
  	addi	$s3, $zero, 0	# s3 is made equal to zero
	beqz	$t1, fin	# if t1 equals zero, go to fin	
fori:
	add	$s3, $s3, $s1	# add s3 and s1
	addi	$t1, $t1, -1   # decrement t1 
	bnez	$t1, fori	# If b does not equal zero, loop.
	
	li	$v0, 4		# Prints the string for result
	la 	$a0, result
	syscall
	
	addi	$a0, $s3, 0	# Prints the product of the inputs
	li	$v0, 1
	syscall
	
	j fin

LessEqualZero:
	
	li	$v0, 4		# Prints the string for result
	la 	$a0, result
	syscall	
	
	addi	$a0, $t2, 0	# Prints the product as zero
	li	$v0, 1
	syscall
			
fin:
	li	$v0, 10         # ready to execute
	syscall		        # executes

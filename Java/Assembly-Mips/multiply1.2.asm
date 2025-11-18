	.data			#Part 2 of question 1 
arg:	.word	4		#The program takes two arguments and multiplies them
		12		

	.text
	.globl main
main:
	la	$t1, arg	#load the address of t1 with the value of the arg
	lw	$s1, 0($t1) 	#loading s1 as the address of t1
	lw	$t1, 4($t1)    #loading t1 as the address of t1

	addi	$s3, $zero, 0  #destination s3 adds the registry for zero and zero constant, this sets
	beqz	$t1, fin	#if t1 equals zero, go to fin	
fori:
	add	$s3, $s3, $s1	#add s3 and s1 and put it into s3 ---- adds s3 and s1 t1 number of times
	addi	$t1, $t1, -1   #decrement t1                     ---- where s3 is x and s1 = arg
	bnez	$t1, fori	#If b does not equal zero	
fin:

	addi	$a0, $s3, 0	#Prints the Result
	li	$v0, 1
	syscall
	
	li	$v0, 10         #ready to execute
	syscall		        #executes

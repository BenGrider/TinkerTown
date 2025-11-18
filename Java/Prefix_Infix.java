
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayDeque;
import java.io.FileNotFoundException;
import java.io.File;

/** Class to store a node of expression tree
    For each internal node, element contains a binary operator
    List of operators: +|*|-|/|%|^
    Other tokens: (|)
    Each leaf node contains an operand (long integer)

	test cases
	( 13 * 12 ) - ( 48 / 3 ) + 66  
	3 + 4 * 5 - 10 
	( 21 * 370 / ( 482 / 50 - 32 * ( 2 + 4 ) / 71 ) ) ^ 2 
	2 ^ ( 3 + 2 ) ^ 2 - 1 
	(1 + 2 * 3 - 4 % 5 + 6 * 7 - 8 / 9 * 10 + 11 - 12 * 13 + 14 / 15 - 16 + 17 * 18 - 19 * 20 ) 
*/

public class Expression {
    public enum TokenType {  // NIL is a special token that can be used to mark bottom of stack
	PLUS, TIMES, MINUS, DIV, MOD, POWER, OPEN, CLOSE, NIL, NUMBER
    }
    
    public static class Token {
		TokenType token;
		int priority; // for precedence of operator
		Long number;  // used to store number of token = NUMBER
		String string;

	Token(TokenType op, int pri, String tok) {
	    token = op;
	    priority = pri;
	    number = null;
	    string = tok;
	}

	// Constructor for number.  To be called when other options have been exhausted.
	Token(String tok) {
	    token = TokenType.NUMBER;
	    number = Long.parseLong(tok);
	    string = tok;
	}
	
	boolean isOperand() { return token == TokenType.NUMBER; }

	public long getValue() {
	    return isOperand() ? number : 0;
	}

	public String toString() { return string; }
    }

    Token element;
    Expression left, right;

    // Create token corresponding to a string
    // tok is "+" | "*" | "-" | "/" | "%" | "^" | "(" | ")"| NUMBER
    // NUMBER is either "0" or "[-]?[1-9][0-9]*
    static Token getToken(String tok) {  // To do
		Token result;
	switch(tok) {
	case "(":
	    //result = new Token(TokenType.OPEN, 1, tok);  //old
		result = new Token(TokenType.OPEN, -1, tok); 
	    break;
	    // Complete rest of this method
	case ")":
	    //result = new Token(TokenType.CLOSE, 2, tok); //old
		result = new Token(TokenType.CLOSE, -1, tok);
	    break;
	case "^":
	    //result = new Token(TokenType.POWER, 3, tok); //old
		result = new Token(TokenType.POWER, 5, tok);  
	    break;
	case "*":
	    result = new Token(TokenType.TIMES, 4, tok); 
	    break;
	case "/":
	    result = new Token(TokenType.DIV, 4, tok);  
	    break;
	case "%":
	    result = new Token(TokenType.MOD, 4, tok);  
	    break;
	case "+":
	    //result = new Token(TokenType.PLUS, 5, tok);  //old
		result = new Token(TokenType.PLUS, 3, tok);  
	    break;
	case "-":
	    //result = new Token(TokenType.MINUS, 5, tok);  //old
		result = new Token(TokenType.MINUS, 3, tok); 
	    break;
	default:
	    result = new Token(tok);
	    break;
	}
	return result;
    }
    
    private Expression() {
		element = null;
    }
    
    private Expression(Token oper, Expression left, Expression right) {
		this.element = oper;
		this.left = left;
		this.right = right;
    }

    private Expression(Token num) {
		this.element = num;
		this.left = null;
		this.right = null;
    }

    // Given a list of tokens corresponding to an infix expression,
    // return the expression tree corresponding to it.
	
    public static Expression infixToExpression(List<Token> exp) {  // To do
    Stack<Expression> stack = new Stack<Expression>();
        
    List<Token> postfix = infixToPostfix(exp);
    for(Token token : postfix) {
        if(token.isOperand()) {
            stack.push(new Expression(token));
        } else {
            stack.push(new Expression(token, stack.pop(), stack.pop()));
        }
    }

    return stack.pop();
}

    // Given a list of tokens corresponding to an infix expression,
    // return its equivalent postfix expression as a list of tokens.
    public static List<Token> infixToPostfix(List<Token> exp) {  // To do
        List<Token> result = new LinkedList<>();    
        ArrayDeque<Token> stack = new ArrayDeque<>();
        int i = 0;
        while(i < exp.size()) {  
            if (exp.get(i).isOperand()) { // If token is an operand

                result.add(exp.get(i));

            }
            
            else if(exp.get(i).token == TokenType.OPEN) { //if token is '('
                stack.push(exp.get(i)); //push the token
            }

            else if(exp.get(i).token == TokenType.CLOSE) { //if token is ')'
                while(!stack.isEmpty() && stack.peek().token != TokenType.OPEN) { //pop the stack until open parenthisis
                    result.add(stack.pop());
                }
                stack.pop(); // Clear out the closed parenthesis
            }
            
            else if(!exp.get(i).isOperand()){  //if token is an operator

                while (!stack.isEmpty() && exp.get(i).priority <= stack.peek().priority) { 
                    result.add(stack.pop());
                }
                stack.push(exp.get(i));
            }

            else {
                // Invalid character
            }
            

            i++;
        }

        while (!stack.isEmpty()) { // Pop Remaining Stack
            result.add(stack.pop());//STOP CHANGING THISSSS
        }

        return result;
    }
    // Given a postfix expression, evaluate it and return its value.
    public static long evaluatePostfix(List<Token> exp) {  // To do
        Stack<Long> stack = new Stack<Long>();
        for(Token t : exp) {
            if(t.isOperand()) {
                stack.push(t.number);
            } else {
                //long a = stack.pop(); //old
                //long b = stack.pop(); //old
				long b = stack.pop();
				long a = stack.pop();
                switch(t.token) {
                    case PLUS:
                        stack.push(a + b);
                        break;
                    case MINUS:
                        stack.push(a - b);
                        break;
                    case TIMES:
                        stack.push(a * b);
                        break;
                    case DIV:
                        stack.push(a / b);
                        break;
                    case MOD:
                        stack.push(a % b);
                        break;
                    case POWER:
                        stack.push((long)Math.pow(a, b));
                        break;
                }
            }
        }

        return stack.pop();
    }
	
    // Given an expression tree, evaluate it and return its value.
    public static long evaluateExpression(Expression tree) {  // To do	
		
		//leafnodes are operands
		if(tree.element.token == TokenType.NUMBER) {
			return tree.element.getValue();//HELPME
		}

		//Internal nodes are operators
		if (tree.element.token == TokenType.PLUS) {
			//return (evaluateExpression(tree.left) + evaluateExpression(tree.right)); //old
			return (evaluateExpression(tree.right) + evaluateExpression(tree.left));
		}

		else if (tree.element.token == TokenType.MINUS) {
			//return (evaluateExpression(tree.left) - evaluateExpression(tree.right)); //old
			return (evaluateExpression(tree.right) - evaluateExpression(tree.left));
		}

		else if (tree.element.token == TokenType.TIMES) {
			//return (evaluateExpression(tree.left) * evaluateExpression(tree.right)); //old
			return (evaluateExpression(tree.right) * evaluateExpression(tree.left));
		}

		else if (tree.element.token == TokenType.DIV) {
			//return (evaluateExpression(tree.left) / evaluateExpression(tree.right)); //old
			return (evaluateExpression(tree.right) / evaluateExpression(tree.left));
		}

		else if (tree.element.token == TokenType.MOD) {
			//return (evaluateExpression(tree.left) % evaluateExpression(tree.right)); //old
			return (evaluateExpression(tree.right) % evaluateExpression(tree.left));
		}

		else if (tree.element.token == TokenType.POWER) {
			return (long)(Math.pow(evaluateExpression(tree.left),evaluateExpression(tree.right))); //old
		}

		// If Internal node, perform operation
		// IF Leaf (type number) node return value

	return 0;
    }
	
    // sample main program for testing
    public static void main(String[] args) throws FileNotFoundException {
	Scanner in;
	
	if (args.length > 0) {
	    File inputFile = new File(args[0]);
	    in = new Scanner(inputFile);
	} else {
	    in = new Scanner(System.in);
	}

	int count = 0;
	while(in.hasNext()) {
	    String s = in.nextLine();
	    List<Token> infix = new LinkedList<>();
	    Scanner sscan = new Scanner(s);
	    int len = 0;
	    while(sscan.hasNext()) {
		infix.add(getToken(sscan.next()));
		len++;
	    }
	    if(len > 0) {
		count++;
		System.out.println("Expression number: " + count);
		System.out.println("Infix expression: " + infix);
		Expression exp = infixToExpression(infix);
		List<Token> post = infixToPostfix(infix);
		System.out.println("Postfix expression: " + post);
		long pval = evaluatePostfix(post);
		long eval = evaluateExpression(exp);
		System.out.println("Postfix eval: " + pval + " Exp eval: " + eval + "\n");
	    }
	}
    }
}

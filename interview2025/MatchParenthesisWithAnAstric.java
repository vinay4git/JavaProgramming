package interview2025;

import java.util.Stack;

class MatchParenthesisWithAnAstric {
    /*
    Given a string s containing only three types of characters: '(', ')' and '*', return true if s is valid.
The following rules define a valid string:
Any left parenthesis '(' must have a corresponding right parenthesis ')'.
Any right parenthesis ')' must have a corresponding left parenthesis '('.
Left parenthesis '(' must go before the corresponding right parenthesis ')'.
'*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string "".

Example 1:
Input: s = "()"
Output: true
Example 2:
Input: s = "(*)"
Output: true
Example 3:
Input: s = "(*))"
Output: true
Input: s = "****(()))((()"
Output: false
Input: s = "****(()))()"
Output: true
     */
    public static void main(String[] args) {
        String s = "****(()))((()";

        Stack<Integer> openParanthesisIndexStack = new Stack<>();
        Stack<Integer> starIndexStack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                openParanthesisIndexStack.push(i);
            } else if (s.charAt(i) == '*') {
                starIndexStack.push(i);
            } else {
                if (!openParanthesisIndexStack.isEmpty()) {
                    openParanthesisIndexStack.pop();
                } else if (!starIndexStack.isEmpty()) {
                    starIndexStack.pop();
                } else {
                    System.out.println(false);
                    return;
                }
            }
        }

        while (!openParanthesisIndexStack.isEmpty() ) {
            if (!starIndexStack.isEmpty() && openParanthesisIndexStack.peek() < starIndexStack.peek()) {
                starIndexStack.pop();
                openParanthesisIndexStack.pop();
            } else {
                System.out.println(false);
                return;
            }
        }
        System.out.println(true);
        
    }
}
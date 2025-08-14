package interview2025;

import java.util.HashMap;

class LongestUniqueSubstringWithAtleastADigit {
    /*

    Problem Statement: Longest Unique Substring With a Digit
You are given a string s that may contain:
Lowercase letters (a-z)
Uppercase letters (A-Z)
Digits (0-9)
Special characters (!@#...)

Your task is to find the length of the longest contiguous substring of s that satisfies:
All characters in the substring are unique (no repeating characters, but digits can be duplicate).
The substring contains at least one digit (0-9).

Constraints:
1 <= len(s) <= 10^5
s contains printable ASCII characters.

 Examples
Example 1:
Input: "abc1234def" Output: 10 # Explanation: Entire string has all unique characters and includes digits — "abc1234def"
Example 2:
Input: "abcdabc1" Output: 5 # Explanation: "dabc1" is the longest substring with all unique characters and at least one digit
Example 3:
Input: "ab1dabc1" Output: 5 # Explanation: "dabc1" is the valid longest substring
Example 4:
Input: "1abbdabc1" Output: 5 # Explanation: "dabc1" is the valid longest substring. All characters are unique and it includes '1'

 Exp
 "a" --> 0

 "abcd1ea"
     */

    public static void main(String[] args) {

        String s = "11111";

        int left = 0, right = 0, maxSubString = 0;
        HashMap<Character, Integer> previousCharsInWindow = new HashMap<>();

        while (right < s.length()) {

            char currentChar = s.charAt(right);

            if (isDigit(currentChar)) {
                Integer curCharCountInMap = previousCharsInWindow.getOrDefault(currentChar, 0);
                previousCharsInWindow.put(currentChar, curCharCountInMap+1);
                
                previousCharsInWindow.compute(currentChar,(character, integer) -> integer+1);
            } else {
                while (previousCharsInWindow.containsKey(currentChar)) {
                    if (isDigit(currentChar)) {
                        Integer curCharCountInMap = previousCharsInWindow.getOrDefault(currentChar, 1);
                        previousCharsInWindow.put(currentChar, curCharCountInMap-1);
                    } else {
                        previousCharsInWindow.remove(s.charAt(left));
                    }
                    left++;
                }

                previousCharsInWindow.put(currentChar,1);
            }

            if (isMapHasAnyDigit(previousCharsInWindow)) {
                maxSubString = Math.max(maxSubString, right -left + 1);
            }
            right++;
        }
        System.out.println("Max Sub String : " + maxSubString);
    }

    public static boolean isDigit(char c) {
        return "0123456789".contains(String.valueOf(c));
    }

    public static boolean isMapHasAnyDigit(HashMap<Character, Integer> previousCharMap) {
        for (int i = 0; i < 10; i++) {
            if (previousCharMap.getOrDefault("0123456789".charAt(i), 0 ) > 0)
                    return true;
        }
        return  false;
    }
}
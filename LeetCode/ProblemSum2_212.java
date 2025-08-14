package LeetCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProblemSum2_212 {
    public static void main(String[] args) {
        char[][] c = {{'a','a'}};
        String[] words = {"aaa"};
        Solution1 s = new Solution1();
        s.findWords(c,words).stream().forEach(System.out::println);
    }
}


class TrieNode {
    Map<Character, TrieNode> nextNodes;
    String val;

    TrieNode() {
        nextNodes = new HashMap<>();
    }
}

class Solution1 {
    public List<String> findWords(char[][] board, String[] words) {
        TrieNode root = buildTrie(words);
        HashSet<String> result = new HashSet<>();

        for(int i=0; i < board.length; i++) {
            for ( int j=0; j < board[i].length; j++) {
                dfs(board, i , j, root, result);
            }
        }

        return result.stream().collect(Collectors.toList());
    }

    TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for(String word : words) {
            TrieNode current = root;
            for(Character c : word.toCharArray()) {
                TrieNode t = current.nextNodes.getOrDefault(c,new TrieNode());
                current.nextNodes.putIfAbsent(c,t);
                current = t;
            }
            current.val = word;
        }
        return root;
    }

    void dfs(char[][] board, int row, int column, TrieNode root, HashSet<String> result) {
        if(root != null && root.val != null) {
            result.add(root.val);
        }

        if(row >= board.length || row < 0 || column >= board[row].length || column < 0 || board[row][column] == '#' ) {
            return;
        }

        if(root != null && root.val != null) {
            result.add(root.val);
        }

        char c = board[row][column];
        if ( root.nextNodes.get(c) == null) {
            return;
        }

        board[row][column] = '#' ;
        dfs(board, row+1, column, root.nextNodes.get(c), result);
        dfs(board, row-1, column, root.nextNodes.get(c), result);
        dfs(board, row, column-1, root.nextNodes.get(c), result);
        dfs(board, row, column+1, root.nextNodes.get(c), result);
        board[row][column] = c;
    }
}
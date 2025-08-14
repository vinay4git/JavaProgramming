package interview2025;

import java.util.*;
import java.util.stream.Collectors;

class LongestPossibleCycleInGraph {
    /*
    Print the longest possible subset of Strings from the given list of strings.
    Such that they form a chain.
    End character of current String should be equal to the first character of next String
    First character of first String should be equal to the last character of last String.
    * */
    public static void main(String[] args) {
        List<String> s = List.of("deep", "hen", "pent", "nod", "root", "toll", "length", "topper");
        // the longest subset of Strings from input that should be returned
        // ["hen",  "nod", "deep", "pent", "toll", "length"]

        // Logic is the build a graph and return the biggest cycle in the graph

        Map<String, Set<String>> graph = s.stream().collect(Collectors.toMap(String::toLowerCase, o -> new HashSet<>()));

        Map<Character, Set<String>> firstCharacterStrings = new HashMap<>();
        Map<Character, Set<String>> lastCharacterStrings = new HashMap<>();

        s.stream().forEach(word -> {

            // Add the current word in the graph
            firstCharacterStrings.computeIfAbsent(word.charAt(0), c -> new HashSet<>()).add(word);
            lastCharacterStrings.computeIfAbsent(word.charAt(word.length()-1), c -> new HashSet<>()).add(word);

            // set of words current from graph that are having current word last char as their first character
            Set<String> lastCharOfWordAsTheirFirstChar = firstCharacterStrings.getOrDefault(word.charAt(word.length()-1), new HashSet<>());

            graph.get(word).addAll(lastCharOfWordAsTheirFirstChar);

            // set of words current from graph that are having current word first char as their first character
            Set<String> firstCharOfWordAsTheirLastChar = lastCharacterStrings.getOrDefault(word.charAt(0), new HashSet<>());

            firstCharOfWordAsTheirLastChar.stream().forEach(w -> {
                graph.get(w).add(word);
            });
        });

        System.out.println(graph);

        HashSet<String> visitedSet = new HashSet<>();
        Stack<String> currentDFSWordsInStack = new Stack<>();
        List<String> maxCycleWords = new ArrayList<>();

        for (String word : s) {
            if (!visitedSet.contains(word)) {
                dfs(graph, word, visitedSet,currentDFSWordsInStack, maxCycleWords);
            }
        }

        System.out.println(maxCycleWords);
    }

    private static void dfs(Map<String, Set<String>> graph, String word, HashSet<String> visitedSet, Stack<String> currentDFSWordsInStack, List<String> maxCycleWords) {

        if (currentDFSWordsInStack.contains(word)) {
            int existingWordIndex = currentDFSWordsInStack.indexOf(word);

            if ( maxCycleWords.size() < (currentDFSWordsInStack.size() - existingWordIndex )) {
                maxCycleWords.removeAll(maxCycleWords);
                maxCycleWords.addAll(currentDFSWordsInStack.subList(existingWordIndex, currentDFSWordsInStack.size()));
            }
            return;
        }
        if (visitedSet.contains(word)) {
            return;
        }
        visitedSet.add(word);
        currentDFSWordsInStack.push(word);

        for (String nextWord : graph.get(word)) {
            dfs(graph, nextWord, visitedSet, currentDFSWordsInStack, maxCycleWords);
        }

        currentDFSWordsInStack.pop();
    }
}
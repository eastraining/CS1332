import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of the Boyer Moore string searching algorithm.
 */
public class PatternMatching {

  /**
   * Boyer Moore algorithm that relies on a last occurrence table.
   * This algorithm Works better with large alphabets.
   *
   * Make sure to implement the buildLastTable() method, which will be
   * used in this boyerMoore() method.
   *
   * Note: You may find the getOrDefault() method from Java's Map class useful.
   *
   * You may assume that the passed in pattern, text, and comparator will not be null.
   *
   * @param pattern    The pattern you are searching for in a body of text.
   * @param text       The body of text where you search for the pattern.
   * @param comparator You MUST use this to check if characters are equal.
   * @return           List containing the starting index for each match found.
   */
  public static List<Integer> boyerMoore(
    CharSequence pattern,
    CharSequence text,
    CharacterComparator comparator
  ) {
    int patternLength = pattern.length(), textLength = text.length();
    List<Integer> matches = new LinkedList<Integer>();
    if (patternLength > textLength) return matches;

    Map<Character, Integer> lastTable = buildLastTable(pattern);
    int currentText = patternLength - 1, currentPattern = patternLength - 1;

    while (currentText < textLength) {
      while (currentPattern >= 0) {
        if (
          comparator.compare(
            text.charAt(currentText),
            pattern.charAt(currentPattern)
          ) ==
          0
        ) {
          currentText--;
          currentPattern--;
        } else {
          break;
        }
      }
      if (currentPattern < 0) {
        currentText++;
        matches.add(currentText);
        currentText += patternLength;
        currentPattern = patternLength - 1;
        continue;
      }
      int lastAt = lastTable.getOrDefault(text.charAt(currentText), -1);
      if (lastAt < 0) {
        currentText += patternLength;
        currentPattern = patternLength - 1;
      } else if (lastAt < currentPattern) {
        int difference = currentPattern - lastAt;
        currentText += difference;
        currentPattern = patternLength - 1;
      } else {
        int difference = patternLength - currentPattern;
        currentText += difference;
        currentPattern = patternLength - 1;
      }
    }
    return matches;
  }

  /**
   * Builds the last occurrence table that will be used to run the Boyer Moore algorithm.
   *
   * Note that each char x will have an entry at table.get(x).
   * Each entry should be the last index of x where x is a particular
   * character in your pattern.
   * If x is not in the pattern, then the table will not contain the key x,
   * and you will have to check for that in your Boyer Moore implementation.
   *
   * Ex. pattern = octocat
   *
   * table.get(o) = 3
   * table.get(c) = 4
   * table.get(t) = 6
   * table.get(a) = 5
   * table.get(everything else) = null, which you will interpret in
   * Boyer-Moore as -1
   *
   * If the pattern is empty, return an empty map.
   * You may assume that the passed in pattern will not be null.
   *
   * @param pattern A pattern you are building last table for.
   * @return A Map with keys of all of the characters in the pattern mapping
   *         to their last occurrence in the pattern.
   */
  public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
    Map<Character, Integer> lastTable = new HashMap<>();
    for (int i = 0; i < pattern.length(); i++) {
      lastTable.put(pattern.charAt(i), i);
    }
    return lastTable;
  }

  // Tests
  private static void printResults(
    List<Integer> results,
    CharacterComparator comparator,
    int cumulative
  ) {
    System.out.print("Pattern found at indices: ");
    for (Integer result : results) {
      System.out.print(result + " ");
    }
    int compares = comparator.getComparisonCount() - cumulative;
    System.out.println("Compares: " + compares);
  }

  public static void main(String[] args) {
    CharacterComparator comparator = new CharacterComparator();
    String text = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
    String text2 = "aaaaabaaaaabaaaaabcaaaaab";
    String pattern1 = "aaaaab";
    String pattern2 = "baaaaa";
    String pattern3 = "cccccc";
    int cumulative = 0;

    List<Integer> result1 = boyerMoore(pattern1, text, comparator);
    printResults(result1, comparator, cumulative);
    cumulative = comparator.getComparisonCount();

    List<Integer> result2 = boyerMoore(pattern2, text, comparator);
    printResults(result2, comparator, cumulative);
    cumulative = comparator.getComparisonCount();

    List<Integer> result3 = boyerMoore(pattern3, text, comparator);
    printResults(result3, comparator, cumulative);
    cumulative = comparator.getComparisonCount();

    List<Integer> result4 = boyerMoore(pattern1, text2, comparator);
    printResults(result4, comparator, cumulative);
    cumulative = comparator.getComparisonCount();
  }
}

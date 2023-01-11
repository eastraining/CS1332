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
    // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
    List<Integer> occurrences = new LinkedList<>();
    int patternLen = pattern.length();
    int textLen = text.length();
    if (patternLen > textLen) return occurrences;

    Map<Character, Integer> lastTable = buildLastTable(pattern);
    int currentIdx = patternLen - 1;
    int patternIdx = patternLen - 1;
    while (currentIdx < textLen) {
      char currentChar = text.charAt(currentIdx);
      char patternChar = pattern.charAt(patternIdx);
      if (comparator.compare(currentChar, patternChar) == 0) {
        if (patternIdx == 0) {
          occurrences.add(currentIdx);
          currentIdx += patternLen;
          patternIdx = patternLen - 1;
        } else {
          currentIdx--;
          patternIdx--;
        }
      } else {
        int newIdx = lastTable.getOrDefault(currentChar, -1);
        if (newIdx < 0 || newIdx > currentIdx) {
          currentIdx += patternLen;
          patternIdx = patternLen - 1;
        } else {
          currentIdx += patternLen - newIdx - 1;
          patternIdx = patternLen - 1;
        }
      }
    }

    return occurrences;
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
    // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
    Map<Character, Integer> lastTable = new HashMap<>();
    int currIdx = 0;
    int patternLen = pattern.length();
    while (currIdx < patternLen) {
      lastTable.put(pattern.charAt(currIdx), currIdx);
      currIdx++;
    }
    return lastTable;
  }
}

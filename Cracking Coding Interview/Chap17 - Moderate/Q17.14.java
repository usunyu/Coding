/*
Oh, no! You have just completed a lengthy document when you have an unfortunate Find/Replace mishap. You have 
accidentally removed all spaces, punctuation, and capitalization in the document. A sentence like 
"I reset the computer. It still didn't boot!" would become "iresetthecomputeritstilldidntboot". You figure that 
you can add back in the punctation and capitalization later, once you get the individual words properly separated. 
Most of the words will be in a dictionary, but some strings, like proper names, will not.
Given a dictionary (a list of words), design an algorithm to find the optimal way of "unconcatenating" a sequence 
of words. In this case, "optimal" is defined to be the parsing which minimizes the number of unrecognized sequences
of characters.
For example, the string "jesslookedjustliketimherbrother" would be optimally parsed as "JESS looked just like TIM 
her brother". This parsing has seven unrecognized characters, which we have capitalized for clarity.
*/
/* Thought
Using dynamic programming, bottom-up build.
*/
import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;

class ResultHelper {
	ArrayList<String> path;
	int charLeft;

	public ResultHelper(int chars) {
		path = new ArrayList<String>();
		charLeft = chars;
	}

	public ResultHelper(ResultHelper res) {
		this.charLeft = res.charLeft;
		this.path = new ArrayList<String>(res.path);
	}
}

class Solution {
	public static ResultHelper parsing(String text, HashSet<String> dict, ResultHelper result, HashMap<String, Integer> cache) {
		if(text.equals("")) {
			// debug
			System.out.println("[DEBUG] chars left: " + result.charLeft + ", words list: " + result.path);
			return result;
		}
		ResultHelper ret = null;
		for(int i = 1; i <= text.length(); i++) {
			ResultHelper res = new ResultHelper(result);
			String word = text.substring(0, i);
			if(dict.contains(word)) {
				res.charLeft -= word.length();
			}
			res.path.add(word);
			String left = text.substring(i);
			if(!cache.containsKey(left) || cache.get(left) > res.charLeft) {
				cache.put(left, res.charLeft);
				res = parsing(left, dict, res, cache);
			}
			else {
				// debug
				// System.out.println("Using cache.");
			}
			if(ret == null || ret.charLeft > res.charLeft) {
				ret = res;
			}
		}
		return ret;
	}

	public static void main(String[] args) {
		String text = "jesslookedjustliketimherbrother";
		HashSet<String> dict = new HashSet<String>();
		dict.add("looked");
		dict.add("just");
		dict.add("like");
		dict.add("her");
		dict.add("brother");
		ResultHelper result = parsing(text, dict, new ResultHelper(text.length()), new HashMap<String, Integer>());
		ArrayList<String> finalPath = new ArrayList<String>();
		String prev = "";
		for(String str : result.path) {
			if(dict.contains(str)) {
				if(!prev.equals("")) {
					finalPath.add(prev);
					prev = "";
				}
				finalPath.add(str);
			}
			else {
				prev += str.toUpperCase();
			}
		}
		if(!prev.equals("")) {
			finalPath.add(prev);
		}
		System.out.println("[RESULT] chars left: " + result.charLeft + ", words list: " + finalPath);
	}
}
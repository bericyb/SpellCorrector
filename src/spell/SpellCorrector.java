package spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Float.NEGATIVE_INFINITY;

public class SpellCorrector implements ISpellCorrector{
    Trie myDictionary = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        Scanner myScanner = new Scanner(new File(dictionaryFileName));
        while (myScanner.hasNext()) {
            myDictionary.add(myScanner.next());
        }
    }

    @Override
    public String suggestSimilarWord(String wordy) {
        String inputWord = wordy.toLowerCase();
        if (myDictionary.find(inputWord) != null) {
            return inputWord;
        }

        Set<String> genOne = genDistances(inputWord);

        String suggestion = null;
        float maxi = NEGATIVE_INFINITY;

        for (String oneWords: genOne) {
            INode searchRes = myDictionary.find(oneWords);
            if (searchRes != null && searchRes.getValue() > maxi) {
                suggestion = oneWords;
                maxi = searchRes.getValue();
            }
            else if (searchRes != null && searchRes.getValue() == maxi) {
                String[] sorter = {oneWords, suggestion};
                Arrays.sort(sorter);
                suggestion = sorter[0];
                maxi = searchRes.getValue();
            }
        }

        if (suggestion != null) {
            return suggestion;
        }

        Set<String> genTwo = new HashSet<String>();

        for (String oneWords: genOne) {
            genTwo.addAll(genDistances(oneWords));
        }



        for (String twoWords: genTwo) {
            INode searchRes = myDictionary.find(twoWords);
            if (searchRes != null && searchRes.getValue() > maxi) {
                suggestion = twoWords;
                maxi = searchRes.getValue();
            }
            else if (searchRes != null && searchRes.getValue() == maxi) {
                String[] sorter = {twoWords, suggestion};
                Arrays.sort(sorter);
                suggestion = sorter[0];
                maxi = searchRes.getValue();
            }
        }

        if (suggestion != null) {
            return suggestion;
        }
        else {
            return null;
        }
    }

    private Set<String> genDistances(String word) {

        Set<String> res = new HashSet<String>();

        //Deletions
        for (int i = 0; i < word.length(); i++) {
            StringBuilder newString = new StringBuilder(word);
            newString.deleteCharAt(i);
            res.add(newString.toString());
        }

        //Transposition
        for (int i = 0; i < word.length()-1; i++) {
            String og = word;
            String sub = word.substring(i, i+2);
            StringBuilder newSub = new StringBuilder(sub);
            newSub = newSub.reverse();
            StringBuilder deleted = new StringBuilder(og);
            deleted.deleteCharAt(i);
            deleted.deleteCharAt(i);

            deleted.insert(i, newSub);
            res.add(deleted.toString());
        }

        //Alteration
        for (int i = 0; i < word.length(); i++) {
            int currLetter = (int) word.charAt(i);
            for (int j = 97; j < 123; j++) {
                StringBuilder og = new StringBuilder(word);
                if (j != currLetter) {
                    og.deleteCharAt(i);
                    og.insert(i, (char) j);
                    res.add(og.toString());
                }
            }
        }

        //Insertion
        for (int i = 0; i < word.length()+1; i++) {
            for (int j = 97; j < 123; j++) {
                StringBuilder og = new StringBuilder(word);
                og.insert(i, (char) j);
                res.add(og.toString());
            }
        }

        //Return resulting set.
        return res;
    }

}

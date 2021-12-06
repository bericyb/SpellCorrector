package spell;

import java.util.Locale;

public class Trie implements ITrie{
    Node root;
    int wordCount;
    int nodeCount;

    public Trie() {
        this.root = new Node();
        this.wordCount = 0;
        this.nodeCount = 1;
    }

    @Override
    public void add(String wordy) {
        String word = wordy.toLowerCase();
        INode currNode = root;

        for (int i = 0; i < word.length(); i++) {
            int letterIndex = ((int) word.charAt(i)) - 97;
            if (currNode.getChildren()[letterIndex] != null) {
                currNode = currNode.getChildren()[letterIndex];
            } else {
                Node newNody = new Node();
                currNode.getChildren()[letterIndex] = newNody;
                currNode = newNody;
                nodeCount++;
            }
        }
        if (currNode.getValue() == 0) {
            wordCount++;
        }
        currNode.incrementValue();
    }

    @Override
    public INode find(String word) {
        INode currNode = root;

        for (int i = 0; i < word.length(); i++) {
            int letterIndex = ((int)word.charAt(i) - 97);
            if (currNode.getChildren()[letterIndex] != null) {
                currNode = currNode.getChildren()[letterIndex];
            }
            else  {
                return null;
            }
        }
        if (currNode.getValue() > 0) {
            return currNode;
        }
        else {
            return null;
        }
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        StringBuilder currWord = new StringBuilder();
        INode currNode = root;

        toStringHelper(root, currWord, res);
        return res.toString();
    }

    private void toStringHelper(INode currNode, StringBuilder currWord, StringBuilder res) {
        if (currNode.getValue() > 0) {
            res.append(currWord);
            res.append("\n");
        }
        for (int i = 0; i < 27; i++) {

            if (currNode.getChildren()[i] != null) {
                currWord.append((char) (i + 97));
                toStringHelper(currNode.getChildren()[i], currWord, res);
                currWord.deleteCharAt(currWord.length()-1);
            }
        }
    }



    @Override
    public int hashCode() {
        int hashBrowns = this.wordCount * this.nodeCount;
        //This is constant time because it's always a loop of 26 or less :)
        for (int i = 0; i < 27; i++) {
            if (root.getChildren()[i] != null) {
                hashBrowns = hashBrowns * i+1;
            }
            else {
                hashBrowns--;
            }
        }
        return hashBrowns;
    }


    @Override
    public boolean equals(Object a) {
        if (a == null) {
            return false;
        }

        if (a == this) {
            return true;
        }

        if (a.getClass() != this.getClass()) {
            return false;
        }

        Trie that = (Trie) a;

        if (that.wordCount != this.wordCount || that.nodeCount != this.nodeCount) {
            return false;
        }

        if (equalsHelper(that.root, this.root)) {
            return true;
        }

        return false;

    }

    private boolean equalsHelper(INode a, INode b) {
        if (a.getValue() != b.getValue()) {
            return false;
        }
        boolean isMatch = true;
        INode [] aboiz = a.getChildren();
        INode [] bboiz = b.getChildren();
        for (int i = 0; i < 27; i++) {
            if ((aboiz[i] != null && bboiz[i] == null) || (aboiz[i] == null && bboiz[i] != null)){
                return false;
            }
            else if (aboiz[i] != null && bboiz[i] != null){
                isMatch = equalsHelper(aboiz[i], bboiz[i]);
            }
        }
        return isMatch;
    }
}

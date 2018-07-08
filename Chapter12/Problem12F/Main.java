package Chapter12.Problem12F;

import java.io.*;
import java.util.*;
import java.lang.*;


public class Main {
    public static final String[] WORDS = {
            "ABOUT", "ABOVE", "ACROSS", "ACT", "ACTIVE", "ACTIVITY",
            "CAKE", "CALL", "CAN", "CANDLE",
            "KEEP", "KEY", "KIN", "KIND", "KING", "KILL"
    };
    
    public static void main(String[] args){
        Trie trie = new Trie();
        
        for(String word : WORDS){
            trie.insert(word);
        }
        
        System.out.println( trie.find("ABOUT") );
        
        trie.erase("ABOUT");
        
        System.out.println( trie.find("ABOUT") );
    }
}

class Trie{
    private Node rootNode;
    
    public Trie() {
        this.rootNode = new Node(null);
    }
    
    public void insert(String S) {
        rootNode.insert(S, 0);
    }
    
    public void erase(String S) {
        rootNode.erase(S, 0);
    }
    
    public boolean find(String S) {
        return rootNode.find(S, 0);
    }
    
    private static class Node {
        private Character character;
        private HashMap<Character, Node> nextNodes;
        private boolean isTerminalNode; // 단어의 마지막 여부
        
        public Node(Character character) {
            this.character = character;
            this.nextNodes = new HashMap<>();
            this.isTerminalNode = false;
        }
        
        /**
         * 현재 노드 하위 그래프에 문자열을 추가하는 함수
         * S[matched...] 문자열을 추가한다.
         *
         * @param S         추가할 문자열
         * @param matched   이때까지 처리된 문자수. 이 노드에서는 S.charAt(matched) 문자에 대한 처리.
         */
        public void insert(String S, int matched) {
            if(matched == S.length()) { // 추가 연산의 마지막 정점
                this.isTerminalNode = true;
                return;
            }
            
            char c = S.charAt(matched);
            
            if(nextNodes.containsKey(c) == false) { // 다음 노드에 c라는 문자가 없으면
                Node newNode = new Node(c); // 현재 노드에서 c라는 문자에 해당하는 자식노드
                nextNodes.put(c, newNode);
            }
    
            Node child = nextNodes.get(c);
            child.insert(S, matched + 1);
        }
        
        public void erase(String S, int matched) {
            if(matched == S.length()) {
                this.isTerminalNode = false;
                return;
            }
            char c = S.charAt(matched);
            if(this.nextNodes.containsKey(c) == false) {
                // Trie에 삭제할 문자열이 없으므로
                return;
            }
    
            Node child = nextNodes.get(c);
            child.erase(S, matched + 1);
        }
        
        public boolean find(String S, int matched) {
    
            if(matched == S.length()) {
                return  this.isTerminalNode; // 터미널 노드라면 해당 단어가 존재하거나 존재 않하거나.
            }
            
            char c = S.charAt(matched);
            if(this.nextNodes.containsKey(c) == false) {
                // Trie에 문자가 없으므로
                return false;
            }
    
            Node child = nextNodes.get(c);
            
            return child.find(S, matched + 1);
        }
    }
    
}

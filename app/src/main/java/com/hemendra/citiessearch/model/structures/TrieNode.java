package com.hemendra.citiessearch.model.structures;

import java.util.HashMap;

class TrieNode {

    // the collection of all the children below this node
    // this mapping is ASCII CODE -> to -> TrieNode
    public HashMap<Integer, TrieNode> children = new HashMap<>();

    // if this value represents the complete string, then this variable will be >= 0
    public int cityIndex = -1;

}

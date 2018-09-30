package com.hemendra.citiessearch.model.structures;

import java.util.HashMap;

class RadixNode {

    // the collection of all the children below this node
    // this mapping is ASCII CODE -> to -> TrieNode
    public HashMap<Integer, RadixNode> children = new HashMap<>();
    public HashMap<Integer, StringBuilder> labels = new HashMap<>();

    // if this value represents the complete string, then this variable will be >= 0
    public int cityIndex = -1;

}

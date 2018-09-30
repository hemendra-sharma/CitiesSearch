package com.hemendra.citiessearch.model.structures;

import com.hemendra.citiessearch.data.City;
import com.hemendra.citiessearch.model.listeners.PrefixSearchStructure;

import java.util.ArrayList;

public class PrefixSearchStructureFactory {

    public static PrefixSearchStructure getStructure(StructureType type,
                                                     ArrayList<City> cities) {
        if(type == StructureType.RADIX)
            return Radix.getInstance(cities);
        else
            return Trie.getInstance(cities);
    }

}

/********************************************************************************
 * MIT Java Wordnet Interface Library (JWI) v2.3.3
 * Copyright (c) 2007-2014 Massachusetts Institute of Technology
 *
 * JWI is distributed under the terms of the Creative Commons Attribution 3.0 
 * Unported License, which means it may be freely used for all purposes, as long 
 * as proper acknowledgment is made.  See the license file included with this
 * distribution for more details.
 *******************************************************************************/

package edu.mit.jwi.item;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Default implementation of {@code IIndexWord}.
 * 
 * @author Mark A. Finlayson
 * @version 2.3.3
 * @since JWI 1.0
 */
public class IndexWord implements IIndexWord {

	// immutable instance fields
    private final IIndexWordID id;
    private final int tagSenseCount;
    private final Set<IPointer> pointers;
    private final List<IWordID> wordIDs;

	/**
	 * Constructs a new index word.
	 * 
	 * @param lemma
	 *            the lemma of this index word
	 * @param pos
	 *            the part of speech of this index word
	 * @param tagSenseCnt
	 *            the tag sense count
	 * @param words
	 *            the words for this index word
	 * @throws NullPointerException
	 *             if lemma, pos, or word array is <code>null</code>, or the
	 *             word array contains null
	 * @throws IllegalArgumentException
	 *             if the tag sense count is negative, or the word array is
	 *             empty
	 * @since JWI 1.0
	 */
    public IndexWord(String lemma, POS pos, int tagSenseCnt, IWordID... words) {
        this(new IndexWordID(lemma, pos), tagSenseCnt, null, words);
    }
    
	/**
	 * Constructs a new index word.
	 * 
	 * @param lemma
	 *            the lemma of this index word
	 * @param pos
	 *            the part of speech of this index word
	 * @param tagSenseCnt
	 *            the tag sense count
	 * @param ptrs
	 *            an array of pointers that the synsets with lemma have; may be
	 *            <code>null</code>
	 * @param words
	 *            the words for this index word
	 * @throws NullPointerException
	 *             if lemma, pos, or word array is <code>null</code>, or the
	 *             word array or pointer array contains <code>null</code>
	 * @throws IllegalArgumentException
	 *             if the tag sense count is negative, or the word array is
	 *             empty
	 * @since JWI 2.3.0
	 */
    public IndexWord(String lemma, POS pos, int tagSenseCnt, IPointer[] ptrs, IWordID... words) {
        this(new IndexWordID(lemma, pos), tagSenseCnt, ptrs, words);
    }

	/**
	 * Constructs a new index word.
	 * 
	 * @param id
	 *            the index word id for this index word
	 * @param tagSenseCnt
	 *            the tag sense count
	 * @param words
	 *            the words for this index word
	 * @throws NullPointerException
	 *             if lemma, pos, or word array is <code>null</code>, or the
	 *             word array contains null
	 * @throws IllegalArgumentException
	 *             if the tag sense count is negative, or the word array is
	 *             empty
	 * @since JWI 1.0
	 */
    public IndexWord(IIndexWordID id, int tagSenseCnt, IWordID... words) {
        this(id, tagSenseCnt, null, words);
    }
    
	/**
	 * Constructs a new index word.
	 * 
	 * @param id
	 *            the index word id for this index word
	 * @param tagSenseCnt
	 *            the tag sense count
	 * @param ptrs
	 *            an array of pointers for all the synsets of this lemma; may be
	 *            <code>null</code>; must not contain <code>null</code>
	 * @param words
	 *            the words for this index word
	 * @throws NullPointerException
	 *             if lemma, pos, or word array is <code>null</code>, or the
	 *             word array or pointer array contains <code>null</code>
	 * @throws IllegalArgumentException
	 *             if the tag sense count is negative, or the word array is
	 *             empty
	 * @since JWI 2.3.0
	 */
    public IndexWord(IIndexWordID id, int tagSenseCnt, IPointer[] ptrs, IWordID... words) {
        if (id == null) 
        	throw new NullPointerException();
        if(tagSenseCnt < 0)
        	throw new IllegalArgumentException();
        if (words.length == 0)
        	throw new IllegalArgumentException();
        for(IWordID wid : words)
        	if(wid == null)
        		throw new NullPointerException();
        
        // do pointers as of v2.3.0
        Set<IPointer> pointers;
        if(ptrs == null || ptrs.length == 0){
        	pointers = Collections.emptySet();
        } else {
        	pointers = new HashSet<IPointer>(ptrs.length);
        	for(IPointer p : ptrs)
        		if(p == null){
        			throw new NullPointerException();
        		} else {
        			pointers.add(p);
        		}
        }
        
        this.id = id;
        this.tagSenseCount = tagSenseCnt;
        this.wordIDs = Collections.unmodifiableList(Arrays.asList(words));
        this.pointers = pointers;
    }

    /* 
     * (non-Javadoc) 
     *
     * @see edu.mit.jwi.item.IIndexWord#getLemma()
     */
    public String getLemma() {
        return id.getLemma();
    }
    
    /* 
	 * (non-Javadoc) 
	 *
	 * @see edu.mit.jwi.item.IIndexWord#getPointers()
	 */
	public Set<IPointer> getPointers() {
		return pointers;
	}

	/* 
     * (non-Javadoc) 
     *
     * @see edu.mit.jwi.item.IIndexWord#getWordIDs()
     */
    public List<IWordID> getWordIDs() {
        return wordIDs;
    }
    
	/* 
	 * (non-Javadoc) 
	 *
	 * @see edu.mit.jwi.item.IIndexWord#getTagSenseCount()
	 */
	public int getTagSenseCount() {
		return tagSenseCount;
	}
	
    /* 
     * (non-Javadoc) 
     *
     * @see edu.mit.jwi.item.IItem#getID()
     */
    public IIndexWordID getID() {
        return id;
    }

    /* 
     * (non-Javadoc) 
     *
     * @see edu.mit.jwi.item.IHasPOS#getPOS()
     */
    public POS getPOS() {
        return id.getPOS();
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append('[');
    	sb.append(id.getLemma());
    	sb.append(" (");
    	sb.append(id.getPOS());
    	sb.append(") ");
        for (Iterator<IWordID> i = wordIDs.iterator(); i.hasNext(); ){
        	sb.append(i.next().toString());
        	if(i.hasNext())
        		sb.append(", ");
        }
        sb.append(']');
        return sb.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
		result = prime * result + id.hashCode();
		result = prime * result + tagSenseCount;
        result = prime * result + wordIDs.hashCode();
        result = prime * result + pointers.hashCode();
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if(this == obj) 
        	return true;
        if(obj == null) 
        	return false;
        if(!(obj instanceof IIndexWord)) 
        	return false;
        final IIndexWord other = (IndexWord) obj;
        if(!id.equals(other.getID())) 
        	return false;
        if(tagSenseCount != other.getTagSenseCount()) 
        	return false;
        if(!wordIDs.equals(other.getWordIDs())) 
        	return false;
        if(!pointers.equals(other.getPointers())) 
        	return false;
        return true;
    }
}
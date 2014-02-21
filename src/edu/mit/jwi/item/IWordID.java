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

/**
 * A unique identifier sufficient to retrieve a particular word from the Wordnet
 * database. Consists of a synset id, sense number, and lemma.
 * 
 * @author Mark A. Finlayson
 * @version 2.3.3
 * @since JWI 1.0
 */
public interface IWordID extends IHasPOS, IItemID<IWord> {

	/**
	 * Returns the synset id object associated with this word.
	 * 
	 * @return the synset id for this word; never <code>null</code>
	 * @since JWI 1.0
	 */
	public ISynsetID getSynsetID();

	/**
	 * Returns the word number, which is a number from 1 to 255 that indicates
	 * the order this word is listed in the Wordnet data files. If the word
	 * number has not been specified, will return -1. If this method returns -1,
	 * the {@link #getLemma()} method will return a non-<code>null</code>,
	 * non-empty string, non-whitespace string.
	 * 
	 * @return an integer between 1 and 255, inclusive; or -1 if the word number
	 *         has not been specified.
	 * @since JWI 1.0
	 */
	public int getWordNumber();

	/**
	 * Returns the lemma (word root) associated with this word. If this word id
	 * does not have a lemma specified (it was underspecified when constructed),
	 * this method will return <code>null</code>.  If this method returns <code>null</code>,
	 * the {@link #getWordNumber()} method will return a positive number.
	 * 
	 * @return the lemma (word root) associated with this word. May return
	 *         <code>null</code> if the lemma has not been specified.
	 * @since JWI 1.0
	 */
	public String getLemma();
}

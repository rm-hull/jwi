/********************************************************************************
 * Java Wordnet Interface Library (JWI) v2.4.0
 * Copyright (c) 2007-2015 Mark A. Finlayson
 *
 * JWI is distributed under the terms of the Creative Commons Attribution 4.0 
 * International Public License, which means it may be freely used for all 
 * purposes, as long as proper acknowledgment is made.  See the license file 
 * included with this distribution for more details.
 *******************************************************************************/

package edu.mit.jwi.item;

import java.io.Serializable;

/**
 * A Wordnet sense entry object, represented in the Wordnet files as a line in the
 * sense entry.
 * 
 * @author Mark A. Finlayson
 * @version 2.4.0
 * @since JWI 2.1.0
 */
public interface ISenseEntry extends IHasPOS, Serializable {

	/**
	 * Returns the sense key for this sense entry.  Will not return <code>null</code>.
	 *
	 * @return The non-<code>null</code> sense key for this sense entry.
	 * @since JWI 2.1.0
	 */
	public ISenseKey getSenseKey();

	/**
	 * Returns the synset offset for this sense entry, a non-negative integer.
	 * 
	 * @return the non-negative synset offset for this entry
	 * @since JWI 2.1.0
	 */
	public int getOffset();

	/**
	 * Returns the sense number for the word indicated by this entry. A sense
	 * number is a positive integer.
	 * 
	 * @return the non-negative sense number for the word indicated by this entry.
	 * @since JWI 2.1.0
	 */
	public int getSenseNumber();

	/**
	 * Returns the tag count for the sense entry. A tag count is a non-negative
	 * integer that represents the number of times the sense is tagged in
	 * various semantic concordance texts. A count of 0 indicates that the sense
	 * has not been semantically tagged.
	 * 
	 * @return the non-negative tag count for this entry
	 * @since JWI 2.1.0
	 */
	public int getTagCount();

}

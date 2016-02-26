/********************************************************************************
 * Java Wordnet Interface Library (JWI) v2.4.0
 * Copyright (c) 2007-2015 Mark A. Finlayson
 *
 * JWI is distributed under the terms of the Creative Commons Attribution 4.0 
 * International Public License, which means it may be freely used for all 
 * purposes, as long as proper acknowledgment is made.  See the license file 
 * included with this distribution for more details.
 *******************************************************************************/

package edu.mit.jwi.data.compare;

import java.util.Comparator;

/**
 * A string comparator that may have an associated comment detector. The
 * <code>compare</code> method of this class will throw an
 * {@link IllegalArgumentException} if the line data passed to that method is
 * ill-formed.
 * 
 * @author Mark A. Finlayson
 * @version 2.4.0
 * @since JWI 2.0.0
 */
public interface ILineComparator extends Comparator<String> {

	/**
	 * Returns the comment detector instance associated with this line
	 * comparator, or <code>null</code> if one does not exist.
	 * 
	 * @return the comment detector associated with this line comparator, or
	 *         <code>null</code> if there is none
	 * @since JWI 2.0.0
	 */
	public ICommentDetector getCommentDetector();

}
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

/**
 * An object that potentially has an associated version.
 * 
 * @author Mark A. Finlayson
 * @version 2.4.0
 * @since JWI 2.1.0
 */
public interface IHasVersion {

	/**
	 * Returns the associated version for this object. If this object is not
	 * associated with any particular version, this method may return
	 * <code>null</code>.
	 * 
	 * @return The associated version, or <code>null</code> if none.
	 * @since JWI 2.1.0
	 */
	public IVersion getVersion();

}

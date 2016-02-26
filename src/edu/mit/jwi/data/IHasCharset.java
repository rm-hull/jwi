/********************************************************************************
 * Java Wordnet Interface Library (JWI) v2.4.0
 * Copyright (c) 2007-2015 Mark A. Finlayson
 *
 * JWI is distributed under the terms of the Creative Commons Attribution 4.0 
 * International Public License, which means it may be freely used for all 
 * purposes, as long as proper acknowledgment is made.  See the license file 
 * included with this distribution for more details.
 *******************************************************************************/

package edu.mit.jwi.data;

import java.nio.charset.Charset;


/** 
 * Classes implementing this interface have an associated Charset.
 *
 * @author Mark A. Finlayson
 * @version 2.4.0
 * @since JWI 2.3.4
 */
public interface IHasCharset {
	
	/** 
	 * Returns the character set associated with this object.  May be <code>null</code>.
	 *
	 * @return the Charset associated this object, possibly <code>null</code>
	 * @since JWI 2.3.4
	 */
	public Charset getCharset();

}

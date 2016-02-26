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
 * A verb frame as specified from the verb frames data file in the Wordnet
 * distribution
 * 
 * @author Mark A. Finlayson
 * @version 2.4.0
 * @since JWI 1.0
 */
public interface IVerbFrame extends Serializable {

	/**
	 * The id number of this verb frame. Should always return 1 or greater.
	 *
	 * @return the id number of the verb frame, will be positive
	 * @since JWI 1.0
	 */
	public int getNumber();

	/**
	 * The string form of the template, drawn directly from the data file.
	 * Will never return <code>null</code>
	 * @return the non-<code>null</code>, non-empty template of the verb frame
	 * @since JWI 1.0
	 */
	public String getTemplate();

	/**
	 * Takes the supplied surface form of a verb and instantiates it into the
	 * template for the verb frame. This is a convenience method; the method
	 * does no morphological processing; it does not check to see if the passed
	 * in word is actually a verb.
	 * 
	 * @param verb
	 *            the string to be substituted into the template
	 * @return the instantiated template
	 * @throws NullPointerException if the specified string is <code>null</code>
	 * @since JWI 1.0
	 */
	public String instantiateTemplate(String verb);

}
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
 * An item is an object with an ID.
 * 
 * @param <T>
 *            the type of the item id object
 * @author Mark A. Finlayson
 * @version 2.4.0
 * @since JWI 2.0.0
 */
public interface IItem<T extends IItemID<?>> extends Serializable {

	/**
	 * Returns the ID object for this item. Will not return <code>null</code>.
	 * 
	 * @return the non-<code>null</code> ID for this item
	 * @since JWI 2.0.0
	 */
	public T getID();

}
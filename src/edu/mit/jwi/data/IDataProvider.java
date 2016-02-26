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

import java.net.URL;
import java.nio.charset.Charset;
import java.util.Set;

import edu.mit.jwi.item.IHasVersion;
import edu.mit.jwi.item.POS;

/**
 * Objects that implement this interface manage access to data source objects.
 * Before the provider can be used, a client must call {@link #setSource(URL)}
 * (or call the appropriate constructor) followed by {@link #open()}.  Otherwise,
 * the provider will throw an exception.
 * 
 * @author Mark A. Finlayson
 * @version 2.4.0
 * @since JWI 1.0
 */
public interface IDataProvider extends IHasVersion, IHasLifecycle, IHasCharset {

	/**
	 * This method is used to set the source URL from which the provider
	 * accesses the data from which it instantiates data sources. The data at
	 * the specified location may be in an implementation-specific format. If
	 * the provider is currently open, this method throws an
	 * {@code IllegalStateException}.
	 * 
	 * @param url
	 *            the location of the data, may not be <code>null</code>
	 * @throws IllegalStateException
	 *             if the provider is currently open
	 * @throws NullPointerException
	 *             if the specified <code>URL</code> is <code>null</code>.
	 * @since JWI 1.0
	 */
	public void setSource(URL url);

	/**
	 * Returns the <code>URL</code> that points to the resource location; should
	 * never return <code>null</code>.
	 * 
	 * @return the<code>URL</code> that points to the resource location; must
	 *         not be <code>null</code>
	 * @since JWI 1.0
	 */
	public URL getSource();
	
	/**
	 * Sets the character set associated with this dictionary. The character set
	 * may be <code>null</code>.
	 * 
	 * @param charset
	 *            the possibly <code>null</code> character set to use when
	 *            decoding files.
	 * @throws IllegalStateException
	 *             if the provider is currently open
	 * @since JWI 2.3.4
	 */
	public void setCharset(Charset charset);
	
	/**
	 * Returns a set containing all the content types this provider looks for at
	 * the resource location. The returned collection may be unmodifiable, or
	 * may be a copy of an internal array; in any event modification of the
	 * returned collection should not affect the set of types used by the
	 * provider.
	 * 
	 * @return a non-<code>null</code>, non-empty set of content types for this
	 *         provider
	 * @since JWI 2.2.0
	 */
	public Set<? extends IContentType<?>> getTypes();
	
	/**
	 * Returns the first content type, if any, that matches the specified data
	 * type and pos object. Either parameter may be <code>null</code>.
	 * 
	 * @param dt
	 *            the data type, possibly <code>null</code>, of the desired
	 *            content type
	 * @param pos
	 *            the part of speech, possibly <code>null</code>, of the desired
	 *            content type
	 * @return the first content type that matches the specified data type and
	 *         part of speech.
	 * @since JWI 2.3.4
	 */
	public <T> IContentType<T> resolveContentType(IDataType<T> dt, POS pos);

	/**
	 * Returns a data source object for the specified content type, if one is
	 * available; otherwise returns <code>null</code>.
	 * 
	 * @param <T>
	 *            the content type of the data source
	 * @param type
	 *            the content type of the data source to be retrieved
	 * @return the data source for the specified content type, or
	 *         <code>null</code> if this provider has no such data source
	 * @throws NullPointerException
	 *             if the type is <code>null</code>
	 * @throws ObjectClosedException
	 *             if the provider is not open when this call is made
	 * @since JWI 2.0.0
	 */
	public <T> IDataSource<T> getSource(IContentType<T> type);

}
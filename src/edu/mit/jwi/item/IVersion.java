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
 * A Wordnet version.
 * 
 * @author Mark A. Finlayson
 * @version 2.4.0
 * @since JWI 2.1.0
 */
public interface IVersion extends Serializable {
	
	/** 
	 * Returns the major version number, i.e., the '1' in '1.7.2'.
	 *
	 * @return the major version number, never negative
	 * @since JWI 2.1.0
	 */
	public int getMajorVersion();
	
	/** 
	 * Returns the minor version number, i.e., the '7' in '1.7.2'.
	 *
	 * @return the minor version number, never negative
	 * @since JWI 2.1.0
	 */
	public int getMinorVersion();

	/** 
	 * Returns the bugfix version number, i.e., the '2' in '1.7.2'.
	 *
	 * @return the bugfix version number, never negative
	 * @since JWI 2.1.0
	 */
	public int getBugfixVersion();
	
	/**
	 * Returns the version qualifier, i.e., the 'abc' in '1.7.2.abc'. The
	 * qualifer is never <code>null</code>, but may be empty.
	 * 
	 * @return the version qualifier, non-<code>null</code>, potentially empty
	 * @since JWI 2.2.0
	 */
	public String getQualifier();

	/**
	 * A dummy version object used to indicate that the version has been
	 * calculated, and determined to be <code>null</code>.
	 * 
	 * @since JWI 2.2.0
	 */
	public static final IVersion NO_VERSION = new IVersion(){

		/**
		 * This serial version UID identifies the last version of JWI whose
		 * serialized instances of the NO_VERSION instance are compatible with this
		 * implementation.
		 * 
		 * @since JWI 2.4.0
		 */
		private static final long serialVersionUID = 240;

		/* 
		 * (non-Javadoc) 
		 *
		 * @see edu.mit.jwi.item.IVersion#getBugfixVersion()
		 */
		public int getBugfixVersion() {
			throw new UnsupportedOperationException();
		}

		/* 
		 * (non-Javadoc) 
		 *
		 * @see edu.mit.jwi.item.IVersion#getMajorVersion()
		 */
		public int getMajorVersion() {
			throw new UnsupportedOperationException();
		}

		/* 
		 * (non-Javadoc) 
		 *
		 * @see edu.mit.jwi.item.IVersion#getMinorVersion()
		 */
		public int getMinorVersion() {
			throw new UnsupportedOperationException();
		}

		/* 
		 * (non-Javadoc) 
		 *
		 * @see edu.mit.jwi.item.IVersion#getQualifier()
		 */
		public String getQualifier() {
			throw new UnsupportedOperationException();
		}
		
		/**
		 * Deserialization implementation. When deserializing this object, make
		 * sure to return the singleton object.
		 *
		 * @return the singleton dummy version object.
		 * @since JWI 2.4.0
		 */
		protected Object readResolve(){
			return IVersion.NO_VERSION;
		}
		
	};
	
}

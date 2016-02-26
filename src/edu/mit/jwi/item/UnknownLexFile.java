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

import java.util.HashMap;
import java.util.Map;

/**
 * Represents 'unknown' lexical files. This class implements internal caching,
 * much like the {@link Integer} class. Clients should use the static
 * {@link #getUnknownLexicalFile(int)} method to retrieve instances of this
 * class.
 * 
 * @author Mark A. Finlayson
 * @version 2.4.0
 * @since JWI 2.1.4
 */
public class UnknownLexFile extends LexFile {
	
	/**
	 * This serial version UID identifies the last version of JWI whose
	 * serialized instances of the UnknownLexFile class are compatible with this
	 * implementation.
	 * 
	 * @since JWI 2.4.0
	 */
	private static final long serialVersionUID = 240;
	
	// cache for unknown lexical file objects.
	private static Map<Integer, UnknownLexFile> lexFileMap = new HashMap<Integer, UnknownLexFile>();

	/**
	 * Obtain instances of this class via the static
	 * {@link #getUnknownLexicalFile(int)} method. This constructor is marked
	 * protected so that the class may be sub-classed, but not directly
	 * instantiated.
	 * 
	 * @param num
	 *            the number of the lexcial file
	 * @since JWI 2.1.4
	 */
	protected UnknownLexFile(int num) {
		super(num, "Unknown", "Unknown Lexical File", null);
	}

	/**
	 * Allows retrieval of an unknown lexical file object given the number.
	 * 
	 * @return UnknownLexFile the unknown lexical file object corresponding to
	 *         the specified number
	 * @throws IllegalArgumentException
	 *             if the specified integer is not a valid lexical file number
	 * @since JWI 2.1.4
	 */
    public static UnknownLexFile getUnknownLexicalFile(int num) {
    	checkLexicalFileNumber(num);
    	UnknownLexFile result =  lexFileMap.get(num);
    	if(result == null){
    		result = new UnknownLexFile(num);
    		lexFileMap.put(num, result);
    	}
    	return result;
    }

}

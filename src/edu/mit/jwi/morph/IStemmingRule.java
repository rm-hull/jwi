/********************************************************************************
 * MIT Java Wordnet Interface Library (JWI) v2.3.3
 * Copyright (c) 2007-2014 Massachusetts Institute of Technology
 *
 * JWI is distributed under the terms of the Creative Commons Attribution 3.0 
 * Unported License, which means it may be freely used for all purposes, as long 
 * as proper acknowledgment is made.  See the license file included with this
 * distribution for more details.
 *******************************************************************************/

package edu.mit.jwi.morph;

import java.util.Set;

import edu.mit.jwi.item.IHasPOS;

/**
 * A rule for deriving a stem (a.k.a., root or lemma) from a word.
 * 
 * @author Mark A. Finlayson
 * @version 2.3.3
 * @since JWI 2.3.1
 */
public interface IStemmingRule extends IHasPOS {

	/**
	 * Returns the suffix for this rule. Will never return <code>null</code>,
	 * empty, or all whitespace.
	 * 
	 * @return the suffix for this rule.
	 * @since JWI 2.3.1
	 */
	public String getSuffix();

	/**
	 * Returns the ending for this rule. Will never return <code>null</code> ,
	 * empty, or all whitespace.
	 * 
	 * @return the ending for this rule.
	 * @since JWI 2.3.1
	 */
	public String getEnding();

	/**
	 * Returns the set of suffixes that should be ignored when applying this
	 * stemming rule. This method will never return <code>null</code>, but it
	 * may return an empty set. The ignore set will not include the string
	 * returned by {@link #getSuffix()}.
	 * 
	 * @return a non-<code>null</code> but possibly empty set of suffixes to be
	 *         ignored
	 * @since JWI 2.3.1
	 */
	public Set<String> getSuffixIgnoreSet();

	/**
	 * Applies this rule to the given word. The word should not be
	 * <code>null</code>, but may be empty. If the rule cannot be applied to the
	 * word, this method returns <code>null</code>. This call is equivalent to
	 * calling {@link #apply(String, String)} with <code>null</code> as the
	 * second argument
	 * 
	 * @param word
	 *            the word to which the stemming rule should be applied.
	 * @return the root of the word, or <code>null</code> if the rule cannot be
	 *         applied to this word
	 * @since JWI 2.3.1
	 */
	public String apply(String word);

	/**
	 * TODO: Write Comment
	 * 
	 * @param word
	 *            the word to which the stemming rule should be applied.
	 * @param suffix
	 *            a suffix that should be appended to the root once it has been
	 *            derived; may be <code>null</code>.
	 * @return the root of the word, or <code>null</code> if the rule cannot be
	 *         applied to this word
	 * @since JWI 2.3.1
	 */
	public String apply(String word, String suffix);

}

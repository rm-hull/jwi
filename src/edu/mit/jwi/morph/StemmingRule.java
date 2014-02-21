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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.mit.jwi.item.POS;

	/** 
	 * Default implementation of the {@link IStemmingRule} interface.
	 *
	 * @author Mark A. Finlayson
	 * @version 2.3.3
	 * @since JWI 2.3.1
	 */
	public class StemmingRule implements IStemmingRule {
		
		private final POS pos;
		private final String suffix;
		private final String ending;
		private final Set<String> ignoreSet;
		
		/**
		 * Creates a new stemming rule with the specified suffix, ending, and
		 * avoid set
		 * 
		 * @param suffix
		 *            the suffix that should be stripped from a word; should not
		 *            be <code>null</code>, empty, or all whitespace.
		 * @param ending
		 *            the ending that should be stripped from a word; should not
		 *            be <code>null</code>, but may be empty or all whitespace.
		 * @param pos
		 *            the part of speech to which this rule applies, may not be
		 *            <code>null</code>
		 * @param ignore
		 *            the set of suffixes that, when present, indicate this rule
		 *            should not be applied. May be null or empty, but not
		 *            contain nulls or empties.
		 * @throws NullPointerException
		 *             if the suffix, ending, or pos are null, or the ignore set
		 *             contains null
		 * @throws NullPointerException
		 *             if the suffix is empty or all whitespace, or the ignore
		 *             set contains a string which is empty or all whitespace
		 * @since JWI 2.3.1
		 */
		public StemmingRule(String suffix, String ending, POS pos, String... ignore){
			if(suffix == null)
				throw new NullPointerException();
			if(ending == null)
				throw new NullPointerException();
			if(pos == null)
				throw new NullPointerException();
			
			// allocate avoid set
			Set<String> ignoreSet = null;
			if(ignore != null && ignore.length > 0){
				ignoreSet = new HashSet<String>(ignore.length);
				for(String avoidStr : ignore){
					if(avoidStr == null)
						throw new NullPointerException();
					avoidStr = avoidStr.trim();
					if(avoidStr.length() == 0)
						throw new IllegalArgumentException();
					ignoreSet.add(avoidStr);
				}
				ignoreSet = Collections.unmodifiableSet(ignoreSet);
			} else {
				ignoreSet = Collections.emptySet();
			}
			
			
			suffix = suffix.trim();
			ending = ending.trim();
			if(suffix.length() == 0)
				throw new IllegalArgumentException();
			
			if(ignoreSet.contains(suffix))
				throw new IllegalArgumentException();
			
			this.pos = pos;
			this.suffix = suffix;
			this.ending = ending;
			this.ignoreSet = ignoreSet;
			
		}
		
		/* 
		 * (non-Javadoc) 
		 *
		 * @see edu.mit.jwi.morph.IStemmingRule#getSuffix()
		 */
		public String getSuffix(){
			return suffix;
		}
		
		/* 
		 * (non-Javadoc) 
		 *
		 * @see edu.mit.jwi.morph.IStemmingRule#getEnding()
		 */
		public String getEnding(){
			return ending;
		}

		/* 
		 * (non-Javadoc) 
		 *
		 * @see edu.mit.jwi.morph.IStemmingRule#getSuffixIgnoreSet()
		 */
		public Set<String> getSuffixIgnoreSet(){
			return ignoreSet;
		}

		/* 
		 * (non-Javadoc) 
		 *
		 * @see edu.mit.jwi.item.IHasPOS#getPOS()
		 */
		public POS getPOS() {
			return pos;
		}
		
		/* 
		 * (non-Javadoc) 
		 *
		 * @see edu.mit.jwi.morph.IStemmingRule#apply(java.lang.String)
		 */
		public String apply(String word){
			return apply(word, null);
		}
		
		/* 
		 * (non-Javadoc) 
		 *
		 * @see edu.mit.jwi.morph.IStemmingRule#apply(java.lang.String, java.lang.String)
		 */
		public String apply(String word, String suffix){
			
			// see if the suffix is present
			if(!word.endsWith(getSuffix()))
				return null;
			
			// process ignore set
			for(String ignoreSuffix : getSuffixIgnoreSet())
				if(word.endsWith(ignoreSuffix))
					return null;
			
			// apply the rule
			// we loop directly over characters here to avoid two loops
			StringBuilder sb = new StringBuilder();
			int len = word.length()-getSuffix().length();
			for (int i = 0; i < len; i++) 
				sb.append(word.charAt(i));
			sb.append(getEnding());
			
			// append optional suffix
			if(suffix != null)
				sb.append(suffix.trim());
			
			return sb.toString();
		}

}
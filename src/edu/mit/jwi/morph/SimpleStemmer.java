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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import edu.mit.jwi.item.POS;

/**
 * Provides simple a simple pattern-based stemming facility based on the "Rules
 * of Detachment" as described in the {@code morphy} man page in the Wordnet
 * distribution, which can be found at <a
 * href="http://wordnet.princeton.edu/man/morphy.7WN.html">
 * http://wordnet.princeton.edu/man/morphy.7WN.html</a> It also attempts to
 * strip "ful" endings. It does not search Wordnet to see if stems actually
 * exist. In particular, quoting from that man page:
 * <p>
 * <h3>Rules of Detachment</h3>
 * <p>
 * The following table shows the rules of detachment used by Morphy. If a word
 * ends with one of the suffixes, it is stripped from the word and the
 * corresponding ending is added. ... No rules are applicable to adverbs.
 * <p>
 * POS Suffix Ending<br>
 * <ul>
 * <li>NOUN "s" ""
 * <li>NOUN "ses" "s"
 * <li>NOUN "xes" "x"
 * <li>NOUN "zes" "z"
 * <li>NOUN "ches" "ch"
 * <li>NOUN "shes" "sh"
 * <li>NOUN "men" "man"
 * <li>NOUN "ies" "y"
 * <li>VERB "s" ""
 * <li>VERB "ies" "y"
 * <li>VERB "es" "e"
 * <li>VERB "es" ""
 * <li>VERB "ed" "e"
 * <li>VERB "ed" ""
 * <li>VERB "ing" "e"
 * <li>VERB "ing" ""
 * <li>ADJ "er" ""
 * <li>ADJ "est" ""
 * <li>ADJ "er" "e"
 * <li>ADJ "est" "e"
 * </ul>
 * <p>
 * <h3>Special Processing for nouns ending with 'ful'</h3>
 * <p>
 * Morphy contains code that searches for nouns ending with ful and performs a
 * transformation on the substring preceding it. It then appends 'ful' back
 * onto the resulting string and returns it. For example, if passed the nouns
 * "boxesful", it will return "boxful".
 * 
 * @author Mark A. Finlayson
 * @version 2.3.3
 * @since JWI 1.0
 */
public class SimpleStemmer implements IStemmer {

	public static final String underscore = "_";
	Pattern whitespace = Pattern.compile("\\s+");

	public static final String SUFFIX_ches = "ches";
	public static final String SUFFIX_ed = "ed";
	public static final String SUFFIX_es = "es";
	public static final String SUFFIX_est = "est";
	public static final String SUFFIX_er = "er";
	public static final String SUFFIX_ful = "ful";
	public static final String SUFFIX_ies = "ies";
	public static final String SUFFIX_ing = "ing";
	public static final String SUFFIX_men = "men";
	public static final String SUFFIX_s = "s";
	public static final String SUFFIX_ss = "ss";
	public static final String SUFFIX_ses = "ses";
	public static final String SUFFIX_shes = "shes";
	public static final String SUFFIX_xes = "xes";
	public static final String SUFFIX_zes = "zes";

	public static final String ENDING_null = "";
	public static final String ENDING_ch = "ch";
	public static final String ENDING_e = "e";
	public static final String ENDING_man = "man";
	public static final String ENDING_s = SUFFIX_s;
	public static final String ENDING_sh = "sh";
	public static final String ENDING_x = "x";
	public static final String ENDING_y = "y";
	public static final String ENDING_z = "z";
	
	public static final Map<POS, List<StemmingRule>> ruleMap;
	
	static {
		Map<POS, List<StemmingRule>> ruleMapHidden = new TreeMap<POS,List<StemmingRule>>();
		
		List<StemmingRule> list;
		
		String[] nullSuffixArray = null;
		
		// nouns
		list = new ArrayList<StemmingRule>(8);
		list.add(new StemmingRule(SUFFIX_s,    ENDING_null, POS.NOUN, SUFFIX_ss));
		list.add(new StemmingRule(SUFFIX_ses,  ENDING_s,    POS.NOUN, nullSuffixArray)); 
		list.add(new StemmingRule(SUFFIX_xes,  ENDING_x,    POS.NOUN, nullSuffixArray));
		list.add(new StemmingRule(SUFFIX_zes,  ENDING_z,    POS.NOUN, nullSuffixArray)); 
		list.add(new StemmingRule(SUFFIX_ches, ENDING_ch,   POS.NOUN, nullSuffixArray));
		list.add(new StemmingRule(SUFFIX_shes, ENDING_sh,   POS.NOUN, nullSuffixArray)); 
		list.add(new StemmingRule(SUFFIX_men,  ENDING_man,  POS.NOUN, nullSuffixArray));
		list.add(new StemmingRule(SUFFIX_ies,  ENDING_y,    POS.NOUN, nullSuffixArray));
		ruleMapHidden.put(POS.NOUN, Collections.unmodifiableList(list));

		// verbs
		list = new ArrayList<StemmingRule>(8);
		list.add(new StemmingRule(SUFFIX_s,   ENDING_null, POS.VERB, nullSuffixArray));
		list.add(new StemmingRule(SUFFIX_ies, ENDING_y,    POS.VERB, nullSuffixArray)); 
		list.add(new StemmingRule(SUFFIX_es,  ENDING_e,    POS.VERB, nullSuffixArray));
		list.add(new StemmingRule(SUFFIX_es,  ENDING_null, POS.VERB, nullSuffixArray)); 
		list.add(new StemmingRule(SUFFIX_ed,  ENDING_e,    POS.VERB, nullSuffixArray));
		list.add(new StemmingRule(SUFFIX_ed,  ENDING_null, POS.VERB, nullSuffixArray)); 
		list.add(new StemmingRule(SUFFIX_ing, ENDING_e,    POS.VERB, nullSuffixArray));
		list.add(new StemmingRule(SUFFIX_ing, ENDING_null, POS.VERB, nullSuffixArray));
		ruleMapHidden.put(POS.VERB, Collections.unmodifiableList(list));
		
		// adjectives
		list = new ArrayList<StemmingRule>(4);
		list.add(new StemmingRule(SUFFIX_er,  ENDING_e,    POS.ADJECTIVE, nullSuffixArray));
		list.add(new StemmingRule(SUFFIX_er,  ENDING_null, POS.ADJECTIVE, nullSuffixArray)); 
		list.add(new StemmingRule(SUFFIX_est, ENDING_e,    POS.ADJECTIVE, nullSuffixArray));
		list.add(new StemmingRule(SUFFIX_est, ENDING_null, POS.ADJECTIVE, nullSuffixArray));
		ruleMapHidden.put(POS.ADJECTIVE, Collections.unmodifiableList(list));
		
		// adverbs
		ruleMapHidden.put(POS.ADVERB, Collections.<StemmingRule>emptyList());
		
		// assign
		ruleMap = Collections.unmodifiableMap(ruleMapHidden);
	}
	
	/**
	 * Returns a set of stemming rules used by this stemmer. Will not return a
	 * null map, but it may be empty. The lists in the map will also not be
	 * null, but may be empty.
	 * 
	 * @return the rule map for this stemmer
	 * @since JWI 3.5.1
	 */
	public Map<POS, List<StemmingRule>> getRuleMap(){
		return ruleMap;
	}
	
	/* 
	 * (non-Javadoc) 
	 *
	 * @see edu.mit.jwi.morph.IStemmer#findStems(java.lang.String, edu.mit.jwi.item.POS)
	 */
	public List<String> findStems(String word, POS pos) {
		
		word = normalize(word);
		
		// if pos is null, do all
		if(pos == null){
			Set<String> result = new LinkedHashSet<String>();
			for(POS p : POS.values())
				result.addAll(findStems(word, p));
			if(result.isEmpty())
				return Collections.emptyList();
			return new ArrayList<String>(result);
		}
		
		boolean isCollocation = word.contains(underscore);
		
		switch(pos){
		case NOUN:
			return isCollocation ? 
					getNounCollocationRoots(word) : 
						stripNounSuffix(word);
		case VERB:
			// BUG006: here we check for composites
			return isCollocation ? 
					getVerbCollocationRoots(word) : 
						stripVerbSuffix(word);
		case ADJECTIVE:
			return stripAdjectiveSuffix(word);
		case ADVERB:
			// nothing for adverbs
			return Collections.<String>emptyList();
		}
		
		throw new IllegalArgumentException("This should not happen");
	}
	
	/**
	 * Converts all whitespace runs to single underscores. Tests first to see if
	 * there is any whitespace before converting.
	 * 
	 * @param word
	 *            the string to be normalized
	 * @return a normalized string
	 * @throws NullPointerException
	 *             if the specified string is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if the specified string is empty or all whitespace
	 * @since JWI 2.1.1
	 */
	protected String normalize(String word) {
		
		// make lowercase
		word = word.toLowerCase();
		
		// replace all underscores with spaces
		word = word.replace('_', ' ');
		
		// trim off extra whitespace
		word = word.trim();
		if(word.length() == 0)
			throw new IllegalArgumentException();
		
		// replace all whitespace with underscores
		word = whitespace.matcher(word).replaceAll(underscore); 

		// return normalized word
		return word;
	}

	/**
	 * Strips suffixes from the specified word according to the noun rules.
	 * 
	 * @param noun
	 *            the word to be modified
	 * @return a list of modified forms that were constructed, or the empty list
	 *         if none
	 * @throws NullPointerException
	 *             if the specified word is <code>null</code>
	 * @since JWI 1.0
	 */
	protected List<String> stripNounSuffix(final String noun) {
		
		if(noun.length() <= 2)
			return Collections.<String>emptyList();
		
		// strip off "ful"
		String word = noun;
		String suffix = null;
		if(noun.endsWith(SUFFIX_ful)) {
			word = noun.substring(0, noun.length()-SUFFIX_ful.length());
			suffix = SUFFIX_ful;
		}
		
		// we will return this to the caller
		Set<String> result = new LinkedHashSet<String>();
		
		// apply the rules
		String root;
		for (StemmingRule rule : getRuleMap().get(POS.NOUN)) {
			root = rule.apply(word, suffix);
			if(root != null && root.length() > 0)
				result.add(root);
		}
		
		return result.isEmpty() ? 
				Collections.<String>emptyList() : 
					new ArrayList<String>(result);
		
	}
	
	/**
	 * Handles stemming noun collocations.
	 * 
	 * @param composite
	 *            the word to be modified
	 * @return a list of modified forms that were constructed, or the empty list
	 *         if none
	 * @throws NullPointerException
	 *             if the specified word is <code>null</code>
	 * @since JWI 1.1.1
	 */
	protected List<String> getNounCollocationRoots(String composite){
		
		// split into parts
		String[] parts = composite.split(underscore);
		if(parts.length < 2) 
			return Collections.emptyList();
		
		// stem each part
		List<List<String>> rootSets = new ArrayList<List<String>>(parts.length);
		for(int i = 0; i < parts.length; i++)
			rootSets.add(findStems(parts[i], POS.NOUN));
		
		// reassemble all combinations
		Set<StringBuffer> poss = new HashSet<StringBuffer>();
		
		// seed the set
		List<String> rootSet = rootSets.get(0);
		if(rootSet == null){
			poss.add(new StringBuffer(parts[0]));
		} else {
			for(Object root : rootSet) 
				poss.add(new StringBuffer((String)root));
		}
		
		// make all combinations
		StringBuffer newBuf;
		Set<StringBuffer> replace;
		for(int i = 1; i < rootSets.size(); i++){
			rootSet = rootSets.get(i);
			if(rootSet.isEmpty()){
				for(StringBuffer p : poss){
					p.append("_");
					p.append(parts[i]);
				}
			} else {
				replace = new HashSet<StringBuffer>();
				for(StringBuffer p : poss){
					for(Object root : rootSet){
						newBuf = new StringBuffer();
						newBuf.append(p.toString());
						newBuf.append("_");
						newBuf.append(root);
						replace.add(newBuf);
					}
				}
				poss.clear();
				poss.addAll(replace);
			}
			
		}
		
		if(poss.isEmpty()) 
			return Collections.<String>emptyList();
		
		// make sure to remove empties
		Set<String> result = new LinkedHashSet<String>();
		String root;
		for(StringBuffer p : poss){
			root = p.toString().trim();
			if(root.length() != 0)
				result.add(root);
		}
		
		return new ArrayList<String>(result);
	}

	/**
	 * Strips suffixes from the specified word according to the verb rules.
	 * 
	 * @param verb
	 *            the word to be modified
	 * @return a list of modified forms that were constructed, or the empty list
	 *         if none
	 * @throws NullPointerException
	 *             if the specified word is <code>null</code>
	 * @since JWI 1.0
	 */
	protected List<String> stripVerbSuffix(final String verb) {
		
		if(verb.length() <= 2)
			return Collections.<String>emptyList();
		
		// we will return this to the caller
		Set<String> result = new LinkedHashSet<String>();
		
		// apply the rules
		String root;
		for (StemmingRule rule : getRuleMap().get(POS.VERB)) {
			root = rule.apply(verb);
			if(root != null && root.length() > 0)
				result.add(root);
		}
		
		return result.isEmpty() ? 
				Collections.<String>emptyList() : 
					new ArrayList<String>(result);
	}
	
	/**
	 * Handles stemming verb collocations.
	 * 
	 * @param composite
	 *            the word to be modified
	 * @return a list of modified forms that were constructed, or an empty list
	 *         if none
	 * @throws NullPointerException
	 *             if the specified word is <code>null</code>
	 * @since JWI 1.1.1
	 */
	protected List<String> getVerbCollocationRoots(String composite){
		
		// split into parts
		String[] parts = composite.split(underscore);
		if(parts.length < 2) 
			return Collections.emptyList();
		
		// find the stems of each parts
		List<List<String>> rootSets = new ArrayList<List<String>>(parts.length);
		for(int i = 0; i < parts.length; i++)
			rootSets.add(findStems(parts[i], POS.VERB));
		
		Set<String> result = new LinkedHashSet<String>();
		
		// form all combinations
		StringBuffer rootBuffer = new StringBuffer();
		for(int i = 0; i < parts.length; i++){
			if(rootSets.get(i) == null) 
				continue;
			for(Object partRoot : rootSets.get(i)){
				rootBuffer.replace(0, rootBuffer.length(), "");
				
				for(int j = 0; j < parts.length; j++){
					if(j == i){
						rootBuffer.append((String)partRoot);
					} else {
						rootBuffer.append(parts[j]);
					}
					if(j < parts.length-1) 
						rootBuffer.append(underscore);
				}
				result.add(rootBuffer.toString());
			}
		}
		
		// remove any empties
		for(Iterator<String> i = result.iterator(); i.hasNext(); )
			if(i.next().length() == 0)
				i.remove();
		
		return result.isEmpty() ? 
				Collections.<String>emptyList() : 
					new ArrayList<String>(result);
	}

	/**
	 * Strips suffixes from the specified word according to the adjective rules.
	 * 
	 * @param adj
	 *            the word to be modified
	 * @return a list of modified forms that were constructed, or an empty list
	 *         if none
	 * @throws NullPointerException
	 *             if the specified word is <code>null</code>
	 * @since JWI 1.0
	 */
	protected List<String> stripAdjectiveSuffix(final String adj) {
		
		// we will return this to the caller
		Set<String> result = new LinkedHashSet<String>();
		
		// apply the rules
		String root;
		for (StemmingRule rule : getRuleMap().get(POS.ADJECTIVE)) {
			root = rule.apply(adj);
			if(root != null && root.length() > 0)
				result.add(root);
		}
		
		return result.isEmpty() ? 
				Collections.<String>emptyList() : 
					new ArrayList<String>(result);
	}
}
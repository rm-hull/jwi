/********************************************************************************
 * Java Wordnet Interface Library (JWI) v2.4.0
 * Copyright (c) 2007-2015 Mark A. Finlayson
 *
 * JWI is distributed under the terms of the Creative Commons Attribution 4.0 
 * International Public License, which means it may be freely used for all 
 * purposes, as long as proper acknowledgment is made.  See the license file 
 * included with this distribution for more details.
 *******************************************************************************/

package edu.mit.jwi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import edu.mit.jwi.data.ILoadPolicy;
import edu.mit.jwi.data.ILoadable;


/** 
 * Interface that governs dictionaries that can be completely loaded into memory.
 *
 * @author Mark A. Finlayson
 * @version 2.4.0
 * @since JWI 2.2.0
 */
public interface IRAMDictionary extends IDictionary, ILoadPolicy, ILoadable {

	/**
	 * Exports the in-memory contents of the to the specified output stream.
	 * This method flushes and closes the output stream when it is done writing
	 * the data.
	 *
	 * @param out
	 *            the output stream to which the in-memory data will be written;
	 *            may not be <code>null</code>
	 * @throws IOException
	 *             if there is a problem writing the in-memory data to the
	 *             output stream.
	 * @throws IllegalStateException
	 *             if the dictionary has not been loaded into memory
	 * @throws NullPointerException
	 *             if the output stream is <code>null</code>
	 * @since JWI 2.4.0
	 */
	void export(OutputStream out) throws IOException;
	
	/**
	 * An input stream factory is used by certain constructors of the
	 * {@link RAMDictionary} class to provide source data to load the dictionary
	 * into memory from a stream. Using this interface allows the dictionary to
	 * be closed and reopened again.  Therefore the expectation is that
	 * the {@link #makeInputStream()} method may be called multiple times
	 * without throwing an exception.
	 *
	 * @author Mark A. Finlayson
	 * @version 2.4.0
	 * @since JWI 2.4.0
	 */
	public interface IInputStreamFactory {
		
		/**
		 * Returns a new input stream from this factory.
		 *
		 * @return a new, unused input stream from this factory.
		 * @since JWI 2.4.0
		 */
		public InputStream makeInputStream() throws IOException;
		
	}
	
	/** 
	 * Default implementation of the {@link IInputStreamFactory} interface which
	 * creates an input stream from a specified File object.
	 *
	 * @author Mark A. Finlayson
	 * @version 2.4.0
	 * @since JWI 2.4.0
	 */
	public static class FileInputStreamFactory implements IInputStreamFactory {
		
		// instance fields
		protected final File file;
		
		/**
		 * Creates a FileInputStreamFactory that uses the specified file.
		 * 
		 * @param file
		 *            the file from which the input streams should be created;
		 *            may not be <code>null</code>
		 * @throws NullPointerException
		 *             if the specified file is <code>null</code>
		 * @since JWI 2.4.0
		 */
		public FileInputStreamFactory(File file){
			if(file == null)
				throw new NullPointerException();
			this.file = file;
		}
		

		/* 
		 * (non-Javadoc) 
		 *
		 * @see edu.mit.jwi.IRAMDictionary.IInputStreamFactory#makeInputStream()
		 */
		public InputStream makeInputStream() throws IOException {
			return new FileInputStream(file);
		}
		
	}
	
	
	/**
	 * Default implementation of the {@link IInputStreamFactory} interface which
	 * creates an input stream from a specified URL.
	 *
	 * @author Mark A. Finlayson
	 * @version 2.4.0
	 * @since JWI 2.4.0
	 */
	public static class URLInputStreamFactory implements IInputStreamFactory {
		
		// instance fields
		protected final URL url;
		
		/**
		 * Creates a URLInputStreamFactory that uses the specified url.
		 * 
		 * @param url
		 *            the url from which the input streams should be created;
		 *            may not be <code>null</code>
		 * @throws NullPointerException
		 *             if the specified url is <code>null</code>
		 * @since JWI 2.4.0
		 */
		public URLInputStreamFactory(URL url){
			if(url == null)
				throw new NullPointerException();
			this.url = url;
		}
		

		/* 
		 * (non-Javadoc) 
		 *
		 * @see edu.mit.jwi.IRAMDictionary.IInputStreamFactory#makeInputStream()
		 */
		public InputStream makeInputStream() throws IOException {
			return url.openStream();
		}
		
	}
	
	

}

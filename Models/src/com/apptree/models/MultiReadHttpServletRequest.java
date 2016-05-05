package com.apptree.models;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Map;

public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
	  private ByteArrayOutputStream cachedBytes;
	  private Map<String,String[]> paramMap;

    /**
     * Create a multi read servlet request
     * @param request The servlet request
     */
	  public MultiReadHttpServletRequest(HttpServletRequest request) {
	    super(request);
	    paramMap = super.getParameterMap();
	  }

    /**
     * Gets the URL parameter with name
     * @param name The name of the URL parameter
     * @return The parameter value
     */
	  @Override
	public String getParameter(String name) {
		String[] values;
		values = paramMap.get(name);
		if ( values != null && values.length > 0 ) {
			return values[0];	
		}
		return null;
	}

    /**
     * Gets the input stream
     * @return The servlet input stream
     * @throws IOException
     */
	  @Override
	  public ServletInputStream getInputStream() throws IOException {
	    if (cachedBytes == null)
	      cacheInputStream();

	      return new CachedServletInputStream();
	  }

    /**
     * Gets the buffered reader
     * @return The buffered reader
     * @throws IOException
     */
	  @Override
	  public BufferedReader getReader() throws IOException{
	    return new BufferedReader(new InputStreamReader(getInputStream()));
	  }

    /**
     * Cache the inputstream in order to read it multiple times. For
     * convenience, I use apache.commons IOUtils
     * @throws IOException
     */
	  private void cacheInputStream() throws IOException {
	    cachedBytes = new ByteArrayOutputStream();
	    IOUtils.copy(super.getInputStream(), cachedBytes);
	  }

    /**
     * An inputstream which reads the cached request body
     */
	  public class CachedServletInputStream extends ServletInputStream {
	    private ByteArrayInputStream input;

	    public CachedServletInputStream() {
	      /* create a new input stream from the cached request body */
	      input = new ByteArrayInputStream(cachedBytes.toByteArray());
	    }

          @Override
          public boolean isFinished() {
              return false;
          }

          @Override
          public boolean isReady() {
              return false;
          }

          @Override
          public void setReadListener(ReadListener readListener) {

          }

          @Override
	    public int read() throws IOException {
	      return input.read();
	    }
	  }
	}
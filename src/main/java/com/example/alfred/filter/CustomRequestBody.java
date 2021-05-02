package com.example.alfred.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * A wrapper to cache the data in the request body, enabling the request body to
 * be read multiple times.
 *
 */
public class CustomRequestBody extends HttpServletRequestWrapper {

	// variable to store the request body
	private final String body;

	/**
	 * constructor to get the request body into the body string
	 * 
	 * @param request
	 * @throws IOException
	 */
	public CustomRequestBody(HttpServletRequest request) throws IOException {
		super(request);
		body = request.getReader().lines().collect(Collectors.joining());
	}

	/**
	 * overrides the getInputStream method to return the stream that reads from the
	 * body string
	 */
	@Override
	public ServletInputStream getInputStream() throws IOException {

		// creates a stream from the body
		final ByteArrayInputStream stream = new ByteArrayInputStream(body.getBytes());

		/**
		 * input stream to read from the body string
		 */
		ServletInputStream servletInputStream = new ServletInputStream() {

			/**
			 * returns the next byte of data from the input stream
			 */
			public int read() throws IOException {
				return stream.read();
			}

			/**
			 * auto-generated
			 */
			@Override
			public boolean isFinished() {
				return false;
			}

			/**
			 * auto-generated
			 */
			@Override
			public boolean isReady() {
				return false;
			}

			/**
			 * auto-generated
			 */
			@Override
			public void setReadListener(ReadListener listener) {
			}

		};
		return servletInputStream;
	}

	/**
	 * @return the request body string
	 */
	public String getBody() {
		return body;

	}
}
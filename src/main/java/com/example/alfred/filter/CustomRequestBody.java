package com.example.alfred.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CustomRequestBody extends HttpServletRequestWrapper {
	private final String body;

	public CustomRequestBody(HttpServletRequest request) throws IOException {
		super(request);
		body = request.getReader().lines().collect(Collectors.joining());
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream stream = new ByteArrayInputStream(body.getBytes());
		ServletInputStream servletInputStream = new ServletInputStream() {
			public int read() throws IOException {
				return stream.read();
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
			public void setReadListener(ReadListener listener) {
			}
		};
		return servletInputStream;
	}

	public String getBody() {
		return body;

	}
}
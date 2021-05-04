package com.example.alfred;

import com.example.alfred.filter.AlfredFilter;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.mock;

public class FilterTest {


    @Test
    public void testDoFilter() throws IOException, ServletException {
       //to be completed
        AlfredFilter alfredFilter = new AlfredFilter();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        System.out.println(httpServletRequest);

    }
}

package com.example.alfred;

import com.example.alfred.aop.AlfredAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AspectTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    private AlfredAspect testAspect = new AlfredAspect();
    @Test
    public void testLogs() throws Throwable{
        testAspect.around(proceedingJoinPoint);
        // to be completed
        verify(proceedingJoinPoint.proceed(),times(1));
    }
    //
}

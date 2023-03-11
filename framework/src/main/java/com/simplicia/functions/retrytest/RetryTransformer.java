package com.simplicia.functions.retrytest;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * This class implements IAnnotationTransformer interface and overriding the transform method and setting the
 * retry analyzer to TCRetryTest if 'IRetryAnalyzer' is null.
 *
 */
public class RetryTransformer implements IAnnotationTransformer {

    /**
     * Method to set Retry Analyzer as TCRetryTest and transform IRetryAnalyzer to IAnnotationTransformer so that we can use it with surefire plugin
     *
     * @param iTestAnnotation annotation
     * @param aClass          test class name
     * @param constructor     test Constructor
     * @param method          test method
     */
    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
        iTestAnnotation.setRetryAnalyzer(RetryTest.class);
    }
}
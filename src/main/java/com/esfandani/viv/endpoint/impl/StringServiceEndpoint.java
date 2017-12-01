package com.esfandani.viv.endpoint.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esfandani.viv.endpoint.ServiceEndpoint;
import com.sun.jdi.InvocationException;

/**
 *  A simple ServiceEndpoint for testing
 */
public class StringServiceEndpoint implements ServiceEndpoint<String> {

    private final static Logger log = LoggerFactory.getLogger(ServiceEndpointResource.class);

    private static Set<String> supportedParameters = new HashSet<>(Arrays.asList("category","country","price"));
    @Override
    public int getMaxConcurrentInvocations() {
        return 1;
    }

    @Override
    public Set<String> supportedParameter() {
        return supportedParameters;
    }

    @Override
    public List<String> invoke(Map<String, Object> parameters) throws InvocationException {
        log.info("going to sleep for StringServiceEndpoint for 1.5 seconds");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            log.error("had an exception during simulation for long call in StringServiceEndpoint",e);
        }
        log.info("waking up from sleep StringServiceEndpoint");
        return Arrays.asList("reza","esfandani");
    }
}

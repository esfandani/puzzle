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
 * A simple ServiceEndpoint for testing
 */
public class IntegerServiceEndpoint implements ServiceEndpoint<Integer> {

    private final static Logger log = LoggerFactory.getLogger(ServiceEndpointResource.class);

    private static Set<String> supportedParameters = new HashSet<>(Arrays.asList("price"));

    @Override
    public int getMaxConcurrentInvocations() {
        return 2;
    }

    @Override
    public Set<String> supportedParameter() {
        return supportedParameters;
    }

    /**
     * mimicking a remote call
     * @param parameters
     * @return
     * @throws InvocationException
     */
    @Override
    public List<Integer> invoke(Map<String, Object> parameters) throws InvocationException {
        try {
            log.info("going to sleep for IntegerServiceEndpoint for 1 seconds");
            Thread.sleep(1000);
            log.info("waking up from sleep integerServiceEndpoint");
        } catch (InterruptedException e) {
            log.error("have an exception during simulation for long call in IntegerServiceEndpoint", e);
        }
        return Arrays.asList(1, 2);
    }
}

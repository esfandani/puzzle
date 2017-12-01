package com.esfandani.viv.endpoint.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esfandani.viv.endpoint.ServiceEndpoint;
import com.esfandani.viv.endpoint.util.EndpointUtils;

/**
 * A wrapper class for {@link ServiceEndpoint} that limits the number of invokation that client can do for the internal service.
 */
public class ServiceEndpointResource<T> implements ServiceEndpoint<T> {

    private final static Logger log = LoggerFactory.getLogger(ServiceEndpointResource.class);

    private Semaphore lock = null;
    private ServiceEndpoint<T> service = null;
    private long id = -1;

    /**
     * @param service internal service that this class wraps it.
     * @param id the id assigns to this service.
     */
    public ServiceEndpointResource(ServiceEndpoint<T> service, long id) {
        Objects.requireNonNull(service);
        if (id < 0) {
            throw new RuntimeException(String.format("Id can't be less than 0; id is %d", id));
        }
        this.service = service;
        if (service.getMaxConcurrentInvocations() > -1) {
            lock = new Semaphore(service.getMaxConcurrentInvocations());
        }
        this.id = id;
    }

    @Override
    public int getMaxConcurrentInvocations() {
        return service.getMaxConcurrentInvocations();
    }

    @Override
    public Set<String> supportedParameter() {
        return service.supportedParameter();
    }

    @Override
    public List<T> invoke(Map<String, Object> parameters) {
        return invokeService(parameters);
    }

    /**
     *
     * @param params
     * @return list of values which returs by calling internal service if conditions are met otherwise it returns empty list.
     */
    private List<T> invokeService(Map<String, Object> params) {
        List ret = null;
        log.info(String.format("invoking service %s %d", service.getClass().getSimpleName(), id));
        try {
            acquireSemaphore();
            if (EndpointUtils.isSubset(service.supportedParameter(), params.keySet())) {
                ret = service.invoke(params);
            } else {
                ret = Collections.emptyList();
            }
        } catch (Exception e) {
            log.error(String.format("an exception happened during invoking the service %s",
                    service.getClass().getSimpleName()), e);
        } finally {
            releaseSemaphore();
            log.info(String.format("finished service %s %d", service.getClass().getSimpleName(), id));
        }

        return ret;
    }

    private void acquireSemaphore() throws InterruptedException {
        if (lock == null) {
            return;
        }
        log.info(String.format("acquiring lock for service:%s", service.getClass().getSimpleName()));
        lock.acquire();
        log.info(String.format("lock acquired for service:%s. the number of available permit is %d",
                service.getClass().getSimpleName(), lock.availablePermits()));
    }

    private void releaseSemaphore() {
        if (lock == null) {
            return;
        }
        log.info(String.format("releasing lock for service:%s", service.getClass().getSimpleName()));
        lock.release();
        log.info(String.format("lock released for service:%s. the number of available permit is %d",
                service.getClass().getSimpleName(), lock.availablePermits()));
    }

}

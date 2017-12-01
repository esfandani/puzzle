package com.esfandani.viv.endpoint.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.esfandani.viv.endpoint.ServiceEndpoint;
import com.esfandani.viv.endpoint.ServiceEndpointManager;
import com.esfandani.viv.endpoint.util.EndpointUtils;

public final class SimpleServiceEndpointManager implements ServiceEndpointManager {

    private static List<ServiceEndpointResource<?>> resources;

    private static SimpleServiceEndpointManager INSTANCE = null;

    /**
     * private constructor to make sure that this class is singleton.
     *
     * @param endpointSet a collection of {@link ServiceEndpoint} that would be wrapped by {@link ServiceEndpointResource} in constructor
     */
    private SimpleServiceEndpointManager(Collection<ServiceEndpoint<?>> endpointSet) {
        resources = new ArrayList<>(endpointSet.size());
        long index = 0;
        for (ServiceEndpoint endpoint : endpointSet) {
            resources.add(new ServiceEndpointResource<>(endpoint, index++));
        }
    }

    /**
     *
     * @param  param sent to each service if service support the parameters.
     * @return aggregated list of all called valid service.
     */
    public List<?> invoke(Map<String, Object> param) {
        return resources.stream()
                .filter(service -> EndpointUtils.isSubset(service.supportedParameter(),
                        param != null ? param.keySet() : null))
                .map(service -> service.invoke(param))
                .filter(Objects::nonNull)
                .flatMap(list -> list.stream())
                .collect(Collectors.toList());
    }

    /**
     * this class could be a managed bean which can be initialized by spring and the values could be injected by DI
     * @return singleton object for this manager.
     */
    public static SimpleServiceEndpointManager getInstance() {
        if (INSTANCE == null) {
            synchronized (SimpleServiceEndpointManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SimpleServiceEndpointManager(initializeDefaultServiceEndpoints());
                }
            }
        }
        return INSTANCE;
    }

    /**
     * returns a singleton list of service end point the sytsem needs to initizlie.
     * can an be done with reflection!
     *
     * @return collection of service endpint
     */
    private static Collection<ServiceEndpoint<?>> initializeDefaultServiceEndpoints() {
        return Arrays.asList(new StringServiceEndpoint(), new IntegerServiceEndpoint());
    }

}

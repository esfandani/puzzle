package com.esfandani.viv.endpoint.util;

import java.util.Set;

import org.springframework.util.CollectionUtils;

/**
 * This class is a utility class related for {@link com.esfandani.viv.endpoint.ServiceEndpoint}
 * and it consists exclusively static methods.
 * @author Reza Esfandani
 */
public class EndpointUtils {

    /**
     *
     * returns true if subset parameter is subset of set parameter.
     * returns true if subset is empty.
     * @param set set
     * @param subset
     * @return
     */
    public static boolean isSubset(Set<String> set, Set<String> subset) {
        if (CollectionUtils.isEmpty(subset)) {
            return true;
        }
        if(CollectionUtils.isEmpty(set)){
            return false;
        }
        if (subset.size() > set.size()) {
            return false;
        }
        for (String param : subset) {
            if (!set.contains(param)) {
                return false;
            }
        }
        return true;
    }
}

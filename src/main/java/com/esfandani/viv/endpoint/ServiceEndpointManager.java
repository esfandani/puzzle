package com.esfandani.viv.endpoint;

import java.util.List;
import java.util.Map;

public interface ServiceEndpointManager {
    public List<?> invoke(Map<String, Object> param);
}

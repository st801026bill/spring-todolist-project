package com.bill.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {
    @Autowired
    private ObjectMapper mapper;

    public String writeValueAsString(Object o) {
      try {
          return mapper.writeValueAsString(o);
      } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
      }
    }

    public <T> T readValue(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
           throw new RuntimeException(e);
        }
    }

    public <T> T readValue(String json, TypeReference<T> clazz) {
        if(StringUtils.isBlank(json)) return null;
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

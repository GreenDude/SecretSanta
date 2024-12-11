package org.GreenDude.SecretSanta.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SimpleInMemoryStorage {

    public enum TYPE {
        SURVEY ("Survey"),
        PARTICIPANTS ("Participants");

        private final String type;

        TYPE(String type) {
            this.type = type;
        }

        public String getType(){
            return type;
        }
    }

    private Map<TYPE, Object> memory = new HashMap<>();

    public void save(TYPE key, Object value){
        memory.put(key, value);
    }

    public Object read(TYPE key){
        return memory.get(key);
    }
}

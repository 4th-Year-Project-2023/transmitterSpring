package org.example.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class MsgData {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MsgData.class);


    public List<String> commands = new ArrayList<String>();


    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }


    public String convertToJson() {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        log.debug("String before conversion: " + json);
        try {
            json = ow.writeValueAsString(this);
            log.debug("String after conversion: " + json);
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("Could not get string format for object.", jsonProcessingException);
        }
        log.info("Converting object to JSON : " + json);
        return json;
    }


}

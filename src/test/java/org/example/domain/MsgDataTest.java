package org.example.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MsgDataTest {

    @Test
    void convertToJson() {
        String expectedJsonString = "{\n  \"commands\" : [ \"neofetch>>cc.out\" ]\n}";
        MsgData msgData = new MsgData();
        msgData.commands= Arrays.asList("neofetch>>cc.out");
        assertEquals(expectedJsonString,msgData.convertToJson());
    }
}
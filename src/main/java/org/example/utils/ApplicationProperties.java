package org.example.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationProperties {


    @JsonProperty("mqtt.server.name")
    private String mqttServerAddress;
    @JsonProperty("mqtt.channel.name")
    private String mqttTopicName;

    @JsonProperty("mqtt.output.channel.name")
    private String mqttOutputTopicName;

    @JsonProperty("senderUserName")
    private String brokerUserName;
    @JsonProperty("senderPassword")
    private String brokerPassword;

    public String getMqttServerAddress() {
        return mqttServerAddress;
    }

    public void setMqttServerAddress(String mqttServerAddress) {
        this.mqttServerAddress = mqttServerAddress;
    }

    public String getMqttTopicName() {
        return mqttTopicName;
    }

    public void setMqttTopicName(String mqttTopicName) {
        this.mqttTopicName = mqttTopicName;
    }

    public String getBrokerUserName() {
        return brokerUserName;
    }

    public void setBrokerUserName(String brokerUserName) {
        this.brokerUserName = brokerUserName;
    }

    public String getBrokerPassword() {
        return brokerPassword;
    }

    public void setBrokerPassword(String brokerPassword) {
        this.brokerPassword = brokerPassword;
    }

    public String getMqttOutputTopicName() {
        return mqttOutputTopicName;
    }

    public void setMqttOutputTopicName(String mqttOutputTopicName) {
        this.mqttOutputTopicName = mqttOutputTopicName;
    }
}

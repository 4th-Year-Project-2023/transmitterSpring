package org.example.domain;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.example.utils.ParameterExtractor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RelayStation {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RelayStation.class);

    private String mqttBrokerAddress;


    private String mqttTopic;


    private String brokerUserName;


    private String brokerPassword;

    @Value("${commands}")
    private String commands;


    @Autowired
    MsgData msgData;

    @Autowired
    ParameterExtractor parameterExtractor;

//    @Autowired
//    OutputReceiverCallback outputReceiverCallback;


    private IMqttClient mqttClient;


    private void initializeParameters() {
        parameterExtractor.initializeParameters();
        mqttBrokerAddress = parameterExtractor.getApplicationProperties().getMqttServerAddress();
        mqttTopic = parameterExtractor.getApplicationProperties().getMqttTopicName();
        brokerUserName = parameterExtractor.getApplicationProperties().getBrokerUserName();
        brokerPassword = parameterExtractor.getApplicationProperties().getBrokerPassword();
    }


    @PostConstruct
    private void setupConnection() {

        initializeParameters();

        log.info("Setting up connection to broker");

        try {
            mqttClient = new MqttClient(mqttBrokerAddress,
                    "publisher_greg" + ThreadLocalRandom.current().nextLong(), new MemoryPersistence());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(brokerUserName);
            options.setPassword(brokerPassword.toCharArray());
            options.setAutomaticReconnect(true);
            options.setCleanSession(false);
            options.setMaxInflight(3);
            options.setKeepAliveInterval(300);
            mqttClient.connect(options);
            log.info("Connected to broker");
            sendMessage();

        } catch (MqttException e) {
            log.error("Could not instantiate client-> ", e);
        }

    }


    public void sendMessage() {


        Thread.currentThread().setName("publisher client");

        try {
            msgData.setCommands(Arrays.asList(commands.split(",")));
            byte[] messageBytes = msgData.convertToJson().getBytes();
            mqttClient.publish(mqttTopic, new MqttMessage(messageBytes));
            log.info("Published message ");
            mqttClient.disconnect();
            log.info("Disconnecting client");
            mqttClient.close();

        } catch (MqttException e) {
            log.error("Could not send msg.", e);
        }

    }

//    public void receiveMessage()
//    {
//        try {
//            mqttClient.subscribe(mqttTopic);
//        } catch (MqttException e) {
//            throw new RuntimeException(e);
//        }
//
//        mqttClient.setCallback(outputReceiverCallback);
//    }



    public String getMqttBrokerAddress() {
        return mqttBrokerAddress;
    }

    public void setMqttBrokerAddress(String mqttBrokerAddress) {
        this.mqttBrokerAddress = mqttBrokerAddress;
    }

    public String getMqttTopic() {
        return mqttTopic;
    }

    public void setMqttTopic(String mqttTopic) {
        this.mqttTopic = mqttTopic;
    }

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }

    public IMqttClient getMqttClient() {
        return mqttClient;
    }

    public void setMqttClient(IMqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }
}

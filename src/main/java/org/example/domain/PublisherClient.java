package org.example.domain;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class PublisherClient implements Runnable{
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PublisherClient.class);
    @Value("${destination.address}")
    private String destinationAddress;

    @Value("${mqtt.topic}")
    private String mqttTopic;

    @Value("${commands}")
    private String commands;

    @Value("${senderUserName}")
    private String senderUserName;

    @Value("${senderPassword}")
    private String senderPassword;



    @Autowired
    MsgData msgData ;

    @Autowired
    GetParameter getParameter;

    private IMqttClient mqttClient;





    @PostConstruct
    private void setupConnection() {

        try {
            mqttClient = new MqttClient(destinationAddress,
                    "publisher_greg" + ThreadLocalRandom.current().nextLong(),new MemoryPersistence());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(senderUserName);
            options.setPassword(senderPassword.toCharArray());
            options.setAutomaticReconnect(true);
            options.setCleanSession(false);
            options.setMaxInflight(3);
            options.setKeepAliveInterval(300);
            mqttClient.connect(options);
            log.info("Connected to broker");

        } catch (MqttException e) {
            log.error("Could not instantiate client-> ", e);
        }
    }


    @Override
    public void run() {
        getParameter.printParam("ccApplication");

        Thread.currentThread().setName("publisher client");





        try {
            msgData.setCommands(Arrays.asList(commands.split(",")));
            byte[] messageBytes = msgData.convertToJson().getBytes();
//            log.info("Publishing message");
            mqttClient.publish(mqttTopic, new MqttMessage(messageBytes));
            log.info("Published message ");
            mqttClient.disconnect();
            log.info("Disconnecting client");
            mqttClient.close();

        } catch (MqttException e) {
            log.error("Could not send msg.", e);
        }

    }



    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
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

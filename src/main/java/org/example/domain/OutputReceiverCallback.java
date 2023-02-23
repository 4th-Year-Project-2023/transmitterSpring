package org.example.domain;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;


@Component
public class OutputReceiverCallback implements MqttCallback {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OutputReceiverCallback.class);

    @Override
    public void connectionLost(Throwable cause) {
        log.info("Connection Lost. Try to reconnect");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        log.info("Message arrived");
        String strMessage = new String(message.getPayload());
        log.info("Output:  "+message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("Delivery Completed!!");
    }
}

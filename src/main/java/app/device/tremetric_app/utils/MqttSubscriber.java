package app.device.tremetric_app.utils;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MqttSubscriber {
    private static final Logger logger = LoggerFactory.getLogger(MqttSubscriber.class);

 
    private final String broker = "tcp://localhost:1883";
    private final String topic = "gps/rawData";
    private final String clientId = "my_client_id";  

    private IMqttClient mqttClient;

    public MqttSubscriber() throws MqttException {
        mqttClient = new MqttClient(broker, clientId);
        connectAndSubscribe();
    }

    private void connectAndSubscribe() throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setKeepAliveInterval(60); 

        try {
            mqttClient.connect(options);
            logger.info("Connected to broker at {}", broker);
        } catch (MqttException e) {
            logger.error("Error connecting to broker: {}", e.getMessage());
            throw e;
        }

        try {
            mqttClient.subscribe(topic, (tpc, message) -> {
                String rawGPSData = new String(message.getPayload());
                logger.info("Received raw message from topic '{}': {}", tpc, rawGPSData);
                
                rawGPSData = rawGPSData.replaceAll("\\s+", "");
                Map<String, Object> transformedPayload = transformPayload(rawGPSData);
                logger.info("Transformed payload: {}", transformedPayload);
            });
            logger.info("Subscribed to topic '{}'", topic);
        } catch (MqttException e) {
            logger.error("Error subscribing to topic '{}': {}", topic, e.getMessage());
            throw e;
        }
    }

    private Map<String, Object> transformPayload(String rawGPSData) {
 
        Map<String, Object> result = new HashMap<>();

        try {
    
            result.put("deviceSerialNumber", rawGPSData.substring(0, 12));
            result.put("latitude", parseHexToDecimal(rawGPSData.substring(12, 20)));  
            result.put("longitude", parseHexToDecimal(rawGPSData.substring(20, 28))); 
            result.put("status", rawGPSData.substring(28, 32));
            result.put("timestamp", rawGPSData.substring(32, 44));
        } catch (StringIndexOutOfBoundsException e) {
            logger.error("Error parsing raw GPS data: {}", e.getMessage());
        }

        return result;
    }

    private double parseHexToDecimal(String hexString) {
        return Long.parseLong(hexString, 16) / 1000000.0;
    }
}

package com.mapr.demo.mqtt.simple;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Publisher {
	
	public static void startPublisher () throws MqttException {
		
		String messageString = "15,20,30";   

	    System.out.println("== START PUBLISHER ==");


	    MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
	    client.connect();
	    MqttMessage message = new MqttMessage();   
	    message.setPayload(messageString.getBytes());
	    client.publish("iot_data", message);
	   
	    
	    client.disconnect();

	    System.out.println("== END PUBLISHER ==");

	  }
		
	}




import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import com.mapr.demo.mqtt.simple.SimpleMqttCallBack;
import com.mapr.demo.mqtt.simple.Subscriber;
import java.util.Arrays;

public class Client {
	

	public Client() {
		
		
	}
	
	public static void startSubscriber() throws MqttException {
		
		  MqttClient client=new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
		  client.setCallback( new SimpleMqttCallBack() );
		  client.connect();

		  client.subscribe("iot_data");
		  
	}
	
	public static double [][] setDiagrammdaten() {
		
		return Diagrammdaten();
		
	}
	
	public static String getLiveMesswerte() throws MqttException {
		
		String LiveMesswerte = SimpleMqttCallBack.setLiveMesswerte();
		
		return LiveMesswerte;
	}
	
	public static String [] setMesswerte() throws MqttException{
		
		return MQTT();
	}
	
	private double [][] DB_request (int DatumA, int DatumE, int ZeitA, int ZeitE) {
		
		// SQL Empfang statement JDBC
		
		double MesswerteArray [][] = null;
		
		return MesswerteArray;
	}

	private static double [][] Diagrammdaten () {
		
		double MesswerteArray1[][] = {{00.00, 05.30,20.00}, {15,20,30}};
				
		return MesswerteArray1;	
	}
	
	private static String[] MQTT() throws MqttException {
		
		String LiveMesswerte = getLiveMesswerte();
		
		String[] SplitArray = LiveMesswerte.split(",");
		
		return SplitArray; 
	}
	

}

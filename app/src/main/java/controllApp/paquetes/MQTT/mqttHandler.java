package controllApp.paquetes.MQTT;


import android.content.Context;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class mqttHandler {

    private MqttClient client;
    public String USERNAME;
    public String PASSWORD;

    public void connect(String brokerUrl, String clientId, Context context) {
        try {
            // persistencia de datos
            MemoryPersistence persistence = new MemoryPersistence();

            client = new MqttClient(brokerUrl, clientId, persistence);
            MqttConnectOptions connectOptions = new MqttConnectOptions();


            // Entrega de datos
            USERNAME = "androidteststiqq";
            PASSWORD = "W0U2XNxCKinXaOBv";

            connectOptions.setUserName(USERNAME);
            connectOptions.setPassword(PASSWORD.toCharArray());

            client.setCallback(new MqttCallback()  {

                @Override
                public void connectionLost(Throwable cause) {
                    // Manejar pérdida de conexión
                    Toast.makeText(context, "Conexión Perdida", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {

                    try {
                        // Este método se llama cuando se recibe un mensaje en el tema suscrito
                        String messageText = new String(message.getPayload(), "UTF-8");
                        // Muestra el mensaje con un Toast
                        Toast.makeText(context, messageText, Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }


            });

            // Opciones de conexion
            connectOptions.setCleanSession(true);

            // Conectar al broker
            client.connect(connectOptions);



        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, String message, Context context) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic) {
        try {
            client.subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}

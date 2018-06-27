package uk.co.labfour.net.transport;

import uk.co.labfour.bjson.BJsonException;
import uk.co.labfour.cloud2.protocol.BaseRequest;
import uk.co.labfour.cloud2.protocol.BaseResponse;
import uk.co.labfour.error.BEarer;
import uk.co.labfour.error.BException;
import uk.co.labfour.logger.MyLogger;
import uk.co.labfour.net.proto.mqtt.client.*;

public class MqttTransport2 implements IGenericTransport2 {
    private MyLogger log;
    private Mqtt mqtt;
    private IMqttClient.QoS defaultQoS = IMqttClient.QoS.QOS1;

    public MqttTransport2(Mqtt mqtt, MyLogger log) {
        this.mqtt = mqtt;
        this.log = log;
    }

    @Override
    public BEarer listen(String address, IMqttSubscriptionCallback callback) {
        return mqtt.subscribe(address, defaultQoS, callback);
    }

    public BEarer<MqttMessage> send(String topic, byte[] payload) {
        MqttMessage msg = new MqttMessage(topic, payload, defaultQoS, new MqttFutureBEarer());

        BEarer sendOp = mqtt.publish(msg);

        if (sendOp.isOk()) {
            return new BEarer<MqttMessage>().setSuccess().set(msg);
        } else {
            return BEarer.createGenericError(this.getClass().toString(), sendOp.getDescription());
        }
    }

    @Override
    public BEarer start() {
        return mqtt.init();
    }

    @Override
    public BEarer stop() {
        return mqtt.shutdown();
    }

    @Override
    public BEarer<MqttMessage> reply(BaseResponse response) {
        try {
            return send(response.getReplyTo(), BaseResponse.toJsonString(response).getBytes());

        } catch (BJsonException e) {
            return BEarer.createGenericError(this.getClass().toString(), e.getMessage());
        }

    }

    @Override
    public BEarer<MqttMessage> send(BaseRequest request) {
        try {
            return send(request.getConsumer(), request.getAsBjsonObject().toString().getBytes());
        } catch (BException e) {
            return BEarer.createGenericError(this.getClass().toString(), e.getMessage());
        }
    }

}

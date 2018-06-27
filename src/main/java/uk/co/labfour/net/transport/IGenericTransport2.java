package uk.co.labfour.net.transport;

import uk.co.labfour.cloud2.protocol.BaseRequest;
import uk.co.labfour.cloud2.protocol.BaseResponse;
import uk.co.labfour.error.BEarer;
import uk.co.labfour.net.proto.mqtt.client.IMqttSubscriptionCallback;
import uk.co.labfour.net.proto.mqtt.client.MqttMessage;

public interface IGenericTransport2 {
    BEarer listen(String address, IMqttSubscriptionCallback callback);

    BEarer start();

    BEarer stop();

    BEarer<MqttMessage> reply(BaseResponse response);

    BEarer<MqttMessage> send(BaseRequest request);
}

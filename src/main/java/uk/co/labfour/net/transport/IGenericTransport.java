package uk.co.labfour.net.transport;

import uk.co.labfour.cloud2.protocol.BaseRequest;
import uk.co.labfour.cloud2.protocol.BaseResponse;
import uk.co.labfour.error.BException;

public interface IGenericTransport {

	public void startup() throws BException;

	public void shutdown() throws BException;
	
	public void reply(BaseResponse response) throws BException;
	
	public void send(BaseRequest request) throws BException;

	public String getReplyToAddress();

}
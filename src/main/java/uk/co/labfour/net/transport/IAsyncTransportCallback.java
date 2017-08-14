package uk.co.labfour.net.transport;

import uk.co.labfour.error.BException;

public interface IAsyncTransportCallback {
	
	public void callback(String message) throws BException;
	
}

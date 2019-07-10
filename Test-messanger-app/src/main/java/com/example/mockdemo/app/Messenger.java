package com.example.mockdemo.app;

import com.example.mockdemo.messenger.ConnectionStatus;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.SendingStatus;


public class Messenger {

	private MessengerServiceImpl ms;

	public Messenger(MessengerServiceImpl ms) {
		this.ms = ms;
	}

	public Messenger() {
	}

	public int testConnection(String server) throws MalformedRecipientException {
		if(server==null) {
			throw new MalformedRecipientException();
		}
		if(server.endsWith(".pl") || server.endsWith(".com")){
			
		ConnectionStatus connectionStatus = ms.testConnection(server);
		switch (connectionStatus) {
	      case FAILURE:
	        return 1;
	      case SUCCESS:
	        return 0;
	      default:
	    	  throw new MalformedRecipientException();
		}
		}else {
			throw new MalformedRecipientException();
		}
	}

	public int sendMessage(String server, String message) throws MalformedRecipientException {
		if(server==null || message==null) {
			throw new MalformedRecipientException();
		}
		if(server.endsWith(".pl") || server.endsWith(".com") && message.length()>1) {
		SendingStatus sendingStatus = ms.sendMessage(server, message);
		switch (sendingStatus) {
	      case SENT:
	        return 0;
	      case SENDING_ERROR:
	        return 1;
	      case MESSAGE_ERROR:
	        return 2;
	      default:
	    	  throw new MalformedRecipientException();
		}
		}else {
			throw new MalformedRecipientException();
		}
	}
	
}

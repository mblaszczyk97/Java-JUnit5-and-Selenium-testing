package com.example.mockdemo.app;
import com.example.mockdemo.messenger.ConnectionStatus;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.SendingStatus;


public interface MessengerServiceImpl {
	
	  ConnectionStatus testConnection(String server);
	  SendingStatus sendMessage(String server, String message) throws MalformedRecipientException;
}

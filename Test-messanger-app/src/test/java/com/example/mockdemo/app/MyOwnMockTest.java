package com.example.mockdemo.app;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.mockdemo.messenger.MalformedRecipientException;

import mymocks.MessengerMock;

class MyOwnMockTest {
	private final String VALID_SERVER = "inf.ug.edu.pl";
	private final String INVALID_SERVER = "inf.ug.edu.com";
	private final String VALID_MESSAGE = "some message";
	private final String INVALID_MESSAGE = "q";

	private Messenger messenger;
	private MessengerMock mock;

	public int returned;
	public int returner;

	@BeforeEach
	public void setUp() throws Exception {
		mock = new MessengerMock();
		messenger = new Messenger(mock);
	}

	@Test
	public void returnZaeroOnValidServer() throws MalformedRecipientException {
		mock.setConnection(true);
		mock.setMessageGo(true);
		int returned = messenger.testConnection(VALID_SERVER);
		int returner = 0;
		assertEquals(returner, returned);
	}
	
	@Test
	public void noConnectionToValidServer() throws MalformedRecipientException {
		mock.setConnection(false);
		mock.setMessageGo(true);
		int returned = messenger.testConnection(VALID_SERVER);
		int returner = 1;
		assertEquals(returner, returned);
	}
	
	@Test
	public void serverInvalid() throws MalformedRecipientException {
		mock.setConnection(true);
		mock.setMessageGo(true);
		int returned = messenger.testConnection(INVALID_SERVER);
		int returner = 0;
		assertEquals(returner, returned);
	}
	
	@Test
	public void messageNotSent() throws MalformedRecipientException {
		mock.setConnection(true);
		mock.setMessageGo(false);
		int returned = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		int returner = 1;
		assertEquals(returner, returned);
	}
	
	@Test
	public void messageInvalid() throws MalformedRecipientException {
		mock.setConnection(true);
		mock.setMessageGo(true);
		int returned = messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE);
		int returner = 2;
		assertEquals(returner, returned);
	}
	
	@Test
	public void messageValidServerInvalid() throws MalformedRecipientException {
		mock.setConnection(true);
		mock.setMessageGo(true);
		int returned = messenger.sendMessage(INVALID_SERVER, VALID_MESSAGE);
		int returner = 0;
		assertEquals(returner, returned);
	}
	
	@Test
	public void exceptionWhenMessageNull() throws MalformedRecipientException {
		mock.setConnection(true);
		mock.setMessageGo(true);
		assertThrows(MalformedRecipientException.class, () -> {
			messenger.sendMessage(VALID_SERVER, null);
		});
	}
	
	@Test
	public void exceptionWhenConnectionNull() throws MalformedRecipientException {
		mock.setConnection(true);
		mock.setMessageGo(true);
		assertThrows(MalformedRecipientException.class, () -> {
			messenger.sendMessage(null, VALID_MESSAGE);
		});
	}
	

	@Test
	public void exceptionWhenBothNull() throws MalformedRecipientException {
		mock.setConnection(true);
		mock.setMessageGo(true);
		assertThrows(MalformedRecipientException.class, () -> {
			messenger.sendMessage(null, null);
		});
	}
	
	@Test
	public void exceptionOnUnknownServer() throws MalformedRecipientException {
		mock.setConnection(true);
		mock.setMessageGo(true);
		assertThrows(MalformedRecipientException.class, () -> {
			messenger.sendMessage("s.com", VALID_MESSAGE);
		});
	}

	@AfterEach
	public void tearDown() {
		returned = 0;
		returner = 0;
		messenger = null;
		mock = null;
	}

}

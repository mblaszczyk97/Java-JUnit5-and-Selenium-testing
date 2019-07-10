package com.example.mockdemo.app;
import static org.junit.Assert.assertEquals;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.mockdemo.messenger.ConnectionStatus;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.SendingStatus;

class JMockMessengerTest {

	private final String VALID_SERVER = "inf.ug.edu.pl";
	private final String INVALID_SERVER = "inf.ug.edu.com";
	private final String VALID_MESSAGE = "some message";
	private final String INVALID_MESSAGE = "qq";

	int returned;
	int returner;

	private Messenger messenger;
	private MessengerServiceImpl mock;
	private Mockery mockery = new Mockery();

	@BeforeEach
	public void begin() throws Exception {
		mockery = new Mockery();
		mock = mockery.mock(MessengerServiceImpl.class);
		messenger = new Messenger(mock);
	}

	@Test
	public void connectedToTheServer() throws MalformedRecipientException {
		mockery.checking(new Expectations() {
			{
				allowing(mock).testConnection(VALID_SERVER);
				will(returnValue(ConnectionStatus.SUCCESS));
			}
		});

		returner = 0;
		returned = messenger.testConnection(VALID_SERVER);
		assertEquals(returner, returned);
	}
	
	@Test
	public void notConnectedToTheServer() throws MalformedRecipientException {
		mockery.checking(new Expectations() {
			{
				allowing(mock).testConnection(INVALID_SERVER);
				will(returnValue(ConnectionStatus.FAILURE));
			}
		});

		returner = 1;
		returned = messenger.testConnection(INVALID_SERVER);
		assertEquals(returner, returned);
	}
	
	@Test
	public void validSent() throws MalformedRecipientException {
		mockery.checking(new Expectations() {
			{
				allowing(mock).sendMessage(VALID_SERVER, VALID_MESSAGE);
				will(returnValue(SendingStatus.SENT));
			}
		});
		returner = 0;
		returned = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		assertEquals(returner, returned);
	}
	
	@Test
	public void validServerNotMessage() throws MalformedRecipientException {
		mockery.checking(new Expectations() {
			{
				allowing(mock).sendMessage(VALID_SERVER, INVALID_MESSAGE);
				will(returnValue(SendingStatus.MESSAGE_ERROR));
			}
		});
		returner = 2;
		returned = messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE);
		assertEquals(returner, returned);
	}
	
	@Test
	public void invalidServerValidMessage() throws MalformedRecipientException {
		mockery.checking(new Expectations() {
			{
				allowing(mock).sendMessage(INVALID_SERVER, VALID_MESSAGE);
				will(returnValue(SendingStatus.SENDING_ERROR));
			}
		});
		returner = 1;
		returned = messenger.sendMessage(INVALID_SERVER, VALID_MESSAGE);
		assertEquals(returner, returned);
	}

	@AfterEach
	public void destroy() throws Exception {
		returner = 0;
		returned = 0;
		messenger = null;
		mock = null;
		mockery = null;
	}

}

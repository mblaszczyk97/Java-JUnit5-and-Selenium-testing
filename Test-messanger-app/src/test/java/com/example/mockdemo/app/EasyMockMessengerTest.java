package com.example.mockdemo.app;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.mockdemo.messenger.ConnectionStatus;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.SendingStatus;

public class EasyMockMessengerTest {

	private final String VALID_SERVER = "inf.ug.edu.pl";
	private final String INVALID_SERVER = "inf.ug.edu.com";
	private final String VALID_MESSAGE = "some message";
	private final String INVALID_MESSAGE = "qq";

	public int returned;
	public int returner;

	private Messenger messenger;
	private MessengerServiceImpl mock;

	@BeforeEach
	public void begin() throws Exception {
		mock = EasyMock.createNiceMock(MessengerServiceImpl.class);
		messenger = new Messenger(mock);
	}

	@Test
	public void testConnectionNotNull() throws MalformedRecipientException {
		expect(mock.testConnection(VALID_SERVER)).andReturn(ConnectionStatus.SUCCESS);
		replay(mock);
		returned = messenger.testConnection(VALID_SERVER);
		assertNotNull(returned);
	}

	@Test
	public void testConnectionInValidNumbers() throws MalformedRecipientException {
		expect(mock.testConnection(VALID_SERVER)).andReturn(ConnectionStatus.SUCCESS);
		replay(mock);
		returned = messenger.testConnection(VALID_SERVER);
		returner = 3;
		assertNotEquals(returner, returned);
	}

	@Test
	public void testConnectionValid() throws MalformedRecipientException {
		expect(mock.testConnection(VALID_SERVER)).andReturn(ConnectionStatus.SUCCESS);
		replay(mock);
		returned = messenger.testConnection(VALID_SERVER);
		returner = 0;
		assertEquals(returner, returned);
	}

	@Test
	public void testConnectionInvalid() throws MalformedRecipientException {
		expect(mock.testConnection(INVALID_SERVER)).andReturn(ConnectionStatus.FAILURE);
		replay(mock);
		returned = messenger.testConnection(INVALID_SERVER);
		returner = 1;
		assertEquals(returner, returned);
	}

	@Test
	public void sendReturnValid() throws MalformedRecipientException {
		expect(mock.sendMessage(VALID_SERVER, VALID_MESSAGE)).andReturn(SendingStatus.SENT);
		replay(mock);
		returned = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		returner = 0;
		assertEquals(returner, returned);
	}

	@Test
	public void sendReturnSendingError() throws MalformedRecipientException {
		expect(mock.sendMessage(INVALID_SERVER, VALID_MESSAGE)).andReturn(SendingStatus.SENDING_ERROR);
		replay(mock);
		returned = messenger.sendMessage(INVALID_SERVER, VALID_MESSAGE);
		returner = 1;
		assertEquals(returner, returned);
	}

	@Test
	public void sendReturnMessageError() throws MalformedRecipientException {
		expect(mock.sendMessage(VALID_SERVER, INVALID_MESSAGE)).andReturn(SendingStatus.MESSAGE_ERROR);
		replay(mock);
		returned = messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE);
		returner = 2;
		assertEquals(returner, returned);
	}

	@Test
	public void sendReturnSometimeThrowsOnInvalidMessage() throws MalformedRecipientException {
		expect(mock.sendMessage(VALID_SERVER, INVALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(mock);
		assertThrows(MalformedRecipientException.class, () -> {
			messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE);
		});
	}

	@Test
	public void sendReturnSometimeThrowsOnInvalidServer() throws MalformedRecipientException {
		expect(mock.sendMessage(INVALID_SERVER, VALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(mock);
		assertThrows(MalformedRecipientException.class, () -> {
			messenger.sendMessage(INVALID_SERVER, VALID_MESSAGE);
		});
	}

	@Test
	public void sendReturnSometimeThrowsOnInvalidBoth() throws MalformedRecipientException {
		expect(mock.sendMessage(INVALID_SERVER, INVALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(mock);
		assertThrows(MalformedRecipientException.class, () -> {
			messenger.sendMessage(INVALID_SERVER, INVALID_MESSAGE);
		});
	}

	@Test
	public void sendReturnSometimeThrowsOnValidBoth() throws MalformedRecipientException {
		expect(mock.sendMessage(VALID_SERVER, VALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(mock);
		assertThrows(MalformedRecipientException.class, () -> {
			messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		});
	}

	@AfterEach
	public void destroy() throws Exception {
		returned = 0;
		returner = 0;
		messenger = null;
		mock = null;
	}
}

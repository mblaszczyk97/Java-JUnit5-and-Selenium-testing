package com.example.mockdemo.app;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.*;

import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.mockdemo.messenger.MalformedRecipientException;


public class EasyMockMessengerWithoutInterfaceTest {

	private final String VALID_SERVER = "inf.ug.edu.pl";
	private final String INVALID_SERVER = "inf.ug.edu.eu";
	private final String VALID_MESSAGE = "some message";
	private final String INVALID_MESSAGE = "qq";

	public int returned;
	public int returner;

	private Messenger messenger;

	@BeforeEach
	public void begin() throws Exception {
		messenger = new Messenger();
		messenger = EasyMock.createStrictMock(Messenger.class);
	}

	@Test
	public void testConnectionValid() throws MalformedRecipientException {
		expect(messenger.testConnection(VALID_SERVER)).andReturn(0);
		replay(messenger);
		returned = messenger.testConnection(VALID_SERVER);
		returner = 0;
		assertEquals(returner, returned);
	}

	@Test
	public void testConnectionInvalid() throws MalformedRecipientException {
		expect(messenger.testConnection(INVALID_SERVER)).andReturn(1);
		replay(messenger);
		returned = messenger.testConnection(INVALID_SERVER);
		returner = 1;
		assertEquals(returner, returned);
	}

	@Test
	public void sendReturnValid() throws MalformedRecipientException {
		expect(messenger.sendMessage(VALID_SERVER, VALID_MESSAGE)).andReturn(0);
		replay(messenger);
		returned = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		returner = 0;
		assertEquals(returner, returned);
	}

	@Test
	public void sendReturnSendingError() throws MalformedRecipientException {
		expect(messenger.sendMessage(INVALID_SERVER, VALID_MESSAGE)).andReturn(1);
		replay(messenger);
		returned = messenger.sendMessage(INVALID_SERVER, VALID_MESSAGE);
		returner = 1;
		assertEquals(returner, returned);
	}

	@Test
	public void sendReturnMessageError() throws MalformedRecipientException {
		expect(messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE)).andReturn(2);
		replay(messenger);
		returned = messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE);
		returner = 2;
		assertEquals(returner, returned);
	}

	@Test
	public void sendReturnSometimeThrowsOnInvalidMessage() throws MalformedRecipientException {
		expect(messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(messenger);
		assertThrows(MalformedRecipientException.class, () -> {
			messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE);
		});
	}

	@Test
	public void sendReturnSometimeThrowsOnInvalidServer() throws MalformedRecipientException {
		expect(messenger.sendMessage(INVALID_SERVER, VALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(messenger);
		assertThrows(MalformedRecipientException.class, () -> {
			messenger.sendMessage(INVALID_SERVER, VALID_MESSAGE);
		});
	}

	@Test
	public void sendReturnSometimeThrowsOnInvalidBoth() throws MalformedRecipientException {
		expect(messenger.sendMessage(INVALID_SERVER, INVALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(messenger);
		assertThrows(MalformedRecipientException.class, () -> {
			messenger.sendMessage(INVALID_SERVER, INVALID_MESSAGE);
		});
	}

	@Test
	public void sendReturnSometimeThrowsOnValidBoth() throws MalformedRecipientException {
		expect(messenger.sendMessage(VALID_SERVER, VALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(messenger);
		assertThrows(MalformedRecipientException.class, () -> {
			messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		});
	}

	@AfterEach
	public void destroy() throws Exception {
		returned = 0;
		returner = 0;
		messenger = null;
	}
}

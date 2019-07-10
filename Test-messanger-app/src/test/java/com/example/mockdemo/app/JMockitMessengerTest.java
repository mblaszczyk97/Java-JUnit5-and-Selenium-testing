package com.example.mockdemo.app;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;

import com.example.mockdemo.messenger.ConnectionStatus;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.SendingStatus;

class JMockitMessengerTest {

	private final String VALID_SERVER = "inf.ug.edu.pl";
	private final String INVALID_SERVER = "inf.ug.edu.com";
	private final String VALID_MESSAGE = "some message";
	private final String INVALID_MESSAGE = "qq";

	int returned;
	int returner;
	
	@Tested
	private Messenger messenger;
	@Mocked
	private MessengerServiceImpl mock;

	@BeforeEach
	public void begin() throws Exception {
		messenger = new Messenger(mock);
	}

	@Test
	public void expectNoProblemsOnValidServer() throws MalformedRecipientException {
		new Expectations() {
			{
				mock.testConnection(VALID_SERVER);
				result = ConnectionStatus.SUCCESS;
			}
		};

		returned = 0;
		returner = messenger.testConnection(VALID_SERVER);
		assertEquals(returned, returner);
	}
	
	@Test
	public void expectBadCodeOnInvalidServer() throws MalformedRecipientException {
		new Expectations() {
			{
				mock.testConnection(INVALID_SERVER);
				result = ConnectionStatus.FAILURE;
			}
		};

		returned = 1;
		returner = messenger.testConnection(INVALID_SERVER);
		assertEquals(returned, returner);
	}
	
	@Test
	public void expectOnValidMessage() throws MalformedRecipientException {
		new Expectations() {
			{
				mock.sendMessage(VALID_SERVER, VALID_MESSAGE);
				result = SendingStatus.SENT;
			}
		};

		returned = 0;
		returner = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		assertEquals(returned, returner);
	}
	
	@Test
	public void expectOnInvalidMessage() throws MalformedRecipientException {
		new Expectations() {
			{
				mock.sendMessage(VALID_SERVER, INVALID_MESSAGE);
				result = SendingStatus.MESSAGE_ERROR;
			}
		};

		returned = 2;
		returner = messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE);
		assertEquals(returned, returner);
	}
	
	@Test
	public void expectThrowException() throws MalformedRecipientException {
		new Expectations() {
			{
				mock.testConnection(INVALID_SERVER);
				result = new MalformedRecipientException();
			}
		};
		assertThrows(MalformedRecipientException.class, () -> {
			messenger.testConnection(INVALID_SERVER);
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

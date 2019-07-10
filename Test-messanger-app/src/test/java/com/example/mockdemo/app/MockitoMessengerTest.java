package com.example.mockdemo.app;

import static org.mockito.Mockito.when;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.SendingStatus;

class MockitoMessengerTest {

	private final String VALID_SERVER = "inf.ug.edu.pl";
	private final String INVALID_SERVER = "inf.ug.edu.com";
	private final String VALID_MESSAGE = "some message";
	private final String INVALID_MESSAGE = "qq";

	public int returned;
	public int returner;

	private Messenger messenger;

	@Mock
	private MessengerServiceImpl mock;

	@BeforeEach
	public void begin() throws Exception {
		MockitoAnnotations.initMocks(this);
		messenger = new Messenger(mock);
	}

	@Test
	public void sendingValid() throws MalformedRecipientException {
		when(mock.sendMessage(VALID_SERVER, VALID_MESSAGE)).thenReturn(SendingStatus.SENT);
		returner = 0;
		returned = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		assertThat(returned).isEqualTo(returner);
	}
	
	@Test
	public void sendingNotNull() throws MalformedRecipientException {
		when(mock.sendMessage(VALID_SERVER, VALID_MESSAGE)).thenReturn(SendingStatus.SENT);
		returned = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		assertThat(returned).isNotNull();
	}
	
	@Test
	public void messageInvalid() throws MalformedRecipientException {
		when(mock.sendMessage(VALID_SERVER, INVALID_MESSAGE)).thenReturn(SendingStatus.MESSAGE_ERROR);
		returned = messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE);
		assertThat(returned).isInstanceOf(Integer.class);
	}
	
	@Test
	public void sendingAndMessageInvalid() throws MalformedRecipientException {
		when(mock.sendMessage(INVALID_SERVER, INVALID_MESSAGE)).thenReturn(SendingStatus.SENDING_ERROR);
		returned = messenger.sendMessage(INVALID_SERVER, INVALID_MESSAGE);
		assertThat(returned).isBetween(1, 2);
	}
	
	@Test
	public void sendingDOesNotReturnDouble() throws MalformedRecipientException {
		when(mock.sendMessage(INVALID_SERVER, INVALID_MESSAGE)).thenReturn(SendingStatus.SENDING_ERROR);
		returned = messenger.sendMessage(INVALID_SERVER, INVALID_MESSAGE);
		assertThat(returned).isNotInstanceOf(Double.class);
	}
	
	@Test
	public void sendingThrowsOnValis() throws MalformedRecipientException {
		when(mock.sendMessage(VALID_SERVER, VALID_MESSAGE)).thenThrow(new MalformedRecipientException());
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

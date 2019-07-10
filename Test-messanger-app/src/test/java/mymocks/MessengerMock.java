package mymocks;

import com.example.mockdemo.app.MessengerServiceImpl;
import com.example.mockdemo.messenger.ConnectionStatus;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.SendingStatus;

public class MessengerMock implements MessengerServiceImpl {
	private boolean connection;
	private boolean messageGo;

	public boolean isMessageGo() {
		return messageGo;
	}

	public boolean isConnection() {
		return connection;
	}

	public void setConnection(boolean value) {
		connection = value;
	}

	public void setMessageGo(boolean value) {
		messageGo = value;
	}

	@Override
	public ConnectionStatus testConnection(String server) {
		if (connection == true && server.endsWith(".pl") || server.endsWith(".com")) {
			return ConnectionStatus.SUCCESS;
		}
		return ConnectionStatus.FAILURE;
	}

	@Override
	public SendingStatus sendMessage(String server, String message) throws MalformedRecipientException {
		if (server == null || message == null) {
			throw new MalformedRecipientException();
		}

		if (connection == true && server.endsWith(".pl") || server.endsWith(".com")) {
			if (server.length() < 6) {
				throw new MalformedRecipientException();
			}
			if (message.length() <= 1) {
				return SendingStatus.MESSAGE_ERROR;
			}
			if (messageGo) {
				return SendingStatus.SENT;
			} else if (!messageGo) {
				return SendingStatus.SENDING_ERROR;
			}
		} else {
			return SendingStatus.SENDING_ERROR;
		}
		return null;
	}

}

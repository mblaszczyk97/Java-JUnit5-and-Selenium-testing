package com.maventest.libraryRent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class LibraryTest {

	
	public Date newDate(String date) {
		SimpleDateFormat dateformat2 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	    String strdate2 = date;
	    Date newdate;
		try {
			newdate = dateformat2.parse(strdate2);
			return newdate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	Library li = new Library();
    Client client1 = new Client("Józef", "Stalin", "js@zssr.ru", 52, "male");
    Client client2 = new Client("Stefan", "Kula", "jk@jk.com", 19, "male");
    Client client3 = new Client("Anna", "Snow", "as@ss", 22, "female");
	
	@BeforeEach
	public void setUp() {
        li.AddClient(client1);
        li.AddClient(client2);
        li.AddClient(client3);
	}
	

	
	@Test
	public void testAddClient() {
		Library li = new Library();
		Client clientExample = new Client("Emma", "Tomm", "et@tss", 23, "female");
        assertThat(li.AddClient(clientExample)).isTrue();
	}
	
	@Test
	public void testAddClientFalse() {
        assertThat(li.AddClient(client1)).isFalse();
	}
	
	@Test
	public void testSizeOfClient() {
		assertEquals(li.Clients.size(), 3);
	}
	
	@Test
	public void testDeleteClient() {
		Library li = new Library();
	    Client client1 = new Client("Józef", "Stalin", "js@zssr.ru", 52, "male");
	    Client client2 = new Client("Stefan", "Kula", "jk@jk.com", 19, "male");
	    Client client3 = new Client("Anna", "Snow", "as@ss", 22, "female");
		li.AddClient(client1);
	    li.AddClient(client2);
	    li.AddClient(client3);
	    int sizeExpected = li.Clients.size();
	    li.DeleteClient(client1);
		assertEquals(li.Clients.size(), sizeExpected-1);
	}
	
	@Test
	public void testAddRent() {
		Library li = new Library();
	    Client client1 = new Client("Anna", "Snow", "as@ss", 22, "female");
	    li.AddClient(client1);
	    Rent rent1 = new Rent(client1, newDate("03-05-2019 09:30:00"), "Władca Pierścieni", 10.50);
	    li.AddRent(client1, rent1);
	    assertThat(li.Clients.get(client1)).contains(rent1);
	}
	
	@Test
	public void testDeleteRent() {
		Library li = new Library();
	    Client client1 = new Client("Anna", "Snow", "as@ss", 22, "female");
	    li.AddClient(client1);
	    Rent rent1 = new Rent(client1, newDate("03-05-2019 09:30:00"), "Władca Pierścieni", 10.50);
	    li.AddRent(client1, rent1);
	    li.DeleteRent(client1, rent1);
	    assertThat(li.Clients.get(client1).size()).isEqualTo(0);
	}
	
	@Test
	public void testFindClient() {
		Library li = new Library();
	    Client client1 = new Client("Anna", "Snow", "as@ss", 22, "female");
	    li.AddClient(client1);
	    assertThat(li.FindClient("as@ss").getLastName()).isEqualToIgnoringCase("snow");
	}
	
	@Test
	public void testFindClientException() {
		Library li = new Library();
	    Client client1 = new Client("Anna", "Snow", "as@ss", 22, "female");
	    li.AddClient(client1);
	    assertThrows(IllegalArgumentException.class, () -> {
	    	li.FindClient("assss@ss");
		  });
	}
	
	@Test
	public void testStats() {
		assertThat(li.stats()).isEqualToIgnoringCase("Number of clients: 3 Number of planned visits:  0 Newest visit:  There's no visit in our database");
	}
	
	@Test
	public void testStatsFull() {
		Library li = new Library();
	    Client client1 = new Client("Anna", "Snow", "as@ss", 22, "female");
	    li.AddClient(client1);
	    Rent rent1 = new Rent(client1, newDate("03-05-2019 09:30:00"), "Władca Pierścieni", 10.50);
	    li.AddRent(client1, rent1);
		assertThat(li.stats()).isEqualToIgnoringCase("Number of clients: 1Number of planned visits:  1Newest visit: 2019 4 5");
	}
	
	@Test
	public void testMailConfirmation() throws AddressException, MessagingException {
		Library li = new Library();
	    Client client1 = new Client("Anna", "Snow", "as@ss", 22, "female");
	    li.AddClient(client1);
	    Rent rent1 = new Rent(client1, newDate("03-05-2019 09:30:00"), "Władca Pierścieni", 10.50);
		assertTrue(li.AddRentWithEmail(client1, rent1));
	}
	
	@Test
	public void testTimeOut() {
		assertTimeout(Duration.ofMillis(5000), () -> {
			Library li = new Library();
		    Client client1 = new Client("Anna", "Snow", "as@ss", 22, "female");
		    li.AddClient(client1);
		    Rent rent1 = new Rent(client1, newDate("03-05-2019 09:30:00"), "Władca Pierścieni", 10.50);
			li.AddRentWithEmail(client1, rent1);
		});
	}
	

}

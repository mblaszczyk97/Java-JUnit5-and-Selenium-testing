package com.maventest.libraryRent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ClientTest  {
	
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
    Client client2 = new Client("Józek", "Stalinowski", "jss@zssr.ru", 53, "male");
    Rent rent1 = new Rent(client1, newDate("03-05-2019 09:30:00"), "Władca Pierścieni", 10.50);
	
	@BeforeEach
	public void setUp() {
		li.AddClient(client1);
	}
	
	@Test
	public void testTrue() {
        assertThat(li.AddClient(client2)).isTrue();
	}
	
	@Test
	public void testFalse() {
        assertThat(li.AddClient(client1)).isFalse();
	}
	
	@Test
	public void testNotSame() {
        assertNotSame(client1, client2);
	}
	
	@Test
	public void testSetClient() {
		client1.setAge(22);
		client1.setEmail("aaa@gm.com");
		client1.setFirstName("Zbigniew");
		client1.setLastName("Zbigniewowski");
		client1.setGender("female");
		li.AddClient(client1);
		
		assertAll("set client", () -> {
			assertThat(client1.getFirstName()).startsWith("Zbi")
			  								  .endsWith("gniew")
			  								  .isEqualToIgnoringCase("zbigniew");
			assertThat(client1.getLastName()).startsWith("Zbigni")
			  								 .endsWith("wowski")
			  								 .isEqualToIgnoringCase("zbigniewowski");
			assertThat(client1.getEmail()).isEqualToIgnoringCase("aaa@gm.com");
			assertThat(client1.getAge()).isEqualTo(22);
			assertThat(client1.getGender()).isEqualToIgnoringCase("female");
		});
	}
	
	@Test
	public void testToString() {
		assertThat(client1.toString()).isEqualToIgnoringCase("józef stalin");
	}
	
	@Test
	public void testExceptionName() {
		assertThrows(IllegalArgumentException.class, () -> {
			Client clientExample = new Client("","Ekl", "aa@aa.aa", 111, "male");
		  });
	}
	
	@Test
	public void testExceptionLastName() {
		assertThrows(IllegalArgumentException.class, () -> {
			Client clientExample = new Client("Aaa","", "aa@aa.aa", 111, "male");
		  });
	}
	
			
	@Test
	public void testExceptionEmail() {
		assertThrows(IllegalArgumentException.class, () -> {
			Client clientExample = new Client("Aaa","Aaa", "", 111, "male");
		  });
	}
	
	@Test
	public void testExceptionAge() {
		assertThrows(IllegalArgumentException.class, () -> {
			Client clientExample = new Client("Aaa","Aaa", "aaa@aaa.aaa", 156, "male");
		  });
	}
	
	@Test
	public void testClientName() {
        assertThat(client1.getFirstName()).startsWith("Jó")
        								  .endsWith("zef")
        							      .isEqualToIgnoringCase("józef");
	}
	
	@Test
	public void testClientSurname() {
        assertThat(client1.getLastName()).isEqualToIgnoringCase("stalin");
	}
	
	@Test
	public void testClientMail() {
        assertThat(client1.getEmail()).isEqualToIgnoringCase("js@zssr.ru");
	}
	
	@Test
	public void testClientAge() {
        assertThat(client1.getAge()).isEqualTo(52);
	}
	
	@Test
	public void testClientGender() {
        assertThat(client1.getGender()).isEqualToIgnoringCase("male");
	}
	
	

}


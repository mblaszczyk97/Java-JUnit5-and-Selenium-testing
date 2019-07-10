package com.maventest.libraryRent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RentTest {
	
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
    Rent rent2 = new Rent(client2, newDate("03-06-2019 09:30:00"), "Władca Pierścieni", 10.50);
	
	
	@Test
	public void testTrue() {
		li.AddClient(client2);
        assertThat(li.AddRent(client2, rent2)).isTrue();
	}
	
	@Test
	public void testSetRent() {
		rent1.setDateOfBorrow(newDate("03-05-2019 11:30:00"));
		rent1.setDescription("Game of Thrones");
		rent1.setPriceInPLN(12.20);
		
		li.AddClient(client1);
		li.AddRent(client1, rent1);
		rent1.setResercationCode(10230412);
		
		assertAll("set client", () -> {
			assertThat(rent1.getDateOfBorrow()).hasYear(2019)
											   .hasMonth(5)
											   .hasDayOfMonth(3);
			assertThat(rent1.getDescription()).isEqualToIgnoringCase("game of thrones");
			assertThat(rent1.getPriceInPLN()).isEqualTo(12.20);
			assertThat(rent1.getResercationCode()).isEqualTo(10230412);
		});
	}
	
	@Test
	public void testToString() {
		assertThat(rent1.toString()).isEqualToIgnoringCase(newDate("03-05-2019 09:30:00") + " Book:" + rent1.getDescription());
	}
	
	@Test
	public void testExceptionDate() {
		assertThrows(IllegalArgumentException.class, () -> {
			Rent rent = new Rent(client1, newDate("03-05-2019 09:33:00"), "Władca Pierścieni", 10.50);
		  });
	}
	
	@Test
	public void testExceptionPrice() {
		assertThrows(IllegalArgumentException.class, () -> {
			Rent rent = new Rent(client1, newDate("03-05-2019 09:30:00"), "Władca Pierścieni", -10.50);
		  });
	}
	
	@Test
	public void testNullFind() {
		li.AddClient(client1);
		li.AddRent(client1, rent1);
		assertNull(li.FindRent(newDate("03-05-2019 09:32:00")));
	}
	
	@Test
	public void testFindEmpty() {
		assertThrows(NoSuchElementException.class, () -> {
			li.FindRent(newDate("03-05-2019 09:32:00"));
		});
	}
	
	@Test
	public void testExcdeption() throws ParseException {
		li.AddClient(client1);
		li.AddRent(client1, rent1);
		assertThrows(IllegalArgumentException.class, () -> {
			li.AddRent(client1, rent1);
		  });
	}
	
	@Test
	public void testFindRent() {
		li.AddClient(client1);
		li.AddRent(client1, rent1);
		assertThat(li.FindRent(newDate("03-05-2019 09:30:00"))).isEqualTo(rent1);
	}
	
	@Test
	public void testFindRentNull() {
		li.AddClient(client1);
		li.AddRent(client1, rent1);
		assertThat(li.FindRent(newDate("03-05-2019 09:31:00"))).isNull();
	}
	
	@Test
	public void testSettingsHoursOpening() throws FileNotFoundException, IOException {
		Rent re = new Rent();
		assertThat(re.fileReader("settings.csv").get(0)).contains("8","20");
	}
	
	@Test
	public void testSettingsDaysOpening() throws FileNotFoundException, IOException {
		Rent re = new Rent();
		assertThat(re.fileReader("settings.csv").get(1)).contains("1","5");
	}
	
	@Test
	public void testSettingsSize() throws FileNotFoundException, IOException {
		Rent re = new Rent();
		assertThat(re.fileReader("settings.csv").size()).isEqualTo(2);
	}
	
	@Test
	public void testSettingsBadLocation() throws FileNotFoundException, IOException {
		Rent re = new Rent();
		assertThrows(FileNotFoundException.class, () -> {
			re.fileReader("set.csv");
		});
	}
	

	
}

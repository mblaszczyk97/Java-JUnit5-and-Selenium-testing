package com.maventest.libraryRent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Rent {
    public Client client;
    public Date DateOfBorrow;
    public String Description;
    public double PriceInPLN;
    public int resercationCode;
    
	public Date getDateOfBorrow() {
		return DateOfBorrow;
	}

	public void setDateOfBorrow(Date dateOfBorrow) {
		DateOfBorrow = dateOfBorrow;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public double getPriceInPLN() {
		return PriceInPLN;
	}

	public void setPriceInPLN(double priceInPLN) {
		PriceInPLN = priceInPLN;
	}

	public int getResercationCode() {
		return resercationCode;
	}

	public void setResercationCode(int resercationCode) {
		this.resercationCode = resercationCode;
	}

	@Override
	public String toString() {
		return DateOfBorrow + " Book:" + Description;
	}

	public Rent(Client clienti, Date dateOfBorrow, String description, double priceInPLN)
    {
        if (!DateOfVisitIsValid(dateOfBorrow))
        {
            throw new IllegalArgumentException("Date of borrow invalid");
        }
        if (!PriceIsPositive(priceInPLN))
        {
            throw new IllegalArgumentException("Price invalid");
        }
        client = clienti;
        DateOfBorrow = dateOfBorrow;
        Description = description;
        PriceInPLN = priceInPLN;
    }

	public Rent() {
	}

	public static List<List<String>> fileReader(String path) throws FileNotFoundException, IOException {
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split("-");
		        records.add(Arrays.asList(values));
		    }
		}
		return records;
	}
	
    private static boolean DateOfVisitIsValid(Date dateOfBorrow)
    {
    	List<List<String>> records;
		try {
			records = fileReader("settings.csv");
			Date date = new Date();
	        if (dateOfBorrow.after(date) && (dateOfBorrow.getMinutes() == 0 || dateOfBorrow.getMinutes() == 30) 
	        		&& (dateOfBorrow.getHours() >= Integer.parseInt(records.get(0).get(0)) && dateOfBorrow.getHours() <= Integer.parseInt(records.get(0).get(1)))
	        		&& (dateOfBorrow.getDay() >= Integer.parseInt(records.get(1).get(0)) && dateOfBorrow.getDay() <= Integer.parseInt(records.get(1).get(1))))
	            return true;
	        else
	            return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
    }
    
    private static boolean PriceIsPositive(double priceInPLN)
    {
        if (priceInPLN >= 0.00)
            return true;
        else
            return false;
    }

}

package com.maventest.libraryRent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.FileReader;
/**
 * Hello world!
 *
 */
public class Library 
{
	private final static String LEXICAL_UNITS_FILE = "go.csv";
	List<Integer> reservationCodes = new ArrayList<Integer>();
    Map<Client, List<Rent>> Clients = new HashMap<>();
    
    /*private void readLexicalUnitsFile() throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(LEXICAL_UNITS_FILE));
        String line;

        while ((line = in.readLine()) != null) {
            String columns[] = line.split("\t");
            
            
            Date DateOfBorrow;
            String Description;
            double PriceInPLN;
            int resercationCode;
            
            String FirstName;
            String LastName;
            String Email;
            int Age;
        	String Gender;
        	
        	FirstName = columns[0];
        	LastName = columns[1];
        	Email = columns[2];
        	Age = Integer.parseInt(columns[3]);
        	Gender = columns[4];
        	Client client = new Client(FirstName,LastName,Email,Age,Gender);
        	
            Client key = columns[5];
            Rent valueInt;
            List<Rent> valueList;

            try {
            	DateOfBorrow = columns[1]
            	
                valueInt = Integer.parseInt(columns[1]);
            } catch (NumberFormatException e) {
                System.out.println(e);
                continue;
            }

            if (Clients.containsKey(key)) {
                valueList = Clients.get(key);
            } else {
                valueList = new ArrayList<>();
                Clients.put(key, valueList);
            }

            valueList.add(valueInt);
        }

        in.close();
    }
    */
	public Library() {
		reservationCodes.add(20190330);
	}
 
    public boolean AddClient(Client client)
    {
        if (!Clients.containsKey(client))
        {
    		List<Rent> bookres = new ArrayList<Rent>();
            Clients.put(client, bookres);
            return true;
        }
        else
            return false;
    }
    
    public void DeleteClient(Client client) {
    	Clients.remove(client);
    }
    
    public boolean AddRent(Client client, Rent rent)
    {
        if(FindRent(rent.getDateOfBorrow())!=null) {
        	throw new  IllegalArgumentException("The date is duplicated in database");
        }

        	int code;
        	code = reservationCodes.get(reservationCodes.size()-1)+1;
        	reservationCodes.add(code);
        	
        	rent.setResercationCode(code);
        	Clients.get(client).add(rent);
            return true;
        
    }
    
    public boolean AddRentWithEmail(Client client, Rent rent) throws AddressException, MessagingException {
    	if(FindRent(rent.getDateOfBorrow())!=null) {
        	throw new  IllegalArgumentException("The date is duplicated in database");
        }

        	int code;
        	code = reservationCodes.get(reservationCodes.size()-1)+1;
        	reservationCodes.add(code);
        	
        	rent.setResercationCode(code);
        	Clients.get(client).add(rent);
        	confirm(code, rent.getDateOfBorrow(), client.getEmail());
            return true;
    }
    
    public void DeleteRent(Client client, Rent rent) {
    	Clients.get(client).remove(rent);
    }
    
    public Client FindClient(String email)
    {
    	for (Client key : Clients.keySet()) {
    		if(Clients.keySet().iterator().next().Email.equals(email)) {
    			return Clients.keySet().iterator().next();
    		}
    	}
        throw new  IllegalArgumentException("Client not in database");
    }
    
    public Rent FindRent(Date date)
    {
    		for (Rent value : Clients.values().iterator().next()) {
    			if(Clients.values().iterator().next().iterator().next().DateOfBorrow.equals(date)) {
        			return Clients.values().iterator().next().iterator().next();
        		}
    		}
        return null;
    }
    
    public String stats() {
    	int count=0;
    	Date date;
    	List<Date> dates = new ArrayList<Date>();
    	for (Rent value : Clients.values().iterator().next()) {
    		if(Clients.values().iterator().next().iterator().next()!=null) {
    			count ++;
    		}
    	}
    	
    	int nClients = Clients.size();
    	int nVisits = count;
    	String returnSt="";
    	
    	System.out.print("Number of clients: "+nClients+"\n");
    	System.out.print("Number of planned visits: "+nVisits+"\n");
    	if(count!=0) {
    		Date latestDate = Clients.values().iterator().next().get(count-1).getDateOfBorrow();
    		System.out.print("Newest visit: "+latestDate+"\n");
    		returnSt = "Number of clients: "+nClients+"Number of planned visits:  "+nVisits+"Newest visit: "+(latestDate.getYear()+1900)+" "+latestDate.getMonth()+" "+latestDate.getDay();
        }else {
    		System.out.print("There's no visit in our database"+"\n");
    		returnSt = "Number of clients: "+nClients+" Number of planned visits:  "+nVisits+" Newest visit: "+" There's no visit in our database";
    	}
    	return returnSt;
    }
    
    public int days(double i1) {
   	 	return (int) i1;
    }
    
    public static void confirm(int code, Date date, String email) throws AddressException, MessagingException {     
    	Properties prop = new Properties();
    	prop.put("mail.smtp.auth", true);
    	prop.put("mail.smtp.starttls.enable", "true");
    	prop.put("mail.smtp.host", "smtp.mailtrap.io");
    	prop.put("mail.smtp.port", "2525");
    	prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
    	
    	Session session = Session.getInstance(prop, new Authenticator() {
    	    @Override
    	    protected PasswordAuthentication getPasswordAuthentication() {
    	        return new PasswordAuthentication("db170bc6228f93", "17aa6f70a2aace");
    	    }
    	});
    	
    	Message message = new MimeMessage(session);
    	message.setFrom(new InternetAddress("internet.reservations.bot@gmail.com"));
    	message.setRecipients(
    	Message.RecipientType.TO, InternetAddress.parse(email));
    	message.setSubject("Reservation");
    	 
    	String msg = "Hello you reservation code is as follows: "+code+ " Date: "+ date;
    	 
    	MimeBodyPart mimeBodyPart = new MimeBodyPart();
    	mimeBodyPart.setContent(msg, "text/html");
    	 
    	Multipart multipart = new MimeMultipart();
    	multipart.addBodyPart(mimeBodyPart);
    	 
    	message.setContent(multipart);
    	 
    	Transport.send(message);
     }
    
    /*public static void main(String[] args) throws ParseException, AddressException, MessagingException{
        SimpleDateFormat dateformat2 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strdate2 = "03-05-2019 09:30:00";
        String strdate3 = "03-05-2019 10:00:00";
        Date newdate = dateformat2.parse(strdate2);
        Date newdate1 = dateformat2.parse(strdate3);
        Library li = new Library();
        Client client1 = new Client("Józef", "Stalin", "js@zssr.ru", 52, "male");
        Client client2 = new Client("Józek", "Stalinowski", "js1@zssr.ru", 53, "male");
        li.AddClient(client1);
        li.AddClient(client2);
      
        Rent rent1 = new Rent(client1, newdate1, "Lord of the rings", 25.50);
        Rent rent2 = new Rent(client1, newdate, "Lord of the rings", 25.50);
        li.AddRent(client1, rent2);
        li.AddRentWithEmail(client1, rent1);
        //li.FindClient("js@zssr.ru");
        li.FindRent(newdate);
        //System.out.print(li.FindClient("js@zssr.ru")+"\n");
        li.DeleteRent(client1, rent1);
        li.DeleteClient(client1);
        li.stats();
    }*/
}
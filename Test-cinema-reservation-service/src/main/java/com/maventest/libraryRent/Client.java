package com.maventest.libraryRent;

import java.util.List;

public class Client {
	
    public String FirstName;
    public String LastName;
    public String Email;
    public int Age;
	public String Gender;
    public List<Rent> Borrowed;
    
    public String getFirstName() {
		return FirstName;
	}
    
    public String getLastName() {
		return LastName;
	}
    
    public void setLastName(String lastName) {
		LastName = lastName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}


	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public int getAge() {
		return Age;
	}

	public void setAge(int age) {
		Age = age;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	@Override
	public String toString() {
		return FirstName +" "+LastName;
	}

	public Client(String firstName, String lastName, String email, int age, String sex)
    {
        if (!NameIsValid(firstName))
        {
            throw new  IllegalArgumentException("Name not good");
        }
        if (!NameIsValid(lastName))
        {
            throw new  IllegalArgumentException("Family name not good");
        }
        if (!EmailIsValid(email))
        {
            throw new  IllegalArgumentException("PESEL should be in valid format");
        }
        if (!AgeIsValid(age))
        {
            throw new  IllegalArgumentException("Age should should be between 0-139");
        }
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Age = age;
        Gender = sex;

    }

    private static boolean NameIsValid(String input)
    {
        if (input.isEmpty() || input.equals(null)) return false;
        else
            return true;
    }
    
    private static boolean EmailIsValid(String input)
    {
        if (input.isEmpty() || input.equals(null)) return false;
        else
            return true;
    }


    private static boolean AgeIsValid(int input)
    {
        if (input > 0 && input < 139)
            return true;
        else
            return false;
    }
}


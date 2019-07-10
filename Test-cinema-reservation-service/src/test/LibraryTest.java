package com.maventest.libraryRent;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;


class LibraryTest {

	@Test
	public void testContains() {
		Library ob = new Library();
        ob.makeFriends("Stefan", "Zbigniew");
        ob.makeFriends("Stefan", "Anna");
        ob.makeFriends("Stefan", "9S");
        assertThat(ob.getFriendsList("Stefan")).contains("Zbigniew","Anna","9S");
	}
	

}

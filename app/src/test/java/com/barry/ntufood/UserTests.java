package com.barry.ntufood;

import com.google.firebase.Timestamp;

import org.junit.Test;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class UserTests {
    @Test
    public void testConstructorForename(){
        Timestamp now = Timestamp.now();
        User mUser = new User("Barry", "O'Connor", "n0813926@my.ntu.ac.uk", now, "0jZ8yxh4QdS8dO4sRvMpCLWisyM2");
        assertEquals(mUser.getForename(),"Barry");
    }

    @Test
    public void testConstructorSurname(){
        Timestamp now = Timestamp.now();
        User mUser = new User("Barry", "O'Connor", "n0813926@my.ntu.ac.uk", now, "0jZ8yxh4QdS8dO4sRvMpCLWisyM2");
        assertEquals(mUser.getSurname(),"O'Connor");
    }

    @Test
    public void testConstructorEmail(){
        Timestamp now = Timestamp.now();
        User mUser = new User("Barry", "O'Connor", "n0813926@my.ntu.ac.uk", now, "0jZ8yxh4QdS8dO4sRvMpCLWisyM2");
        assertEquals(mUser.getEmail(),"n0813926@my.ntu.ac.uk");
    }

    @Test
    public void testConstructorUid(){
        Timestamp now = Timestamp.now();
        User mUser = new User("Barry", "O'Connor", "n0813926@my.ntu.ac.uk", now, "0jZ8yxh4QdS8dO4sRvMpCLWisyM2");
        assertEquals(mUser.getUid(),"0jZ8yxh4QdS8dO4sRvMpCLWisyM2");
    }

    @Test
    public void testConstructorTimestamp(){
        Timestamp now = Timestamp.now();
        User mUser = new User("Barry", "O'Connor", "n0813926@my.ntu.ac.uk", now, "0jZ8yxh4QdS8dO4sRvMpCLWisyM2");
        assertEquals(mUser.getRegisteredDate(),now);
    }

    @Test
    public void testGetSetForename(){
        Timestamp now = Timestamp.now();
        User mUser = new User("Barry", "O'Connor", "n0813926@my.ntu.ac.uk", now, "0jZ8yxh4QdS8dO4sRvMpCLWisyM2");
        mUser.setForename("Christopher");
        
        assertEquals(mUser.getForename(),"Christopher");
    }

    @Test
    public void testGetSetSurname(){
        Timestamp now = Timestamp.now();
        User mUser = new User("Barry", "O'Connor", "n0813926@my.ntu.ac.uk", now, "0jZ8yxh4QdS8dO4sRvMpCLWisyM2");
        mUser.setSurname("Walken");
        assertEquals(mUser.getSurname(),"Walken");
    }

    @Test
    public void testGetSetEmail(){
        Timestamp now = Timestamp.now();
        User mUser = new User("Barry", "O'Connor", "n0813926@my.ntu.ac.uk", now, "0jZ8yxh4QdS8dO4sRvMpCLWisyM2");
        mUser.setEmail("me@barryoconnor.co.uk");
        assertEquals(mUser.getEmail(),"me@barryoconnor.co.uk");
    }

    @Test
    public void testGetSetUid(){
        Timestamp now = Timestamp.now();
        User mUser = new User("Barry", "O'Connor", "n0813926@my.ntu.ac.uk", now, "0jZ8yxh4QdS8dO4sRvMpCLWisyM2");
        mUser.setUid("XXXXX");
        assertEquals(mUser.getUid(),"XXXXX");
    }

    @Test
    public void testGetSetTimestamp() throws InterruptedException {
        Timestamp now = Timestamp.now();
        User mUser = new User("Barry", "O'Connor", "n0813926@my.ntu.ac.uk", now, "0jZ8yxh4QdS8dO4sRvMpCLWisyM2");
        TimeUnit.SECONDS.sleep(2);
        now = Timestamp.now();
        mUser.setRegisteredDate(now);
        assertEquals(mUser.getRegisteredDate(),now);
    }

    @Test
    public void testFormattedName() {
        Timestamp now = Timestamp.now();
        User mUser = new User("Barry", "O'Connor", "n0813926@my.ntu.ac.uk", now, "0jZ8yxh4QdS8dO4sRvMpCLWisyM2");
        mUser.setForename("Christopher");
        mUser.setSurname("Walken");
        assertEquals(mUser.formattedName(),"Christopher Walken");
    }
}

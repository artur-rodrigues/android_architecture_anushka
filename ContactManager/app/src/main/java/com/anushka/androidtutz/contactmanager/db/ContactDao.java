package com.anushka.androidtutz.contactmanager.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.anushka.androidtutz.contactmanager.db.entity.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    Long addContact(Contact contact);

    @Update
    void updateContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);

    @Query("SELECT * FROM contacts")
    List<Contact> findAllContacts();

    @Query("SELECT * FROM contacts WHERE contact_id == :id")
    Contact findContact(long id);
}

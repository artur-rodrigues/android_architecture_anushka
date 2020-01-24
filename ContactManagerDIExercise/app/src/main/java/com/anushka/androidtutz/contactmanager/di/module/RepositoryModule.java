package com.anushka.androidtutz.contactmanager.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.anushka.androidtutz.contactmanager.db.ContactDAO;
import com.anushka.androidtutz.contactmanager.db.ContactsAppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    ContactsAppDatabase provideDataBase(Context context) {
        return Room
                .databaseBuilder(context, ContactsAppDatabase.class,"ContactDB")
                .build();
    }

    @Provides
    @Singleton
    ContactDAO provideContactDAO(ContactsAppDatabase database) {
        return database.getContactDAO();
    }
}

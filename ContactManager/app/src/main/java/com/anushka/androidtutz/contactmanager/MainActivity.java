package com.anushka.androidtutz.contactmanager;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anushka.androidtutz.contactmanager.adapter.ContactsAdapter;
import com.anushka.androidtutz.contactmanager.db.ContactAppDataBase;
import com.anushka.androidtutz.contactmanager.db.ContactDao;
import com.anushka.androidtutz.contactmanager.db.entity.Contact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ContactsAdapter contactsAdapter;
    private ArrayList<Contact> contactArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContactAppDataBase dataBase;
    private CompositeDisposable disposable = new CompositeDisposable();

    private RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Log.i("MAIN_ACTIVITY", "Create DataBase");

            for(int i = 1; i <= 5; i++) {
                createContact("name " + i, "email " + i);
            }
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            Log.i("MAIN_ACTIVITY", "Open DataBase");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" Contacts Manager");

        recyclerView = findViewById(R.id.recycler_view_contacts);
        dataBase = Room.databaseBuilder(
                    getApplicationContext(),
                    ContactAppDataBase.class,
                    "contactDB")
                .addCallback(callback)
                .build();

        loadAllContacts();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> addAndEditContacts(false, null, -1));
    }

    private ContactDao getDAO() {
        return dataBase.getContactDAO();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void addAndEditContacts(final boolean isUpdate, final Contact contact, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.layout_add_contact, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        TextView contactTitle = view.findViewById(R.id.new_contact_title);
        final EditText newContact = view.findViewById(R.id.name);
        final EditText contactEmail = view.findViewById(R.id.email);

        contactTitle.setText(!isUpdate ? "Add New Contact" : "Edit Contact");

        if (isUpdate && contact != null) {
            newContact.setText(contact.getName());
            contactEmail.setText(contact.getEmail());
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(isUpdate ? "Update" : "Save", (dialogBox, id) -> {})
                .setNegativeButton("Delete",
                        (dialogBox, id) -> {

                            if (isUpdate) {

                                deleteContact(contact, position);
                            } else {

                                dialogBox.cancel();

                            }

                        });


        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {

            if (TextUtils.isEmpty(newContact.getText().toString())) {
                Toast.makeText(MainActivity.this, "Enter contact name!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                alertDialog.dismiss();
            }

            if (isUpdate && contact != null) {
                updateContact(newContact.getText().toString(), contactEmail.getText().toString(), position);
            } else {
                createContact(newContact.getText().toString(), contactEmail.getText().toString());
            }
        });
    }

    private void deleteContact(Contact contact, int position) {
        contactArrayList.remove(position);

        Disposable disposable = Completable
                .fromAction(() -> getDAO().deleteContact(contact))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> contactsAdapter.notifyDataSetChanged(),
                        Throwable::printStackTrace);

        this.disposable.add(disposable);
    }

    private void updateContact(String name, String email, int position) {
        Contact contact = contactArrayList.get(position);
        contact.setName(name);
        contact.setEmail(email);

        Disposable disposable = Completable
                .fromAction(() -> getDAO().updateContact(contact))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    contactArrayList.set(position, contact);
                    contactsAdapter.notifyDataSetChanged();
                }, Throwable::printStackTrace);

        this.disposable.add(disposable);
    }

    private void createContact(final String name, final String email) {
        Disposable disposable = Single
                .fromCallable(() -> getDAO().addContact(new Contact(0, name, email)))
                .subscribeOn(Schedulers.io())
                .flatMap((Function<Long, SingleSource<Contact>>) id ->
                        (SingleSource<Contact>) observer ->
                                observer.onSuccess(getDAO().findContact(id)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contact -> {
                    contactArrayList.add(0, contact);
                    contactsAdapter.notifyDataSetChanged();
                }, Throwable::printStackTrace);

        this.disposable.addAll(disposable);
    }

    private void loadAllContacts() {
        Disposable disposable = Single
                .fromCallable(() -> getDAO().findAllContacts())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contacts -> {
                    contactArrayList.addAll(contacts);
                    contactsAdapter = new ContactsAdapter(this, contactArrayList, MainActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(contactsAdapter);
                }, Throwable::printStackTrace);

        this.disposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        if(! disposable.isDisposed()) {
            disposable.clear();
            disposable.dispose();
        }

        super.onDestroy();
    }
}

package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Playlist extends AppCompatActivity implements AdapterView.OnItemClickListener {

    DatabaseReference databaseReference;
    ListView listView;
    List list = new ArrayList();
    ArrayAdapter adapter;
    Button swipe, history, pickArtist;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        pickArtist = findViewById(R.id.pickArtistBtn);
        history = findViewById(R.id.historyBtn);
        search = findViewById(R.id.search);
        swipe = findViewById(R.id.swipeBtn);
        listView = (ListView)findViewById(R.id.listviewtxt);
        adapter = new ArrayAdapter<String>(Playlist.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        String putTogetter = "Users/"+Login.user;
        databaseReference = FirebaseDatabase.getInstance().getReference(putTogetter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Playlist.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        swipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Swipes.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), History.class);
                startActivity(intent);
            }
        });

        pickArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PickArtist.class);
                startActivity(intent);
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = Objects.requireNonNull(snapshot.getValue(Testing_HelperClass.class)).toString();
                List<String> song = Arrays.asList(value.split(","));
                if(song.get(1).equals(" Like")){
                    list.add(song.get(0) + "," + song.get(2));

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(Playlist.this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String m = parent.getItemAtPosition(position).toString();
        List<String> song = Arrays.asList(m.split(","));
        String url_song = song.get(1).trim();
        Toast.makeText(getApplicationContext(),"Redirecting to " + url_song, Toast.LENGTH_SHORT).show();
        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(url_song));
        startActivity(viewIntent);

    }
}
package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class PickArtist extends AppCompatActivity {

    Button btn;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_artist);

        btn = findViewById(R.id.submit);

        CheckBox Tim, George, Garth, Carrie;
        CheckBox Miles, John, Duke, Louis;
        CheckBox Beetles, Rolling, U2, Grateful;
        CheckBox Taylor, Justin, Ariana, Selena;

        Tim = findViewById(R.id.Tim);
        George = findViewById(R.id.George);
        Garth = findViewById(R.id.Garth);
        Carrie = findViewById(R.id.Carrie);

        Miles = findViewById(R.id.Miles);
        John = findViewById(R.id.John);
        Duke = findViewById(R.id.Duke);
        Louis = findViewById(R.id.Louis);

        Beetles = findViewById(R.id.Beetles);
        Rolling = findViewById(R.id.Rolling);
        U2 = findViewById(R.id.U2);
        Grateful = findViewById(R.id.Grateful);

        Taylor = findViewById(R.id.Taylor);
        Justin = findViewById(R.id.Justin);
        Ariana = findViewById(R.id.Ariana);
        Selena = findViewById(R.id.Selena);

        StringBuffer result = new StringBuffer();
        result.append("My favourite artists: ");

        if (Tim.isChecked())
        {
            result.append("Tim McGraw");
            counter++;
        }

        if (George.isChecked())
        {
            result.append("George Strait");
            counter++;
        }

        if (Garth.isChecked())
        {
            result.append("Garth Brooks");
            counter++;
        }

        if (Carrie.isChecked())
        {
            result.append("Carrie Underwood");
            counter++;
        }

        if (Miles.isChecked())
        {
            result.append("Miles davis");
            counter++;
        }

        if (John.isChecked())
        {
            result.append("John Coltrane");
            counter++;
        }

        if (Duke.isChecked())
        {
            result.append("Duke Ellington");
            counter++;
        }

        if (Louis.isChecked())
        {
            result.append("Louis Armstrong");
            counter++;
        }

        if (Beetles.isChecked())
        {
            result.append("The Beetles");
            counter++;
        }

        if (Rolling.isChecked())
        {
            result.append("The Rolling Stone");
            counter++;
        }

        if (U2.isChecked())
        {
            result.append("U2");
            counter++;
        }

        if (Grateful.isChecked())
        {
            result.append("The Grateful Dead");
            counter++;
        }

        if (Taylor.isChecked())
        {
            result.append("Taylor Swift");
            counter++;
        }

        if (Justin.isChecked())
        {
            result.append("Justin Bieber");
            counter++;
        }

        if (Ariana.isChecked())
        {
            result.append("Ariana Grande");
            counter++;
        }

        if (Selena.isChecked())
        {
            result.append("Selena Gomez");
            counter++;
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter < 3)
                {
                    Toast.makeText(PickArtist.this,"You can choose more artist/s", Toast.LENGTH_SHORT).show();
                }
                if(counter > 3)
                {
                    Toast.makeText(PickArtist.this,"You can only pick 3 artists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
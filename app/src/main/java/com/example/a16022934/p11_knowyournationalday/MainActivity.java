package com.example.a16022934.p11_knowyournationalday;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.MainThread;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout passPhrase = (LinearLayout) inflater.inflate(R.layout.passphrase, null);
        final EditText etPassphrase = (EditText) passPhrase.findViewById(R.id.editTextPassPhrase);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean logged = prefs.getBoolean("logged", false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Login")
                .setView(passPhrase)
                .setPositiveButton("Done", null)
                .setNegativeButton("No Access Code", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (etPassphrase.getText().toString().equals("738964")) {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            SharedPreferences.Editor prefEdit = prefs.edit();
                            prefEdit.putBoolean("logged", true);
                            prefEdit.apply();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "Code is wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        if (!logged) {
            alertDialog.show();
        }
    }

    ListView lvFacts;
    ArrayList<String> alFacts;
    ArrayAdapter aaFacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvFacts = findViewById(R.id.lvFacts);
        alFacts = new ArrayList<>();
        alFacts.add("Singapore National Day is on 9 Aug");
        alFacts.add("Singapore is 53 Years Old");
        alFacts.add("Theme is We are Singapore");
        aaFacts = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alFacts);
        lvFacts.setAdapter(aaFacts);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Tally against the respective action item clicked
        //  and implement the appropriate action
        if (item.getItemId() == R.id.share) {
            String[] list = new String[]{"Email", "SMS"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the way to enrich your friend")
                    .setItems(list, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Snackbar sb = Snackbar.make(lvFacts, "Sent an Email", Snackbar.LENGTH_SHORT);
                                sb.show();
                            } else {
                                Snackbar sb = Snackbar.make(lvFacts, "Sent a SMS", Snackbar.LENGTH_SHORT);
                                sb.show();
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if (item.getItemId() == R.id.quiz) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ConstraintLayout quiz = (ConstraintLayout) inflater.inflate(R.layout.quiz, null);
            final RadioGroup qn1 = quiz.findViewById(R.id.rgqn1);
            final RadioGroup qn2 = quiz.findViewById(R.id.rgqn2);
            final RadioGroup qn3 = quiz.findViewById(R.id.rgqn3);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please Enter")
                    .setView(quiz)
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            if (qn1.getCheckedRadioButtonId() == R.id.rb2 && qn2.getCheckedRadioButtonId() == R.id.rb3 && qn3.getCheckedRadioButtonId() == R.id.rb5) {
                                //3 Correct
                                Toast.makeText(MainActivity.this, "Score: 3/3\nSwee Lah!", Toast.LENGTH_SHORT).show();
                            } else if ((qn1.getCheckedRadioButtonId() == R.id.rb2 && qn2.getCheckedRadioButtonId() == R.id.rb3)
                                    || (qn1.getCheckedRadioButtonId() == R.id.rb2 && qn3.getCheckedRadioButtonId() == R.id.rb5)
                                    || (qn2.getCheckedRadioButtonId() == R.id.rb3 && qn3.getCheckedRadioButtonId() == R.id.rb5)) {
                                //2 Correct 1 Wrong
                                Toast.makeText(MainActivity.this, "Score: 2/3\nGood Job Lah!", Toast.LENGTH_SHORT).show();
                            } else if (qn1.getCheckedRadioButtonId() == R.id.rb2 || qn2.getCheckedRadioButtonId() == R.id.rb3 || qn3.getCheckedRadioButtonId() == R.id.rb5) {
                                //1 Correct 2 Wrong
                                Toast.makeText(MainActivity.this, "Score: 1/3\nNot Bad Lah!", Toast.LENGTH_SHORT).show();
                            } else {
                                //3 Wrong
                                Toast.makeText(MainActivity.this, "Score: 0/3\nWork Harder Lah!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if (item.getItemId() == R.id.quit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Quit?")
                    // Set text for the positive button and the corresponding
                    //  OnClickListener when it is clicked
                    .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    // Set text for the negative button and the corresponding
                    //  OnClickListener when it is clicked
                    .setNegativeButton("Not Really", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}

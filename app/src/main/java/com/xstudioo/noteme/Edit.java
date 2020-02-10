package com.xstudioo.noteme;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Calendar;

public class Edit extends AppCompatActivity {
    Toolbar toolbar;
    EditText nTitle,nContent;
    Calendar c;
    String todaysDate;
    String currentTime;
    long nId;
    String color;
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent i = getIntent();
        nId = i.getLongExtra("ID",0);
        SimpleDatabase db = new SimpleDatabase(this);
        Note note = db.getNote(nId);

        final String title = note.getTitle();
        String content = note.getContent();
        nTitle = findViewById(R.id.noteTitle);
        nContent = findViewById(R.id.noteDetails);
        nTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                getSupportActionBar().setTitle(title);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        nTitle.setText(title);
        nContent.setText(content);

        // set current date and time
        c = Calendar.getInstance();
        todaysDate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
        Log.d("DATE", "Date: "+todaysDate);
        currentTime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE));
        Log.d("TIME", "Time: "+currentTime);

        view = this.getWindow().getDecorView();
        if(note.getColor().equals("#ffffcf")){
            view.setBackgroundResource(R.color.fab1_color);
        }else if(note.getColor().equals("#d9ffdf")){
            view.setBackgroundResource(R.color.fab2_color);
        }else if(note.getColor().equals("#d9ffff")){
            view.setBackgroundResource(R.color.fab3_color);
        }else
            view.setBackgroundResource(R.color.def);

    }

    public void goFab1(View v){
        view.setBackgroundResource(R.color.fab1_color);
        color="#ffffcf";
    }
    public void goFab2(View v){
        view.setBackgroundResource(R.color.fab2_color);
        color="#d9ffdf";
    }
    public void goFab3(View v){
        view.setBackgroundResource(R.color.fab3_color);
        color="#d9ffff";
    }


    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if(item.getItemId() == R.id.save){
            Note note = new Note(nId,nTitle.getText().toString(),nContent.getText().toString(),todaysDate,currentTime,color);
            Log.d("EDITED", "edited: before saving id -> " + note.getId());
            SimpleDatabase sDB = new SimpleDatabase(getApplicationContext());
            long id = sDB.editNote(note);
            Log.d("EDITED", "EDIT: id " + id);
            goToMain();
            Toast.makeText(this, "Note Edited.", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.delete){
            SimpleDatabase db = new SimpleDatabase(getApplicationContext());
            db.deleteNote(nId);
            Toast.makeText(getApplicationContext(),"Note Deleted",Toast.LENGTH_SHORT).show();
            goToMain();
//            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
//            onBackPressed();
        }else if(item.getItemId() == R.id.search){
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setQueryHint("Search text within note");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    //ArrayAdapter<> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nContent);
//                    String note = nContent.getText().toString();
//                    if(note.contains(s)){
//                        int first = note.indexOf(s);
//                        int last = note.lastIndexOf(s);
//                        String searchedItem = note.substring(first, last);
//                    }
                    //adapter.getFilter().filter(s);
                    return false;
                }
            });

        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }


}

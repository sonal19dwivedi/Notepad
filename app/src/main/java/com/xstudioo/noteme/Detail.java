package com.xstudioo.noteme;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Detail extends AppCompatActivity {
    long id;
    View view;
    String color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        

        Intent i = getIntent();
        id = i.getLongExtra("ID",0);
        SimpleDatabase db = new SimpleDatabase(this);
        Note note = db.getNote(id);
        getSupportActionBar().setTitle(note.getTitle());
        TextView details = findViewById(R.id.noteDesc);
        details.setText(note.getContent());
        details.setMovementMethod(new ScrollingMovementMethod());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDatabase db = new SimpleDatabase(getApplicationContext());
                db.deleteNote(id);
                Toast.makeText(getApplicationContext(),"Note Deleted",Toast.LENGTH_SHORT).show();
                goToMain();
            }
        });
        /*
        **
        Added by C.Y
        **
         */
        //********************************************************
        FloatingActionButton shareFab = findViewById(R.id.shareFab);
        shareFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                showShareDialog();
            }
        });
        //********************************************************

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.edit){
            Intent i = new Intent(this,Edit.class);
            i.putExtra("ID",id);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void goToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    /*
    **
    Added by C.Y
    **
     */
    private void showShareDialog(){
        SimpleDatabase db = new SimpleDatabase(this);
        final Note note = db.getNote(id);
        AlertDialog.Builder builder = new AlertDialog.Builder(Detail.this);
        builder.setTitle("Share Via");
        builder.setItems(new String[]{"Email", "More"}, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
                switch(which){
                    case 0: //Email
                        sendMail(note);
                        break;
                    case 1: //System share
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                        intent.putExtra(Intent.EXTRA_TEXT, "I like this! Recommend!");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(intent, "share"));
                        break;
                    default:
                        break;
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void sendMail(Note note){
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        email.setType("plain/text");

        String subjectStr = "Share Note";
        String emailBody = "This is a shared note: \n" +
                "Title: " + note.getTitle() + '\n' +
                "Content: " + note.getContent() + '\n' +
                "Create Date: " + note.getDate() + '\n' +
                "Create Time: " + note.getTime() + '\n';
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, subjectStr);
        email.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);
        startActivityForResult(Intent.createChooser(email, "Select the email content"), 1001);
    }

}

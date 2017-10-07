package thapliyal.abhishek;

import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class chat extends AppCompatActivity {
    private Button btn_send_msg,remove;
    private EditText input_msg;

    //To diplay all messages in scrollview
    private TextView chat_text;

    String user_name,room_name,time;
    private DatabaseReference root ;
    private String temp_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //   remove = (Button)findViewById(R.id.remove);
        btn_send_msg = (Button) findViewById(R.id.btn_send);
        input_msg = (EditText) findViewById(R.id.msg_input);
        chat_text = (TextView) findViewById(R.id.textView);


        //we get these values from Mainactivity.java
        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();


        //Actionbar , which represent current group name.
        setTitle(" Group Name - "+room_name);


        //.child() will take you to access data inside group or current group object
        root = FirebaseDatabase.getInstance().getReference().child(room_name);

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();

                //this will return unique key of each message
                temp_key = root.push().getKey();

                //updateChildren is firebase method to append data in current group.
                root.updateChildren(map);

                //insert the data in database at current group name
                DatabaseReference message_root = root.child(temp_key);
                time= formatTimeStamp();


                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg",input_msg.getText().toString());
                map2.put("time",time);
                message_root.updateChildren(map2);
                input_msg.setText("");
            }
        });






        //to listen for data changes
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private String chat_msg,chat_user_name,chat_time;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){

            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();
            chat_time=(String) ((DataSnapshot)i.next()).getValue();

            chat_text.append(chat_user_name +" : "+chat_msg + " "+chat_time+" \n");


        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notify)
                .setContentTitle(chat_user_name)
                .setContentText(chat_msg);
// Sets an ID for the notification
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.del:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are You sure? You want to delete group");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //             FirebaseDatabase.getInstance().getReference(user_name).removeValue();
                        FirebaseDatabase.getInstance().getReference().child(room_name).removeValue();
                        Intent ii = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(ii);

                    }
                });


                builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
    public static String formatTimeStamp(){
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

}


package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.firebasetest.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Handler meetingHandler;
    private Handler positionHandler;


Button boutonPosition;
TextView id;
TextView name;
TextView lat;
TextView lon;
Localisation localisation;
Spinner spinnerMeeting;
ArrayAdapter<String> spinnerAdapter;
ArrayAdapter<String> spinnerNameAdapter;
Spinner spinnerNames;
FirebaseFirestore datastore;
Meeting meetings[] = new Meeting[10];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boutonPosition=(Button) findViewById(R.id.button3);
        spinnerMeeting=(Spinner) findViewById(R.id.spinnerMeeting);
        spinnerNames=(Spinner) findViewById(R.id.spinnerName);

        datastore = FirebaseFirestore.getInstance();

        boutonPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCurrentPosition();
            }
        });
        this.localisation = new Localisation(this,this);

        meetingHandler = new Handler();
        meetingHandler.postDelayed(meetingRun,5000); // on redemande toute les 500ms

        spinnerMeeting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getMeetingNameList(spinnerAdapter.getItem(i));
                Log.d("TAG", spinnerAdapter.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,new ArrayList<String>());
        spinnerMeeting.setAdapter(spinnerAdapter);

        spinnerNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,new ArrayList<String>());
        spinnerNames.setAdapter(spinnerNameAdapter);
    }

    private Runnable meetingRun = new Runnable() {
        @Override
        public void run() {
            refreshMeetingList();
            meetingHandler.postDelayed(this,5000);
        }
    };

    private Runnable positionRun = new Runnable() {
        @Override
        public void run() {
            addCurrentPosition();
            positionHandler.postDelayed(this,5000);
        }
    };

    public void getMeetingNameList(final String meetingName){
        //final ArrayList<String> newMeeting = new ArrayList<>();
        final List<Map<String, Object>> meetingData = new ArrayList<Map<String, Object>>();
        final ArrayList<String> meetingNames = new ArrayList<>();
        datastore.collection("meeting")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                              if(meetingName==document.getId()){
                                  meetingData.add(document.getData());

                              }
                            }
                        } else {
                            Log.d("doc", "Error getting documents: ", task.getException());
                        }
                        spinnerNameAdapter.clear();
                        spinnerNameAdapter.addAll(meetingData.toString());
                        spinnerNameAdapter.notifyDataSetChanged();
                        spinnerNames.setAdapter(spinnerNameAdapter);

                    }
                });
        spinnerNames.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, meetingNames));
    }

    public void refreshMeetingList(){
        final ArrayList<String> meetingId = new ArrayList<>();
        //final ArrayList<String> newMeeting = new ArrayList<>();
        final List<Map<String, Object>> meetingData = new ArrayList<Map<String, Object>>();
        //final Integer[] i = {0};
        final int[] j = new int[1];
        datastore.collection("meeting")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("doc", document.getId() + " => " + document.getData());
                                meetingId.add(document.getId());
                                meetingData.add(document.getData());
                                //i[0] = i[0] +1;

                            }
                        } else {
                            Log.d("doc", "Error getting documents: ", task.getException());
                        }
                        spinnerAdapter.clear();
                        spinnerAdapter.addAll(meetingId);
                        spinnerAdapter.notifyDataSetChanged();
                        spinnerMeeting.setAdapter(spinnerAdapter);
                    }
                });
    }


    public void addCurrentPosition(){
        User position =  setPosition();
        datastore.collection("position").document(position.getId()).set(createMap(position));
    }


    public Map<String, Object> createMap(User position){
        Map<String, Object> pos = new HashMap<>();
        pos.put("nom", position.getNom());
        pos.put("latitude", position.getLatitude());
        pos.put("longitude", position.getLongitude());
        return pos;

    }

    public User setPosition(){
        id = (EditText) findViewById(R.id.editId);
        name = (EditText) findViewById(R.id.editName);
        lat = (TextView) findViewById(R.id.currentLatitude);
        lon = (TextView) findViewById(R.id.currentLongitude);

        String idS = id.getText().toString();
        String nameS = name.getText().toString();
        String latS = lat.getText().toString();
        String lonS = lon.getText().toString();
        return new User(idS,nameS,latS,lonS);
    }

}

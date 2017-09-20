package com.codreamstudio.parse_server;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseLiveQueryClient;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SubscriptionHandling;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button guzik = (Button)findViewById(R.id.button);
        guzik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ParseObject gameScore = new ParseObject("GameScore3");
                gameScore.put("score", 1337);
                gameScore.put("playerName", "Sean Plott8");
                gameScore.put("cheatMode", false);
                gameScore.saveInBackground();
            }
        });
        Parse.initialize(this);
        ParseLiveQueryClient parseLiveQueryClient = null;
        try {
            parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient(new URI("ws://partygoer.org:8080"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore3");

        query.whereEqualTo("playerName", "Sean Plott8");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null) {
                    for (ParseObject object : objects) {
                        Log.i("Player Name", "Player: "+object.getString("playerName"));
                    }
                }
                else{
                    Log.i("ParseException", e.toString());
                }
            }
        });
        SubscriptionHandling<ParseObject> subscriptionHandling = parseLiveQueryClient.subscribe(query);
        subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, new SubscriptionHandling.HandleEventCallback<ParseObject>() {
            @Override
            public void onEvent(ParseQuery<ParseObject> query, ParseObject object) {
                Log.i("Brawo","Coś się dzieje1");
            }
        });
        subscriptionHandling.handleEvents(new SubscriptionHandling.HandleEventsCallback<ParseObject>() {
            @Override
            public void onEvents(ParseQuery<ParseObject> query, SubscriptionHandling.Event event, ParseObject object) {
                Log.i("Brawo","Coś się dzieje");
            }
        });


        }
}

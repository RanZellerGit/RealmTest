package com.example.ranzeller.realmtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.Required;




public class MainActivity extends AppCompatActivity {


	public static final String TAG = "MainActivity";

	// Define you model class by extending the RealmObject


	private Button addNote;
	private Realm realm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addNote = (Button) findViewById(R.id.addNoteBtn);

		RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
		realm = Realm.getInstance(realmConfig);
		addNote.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				fetchNotesAndSave();
			}
		});
	}


	private void fetchNotesAndSave() {
		InputStream stream;
		try {
			stream = getAssets().open("notes.json");
		} catch (IOException e) {
			return ;
		}

		Gson gson = new GsonBuilder().create();

		JsonElement json = new JsonParser().parse(new InputStreamReader(stream));
		Note note = gson.fromJson(json, Note.class);

		realm.beginTransaction();
		realm.where(Note.class).findAll().deleteAllFromRealm();
		realm.copyToRealmOrUpdate(note);
		realm.commitTransaction();

		String text = "" + realm.where(Template.class).findAll().size();
		Log.d("MainActivity",text);
		Toast.makeText(this,text,Toast.LENGTH_LONG);
	}

}

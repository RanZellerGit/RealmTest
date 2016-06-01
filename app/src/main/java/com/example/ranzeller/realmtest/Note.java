package com.example.ranzeller.realmtest;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ranzeller on 01/06/16.
 */
public class Note extends RealmObject {

	@PrimaryKey
	private String id;

	private Template template;

	// other primitive fields, getters & setters
}

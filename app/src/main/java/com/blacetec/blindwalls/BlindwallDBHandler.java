package com.blacetec.blindwalls;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class BlindwallDBHandler extends SQLiteOpenHelper {

    private static final String TAG = BlindwallDBHandler.class.getSimpleName();

    public BlindwallDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "onCreate called");

        sqLiteDatabase.execSQL("CREATE TABLE `BlindWall` (\n" +
            "\t`id`\tINTEGER,\n" +
            "\t`title`\tTEXT,\n" +
            "\t`address`\tTEXT,\n" +
            "\t`addressNumber`\tINTEGER,\n" +
            "\t`photographer`\tTEXT,\n" +
            "\t`description`\tTEXT,\n" +
            "\t`imageurl`\tTEXT,\n" +
            "\t`material`\tTEXT\n" +
            ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "onUpgrade upgrading database.");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS BlindWall");
        this.onCreate(sqLiteDatabase);
    }

    public ArrayList<BlindWall> getAllWalls() {
        Log.i(TAG, "getAllWalls called");

        String query = "SELECT * FROM BlindWall";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<BlindWall> blindWalls = new ArrayList<>();

        cursor.moveToFirst();
        while(!cursor.isLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            int addressNumber = cursor.getInt(cursor.getColumnIndex("addressNumber"));
            String photographer = cursor.getString(cursor.getColumnIndex("photographer"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            String imageurl = cursor.getString(cursor.getColumnIndex("imageurl"));
            String material = cursor.getString(cursor.getColumnIndex("material"));

            blindWalls.add(new BlindWall(id, title, address, photographer, addressNumber, description, imageurl, material));
            cursor.moveToNext();
        }

        db.close();
        return blindWalls;
    }

    public void addBlindWall(BlindWall blindWall) {
        ContentValues values = new ContentValues();
        values.put("id", blindWall.getId());
        values.put("title", blindWall.getTitle());
        values.put("address", blindWall.getAddress());
        values.put("addressNumber", blindWall.getAddressNumber());
        values.put("photographer", blindWall.getPhotographer());
        values.put("description", blindWall.getDescription());
        values.put("imageurl", blindWall.getImageUrl());
        values.put("material", blindWall.getMaterial());

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM BlindWall WHERE id = " + blindWall.getId(), null);

        if(cursor.getCount() <= 0) {
            db.insert("BlindWall", null, values);
        } else {
            values.remove("id");
            db.update("BlindWall",
                    values,
                    "id = " + blindWall.getId(),
                    null);
        }

        db.close();
    }

    public boolean isDatabaseEmpty() {
        String query = "SELECT * FROM BlindWall";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        return cursor.getCount()<=0;

    }
}

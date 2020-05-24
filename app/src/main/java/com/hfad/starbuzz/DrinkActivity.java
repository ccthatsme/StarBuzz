package com.hfad.starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {

    public static final String EXTRA_DRINKID = "drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        //get the drink from the intent
        int drinkId = (Integer)getIntent().getExtras().get(EXTRA_DRINKID);
        //this was getting the data from our drink class old code
       // Drink drink = Drink.drinks[drinkId];

        //create a cursor
        SQLiteOpenHelper starbuzzdatabasehelper = new StarBuzzDatabaseHelper(this);

        try {
            SQLiteDatabase db = starbuzzdatabasehelper.getReadableDatabase();

            Cursor cursor = db.query("DRINK", new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"}, "_id = ?", new String[]{Integer.toString(drinkId)}, null, null, null, null);

            if (cursor.moveToFirst()) {
                //get drink details from the cursor
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);


                //populate the drink name
                TextView name = (TextView) findViewById(R.id.name);
                //name.setText(drink.getName());
                name.setText(nameText);

                //populate the drink description
                TextView description = (TextView) findViewById(R.id.description);
                //description.setText(drink.getDescription());
                description.setText(descriptionText);

                //populate the photo
                ImageView photo = (ImageView) findViewById(R.id.photo);
                // photo.setImageResource(drink.getImageResourceId());
                // photo.setContentDescription(drink.getName());
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
            }

            cursor.close();
            db.close();
        } catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }


    }
}

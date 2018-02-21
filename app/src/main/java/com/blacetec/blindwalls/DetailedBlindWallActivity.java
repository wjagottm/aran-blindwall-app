package com.blacetec.blindwalls;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class DetailedBlindWallActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detailed_product);

        Intent intent = getIntent();
        BlindWall blindwall = (BlindWall) intent.getSerializableExtra("BLINDWALL");

        ImageView imageView = (ImageView) findViewById(R.id.imageview);
        TextView titleView = (TextView) findViewById(R.id.title);
        TextView materialView = (TextView) findViewById(R.id.material);
        TextView addressView = (TextView) findViewById(R.id.address);
        TextView photographView = (TextView) findViewById(R.id.photographer);
        TextView longDescriptionView = (TextView) findViewById(R.id.longDescription);

        titleView.setText(blindwall.getTitle());
        materialView.setText("Material: " + blindwall.getMaterial());
        addressView.setText("Address: " + blindwall.getAddress() + " " + blindwall.getAddressNumber());
        photographView.setText("Photographer: " + blindwall.getPhotographer());
        longDescriptionView.setText(blindwall.getDescription());

        if(blindwall.getImage()!=null) {
            Bitmap imageMap = BitmapFactory.decodeByteArray(blindwall.getImage(), 0, blindwall.getImage().length);
            imageView.setImageBitmap(imageMap);
        }

    }
}

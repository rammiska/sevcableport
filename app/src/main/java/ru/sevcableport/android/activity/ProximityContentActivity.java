package ru.sevcableport.android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import ru.sevcableport.android.R;

public class ProximityContentActivity extends AppCompatActivity {

    public static final String SUBTITLE = "subttile";

    public static final String TEXT = "text";

    public static final String PHOTO_RESOURCE_ID = "photo_resource_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        Intent intent = getIntent();
        String subtitle = intent.getStringExtra(SUBTITLE);
        String text = intent.getStringExtra(TEXT);
        int photoResourceId = intent.getIntExtra(PHOTO_RESOURCE_ID, 0);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView subtitleView = findViewById(R.id.subtile);
        subtitleView.setText(subtitle);

        TextView textView = findViewById(R.id.text);
        textView.setText(text);

        ImageView backdropView = findViewById(R.id.backdrop);
        backdropView.setImageResource(photoResourceId);
    }
}

package com.google.ar.sceneform.samples.augmentedfaces.Kallpa.Lip;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.ar.core.AugmentedFace;
import com.google.ar.sceneform.samples.augmentedfaces.AugmentedFacesActivity;
import com.google.ar.sceneform.samples.augmentedfaces.Kallpa.Lip.fragments.Lip1;
import com.google.ar.sceneform.samples.augmentedfaces.OnSwipeTouchListener;
import com.google.ar.sceneform.samples.augmentedfaces.R;
import com.google.ar.sceneform.ux.AugmentedFaceNode;

import java.util.HashMap;

public class Lip extends AppCompatActivity {

    ImageView rostro, labios, ojos, toque;
    private final HashMap<AugmentedFace, AugmentedFaceNode> faceNodeMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lip);

        findViewById(R.id.ActivityLip).setOnTouchListener(new OnSwipeTouchListener(Lip.this){
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
            }

            @Override
            public void onSwipeBottom() {
                super.onSwipeBottom();
            }
        });

        rostro = findViewById(R.id.rostro);
        labios = findViewById(R.id.labios);
        ojos = findViewById(R.id.ojos);
        toque = findViewById(R.id.toque);

        rostro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lip.this, AugmentedFacesActivity.class);
                startActivity(intent);
            }
        });

        labios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lip.this, AugmentedFacesActivity.class);
                startActivity(intent);
            }
        });

        toque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lip.this, AugmentedFacesActivity.class);
                startActivity(intent);
            }
        });


        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment start_frag = fragmentManager.findFragmentById(R.id.frame);
        if(start_frag == null) {
            start_frag = new Lip1();
            fragmentManager.beginTransaction().add(R.id.frame, start_frag).commit();
        }

    }
}

package com.example.myar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;

import android.widget.Button;
import android.widget.Toast;

import com.example.myar.ui.main.SectionsPagerAdapter;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.ar.core.Anchor;

import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Sun;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;

import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;
    public Plane.Type planeType;

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;
    TabItem tabProduct;
    TabItem tabAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabLayout = findViewById(R.id.tablayout);
        tabProduct = findViewById(R.id.tabProduct);
        tabAccount = findViewById(R.id.tabAccount);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        arFragment.setOnTapArPlaneListener((HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {

            //Renderable mode in AR app

            ModelRenderable.builder()
                    .setSource(this, Uri.parse("ArcticFox_Posed.sfb"))
                    .build()
                    .thenAccept(modelRenderable -> addModelToScene(modelRenderable, hitResult, planeType))
                    .exceptionally(throwable -> {
                        Toast toast = Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return null;
                    });
        });

        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(view -> onClear());

     /*   Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(view -> removeAnchorNode()); */

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                            R.color.colorAccent));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                            R.color.colorAccent));
                    getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,
                            R.color.colorAccent));

                } else {
                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                            R.color.colorPrimaryDark));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                            R.color.colorPrimaryDark));
                    getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,
                            R.color.colorPrimaryDark));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    private void addModelToScene(ModelRenderable modelRenderable, HitResult hitResult, Plane.Type planeType) {
        Anchor anchor = hitResult.createAnchor();
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.select();
        arFragment.getArSceneView().getScene().addChild(anchorNode);

        if (planeType == Plane.Type.HORIZONTAL_DOWNWARD_FACING) {
            Node downward = new Node();
            downward.setParent(transformableNode);
            downward.setLocalPosition(new Vector3(0, 1, 0));
            downward.setLocalRotation(new Quaternion(0, 0, 1, 0));
            downward.setRenderable(modelRenderable);
        } else if (planeType == Plane.Type.VERTICAL) {
            Node downward = new Node();
            downward.setParent(transformableNode);
            //downward.setLookDirection(curPlaneNormal.negated());
            downward.setRenderable(modelRenderable);
        } else {
            transformableNode.setRenderable(modelRenderable);
        }
    }

    private void onClear() {
        List<Node> children = new ArrayList<>(arFragment.getArSceneView().getScene().getChildren());
        for (Node node : children) {
            if (node instanceof AnchorNode) {
                if (((AnchorNode) node).getAnchor() != null) {
                    Objects.requireNonNull(((AnchorNode) node).getAnchor()).detach();
                }
            }
            if (!(node instanceof Camera) && !(node instanceof Sun)) {
                node.setParent(null);
            }
        }
    }
}

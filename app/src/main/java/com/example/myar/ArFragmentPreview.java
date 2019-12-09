package com.example.myar;

import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.ux.ArFragment;
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
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArFragmentPreview extends AppCompatActivity {

    private ArFragment arFragment;
    public Plane.Type planeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arpreview_layout);
        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.arFragment);
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

        Button backButton = findViewById(R.id.deleteButton);
        backButton.setOnClickListener(v -> this.finish());

        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(view -> onClear());

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
          //  downward.setLookDirection(new Vector3());
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

package com.example.myar;

import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.collision.Box;
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
import com.google.ar.sceneform.ux.PlaneDiscoveryController;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArFragmentPreview extends AppCompatActivity {

    private ArFragment arFragment;
    public Plane.Type planeType;
    ModelRenderable Fox, Vase, Plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.arpreview_activity);
        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.arFragment);
        arFragment.getPlaneDiscoveryController().hide();
        arFragment.getPlaneDiscoveryController().setInstructionView(null);
        arFragment.setOnTapArPlaneListener((HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {

            //Renderable mode in AR app

            ModelRenderable.builder()
                    .setSource(this, Uri.parse("FiddleleafFigPottedPlant.sfb"))
                    .build()
                    .thenAccept(modelRenderable -> addModelToScene(modelRenderable,
                                hitResult, planeType))
                    .exceptionally(throwable -> {
                        Toast toast = Toast.makeText(this, "Unable to load any renderable", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return null;
                    });
        });


        Button backButton = findViewById(R.id.backButton);
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
        arFragment.getArSceneView().getScene().addChild(anchorNode);

        if (planeType == Plane.Type.HORIZONTAL_DOWNWARD_FACING) {
            transformableNode.setParent(transformableNode);
            transformableNode.setLocalPosition(new Vector3(0, 1, 0));
            transformableNode.setLocalRotation(new Quaternion(0, 0, 1, 0));
            transformableNode.setRenderable(modelRenderable);
        } else if (planeType == Plane.Type.VERTICAL) {
            transformableNode.setParent(transformableNode);
            transformableNode.setRenderable(modelRenderable);
        } else {
            transformableNode.setRenderable(modelRenderable);
            transformableNode.select();
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

package com.example.myar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private Plane.Type planeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

        arFragment.setOnTapArPlaneListener((HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {

            Anchor anchor = hitResult.createAnchor();

         //Renderable mode in AR app

            ModelRenderable.builder()
                    .setSource(this, Uri.parse("ArcticFox_Posed.sfb"))
                    .build()
                    .thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable, planeType))
                    .exceptionally(throwable -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Error" + throwable.getMessage()).show();
                        return null;
                    });
        });
    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable, Plane.Type planeType) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);

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
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }

}

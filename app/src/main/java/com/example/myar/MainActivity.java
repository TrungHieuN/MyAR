package com.example.myar;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;

import android.widget.Button;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;

import com.google.ar.core.Pose;
import com.google.ar.core.Trackable;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Sun;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.PlaneRenderer;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;
    public Plane.Type planeType;
    private Anchor anchor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.clearButton);
        button.setOnClickListener(view -> onClear());
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

        Objects.requireNonNull(arFragment).setOnTapArPlaneListener((HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {

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
    }

    private void addModelToScene(ModelRenderable modelRenderable, HitResult hitResult, Plane.Type planeType) {
        anchor = hitResult.createAnchor();
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


    @SuppressWarnings("unused")
    private MotionEvent getNewTapOnBottomCloud(MotionEvent tap, Frame frame) {
        if (tap == null) {
            return null;
        }
        for (int i = 0; i < 70; i++) {
            for (HitResult hitResult : frame.hitTest(tap)) {
                Trackable trackable = hitResult.getTrackable();
                if (trackable instanceof Plane && ((Plane) trackable).isPoseInPolygon(hitResult.getHitPose()) && ((Plane) trackable).getType() == Plane.Type.HORIZONTAL_UPWARD_FACING) {

                    float distance = PlaneRenderer.class(hitResult.getHitPose(), Objects.requireNonNull(arFragment.getArSceneView().getArFrame()).getCamera().getPose());
                    anchor = hitResult.createAnchor();
                    ModelRenderable.builder()
                            .setSource(this, Uri.parse("ArcticFox_Posed.sfb"))
                            .build()
                            .thenAccept(modelRenderable ->addModelToScene(modelRenderable, hitResult, planeType))
                            .exceptionally(throwable -> {
                                Toast toast = Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return null;
                            });
                }
            }
            tap.offsetLocation(-1 * 15, 0);
            if (tap.getX() < -100) {
                break;
            }
        }
        return tap;
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

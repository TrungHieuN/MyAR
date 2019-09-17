package com.example.myar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.core.Config;
import com.google.ar.core.Pose;
import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;

import com.google.ar.core.Trackable;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.PlaneRenderer;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;
    public Plane.Type planeType;
    private Anchor anchor;
    private AnchorNode anchorNode;
    private TransformableNode transformableNode;
    private ModelRenderable modelRenderable;
    private float scale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    private void addModelToScene(ModelRenderable modelRenderable, HitResult hitResult, Plane.Type planeType) {
        anchor = hitResult.createAnchor();
        anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());
        transformableNode = new TransformableNode(arFragment.getTransformationSystem());
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

  /*  private MotionEvent getNewTapOnBottomCloud(MotionEvent tap, Frame frame) {
        if (tap == null) {
            return null;
        }
        for (int i = 0; i < 70; i++) {
            for (HitResult hitResult : frame.hitTest(tap)) {
                Trackable trackable = hitResult.getTrackable();
                if (trackable instanceof Plane && ((Plane) trackable).isPoseInPolygon(hitResult.getHitPose()) && ((Plane) trackable).getType() == Plane.Type.HORIZONTAL_UPWARD_FACING) {
                    float distance = Pose.makeRotation(hitResult.getHitPose(), arFragment.getArSceneView().getArFrame().getCamera().getPose());

                    scale = distance;
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
    }    */

    private void setNewAnchor(Anchor newAnchor) {
        AnchorNode newAnchorNode = null;

        if (anchorNode != null && newAnchor != null) {
            // Create a new anchor node and move the children over.
            newAnchorNode = new AnchorNode(newAnchor);
            newAnchorNode.setParent(arFragment.getArSceneView().getScene());
            List<Node> children = new ArrayList<>(anchorNode.getChildren());
            for (Node child : children) {
                child.setParent(newAnchorNode);
            }
        } else if (anchorNode == null && newAnchor != null) {
            // First anchor node created, add Andy as a child.
            newAnchorNode = new AnchorNode(newAnchor);
            newAnchorNode.setParent(arFragment.getArSceneView().getScene());

            Node andy = new Node();
            andy.setRenderable(modelRenderable);
            andy.setParent(newAnchorNode);
        } else {
            // Just clean up the anchor node.
            if (anchorNode != null && anchorNode.getAnchor() != null) {
                anchorNode.getAnchor().detach();
                anchorNode.setParent(null);
                anchorNode = null;
            }
        }
        anchorNode = newAnchorNode;
    }
}

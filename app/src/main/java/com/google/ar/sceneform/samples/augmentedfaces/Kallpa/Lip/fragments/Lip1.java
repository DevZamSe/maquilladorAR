package com.google.ar.sceneform.samples.augmentedfaces.Kallpa.Lip.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.ar.core.AugmentedFace;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.samples.augmentedfaces.AugmentedFacesActivity;
import com.google.ar.sceneform.samples.augmentedfaces.R;
import com.google.ar.sceneform.ux.AugmentedFaceNode;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class Lip1 extends Fragment {

    private static final String TAG = Lip1.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;
    private AugmentedFacesActivity.FaceArFragment arFragment;
    private Texture faceMeshTexture;
    private ModelRenderable faceRegionsRenderable;
    private final HashMap<AugmentedFace, AugmentedFaceNode> faceNodeMap = new HashMap<>();

    public Lip1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lip1, container, false);

        FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();

        arFragment = (AugmentedFacesActivity.FaceArFragment) fm.findFragmentById(R.id.face_fragment_lip);

        lips();

        return view;
    }

    private void lips() {

        ModelRenderable.builder()
            .setSource(getActivity(), R.raw.sunglasses)
            .build()
            .thenAccept(
                modelRenderable -> {
                    faceRegionsRenderable = modelRenderable;
                    modelRenderable.setShadowCaster(true);
                    modelRenderable.setShadowReceiver(false);
                });

        Texture.builder()
                .setSource(getActivity(), R.drawable.makeup)
                .build()
                .thenAccept(texture -> faceMeshTexture = texture);

        ArSceneView sceneView = arFragment.getArSceneView();
        sceneView.setCameraStreamRenderPriority(Renderable.RENDER_PRIORITY_FIRST);
        Scene scene = sceneView.getScene();

        scene.addOnUpdateListener(
            (FrameTime frameTime) -> {
                if (faceRegionsRenderable == null || faceMeshTexture == null) {
                    return;
                }

                Collection<AugmentedFace> faceList =
                        sceneView.getSession().getAllTrackables(AugmentedFace.class);

                // Make new AugmentedFaceNodes for any new faces.
                for (AugmentedFace face : faceList) {
                    if (!faceNodeMap.containsKey(face)) {
                        AugmentedFaceNode faceNode = new AugmentedFaceNode(face);
                        faceNode.setParent(scene);
                        faceNode.setFaceRegionsRenderable(faceRegionsRenderable);
                        faceNode.setFaceMeshTexture(faceMeshTexture);
                        faceNodeMap.put(face, faceNode);
                    }
                }

                // Remove any AugmentedFaceNodes associated with an AugmentedFace that stopped tracking.
                Iterator<Map.Entry<AugmentedFace, AugmentedFaceNode>> iter =
                        faceNodeMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<AugmentedFace, AugmentedFaceNode> entry = iter.next();
                    AugmentedFace face = entry.getKey();
                    if (face.getTrackingState() == TrackingState.STOPPED) {
                        AugmentedFaceNode faceNode = entry.getValue();
                        faceNode.setParent(null);
                        iter.remove();
                    }
                }
            });
    }
}

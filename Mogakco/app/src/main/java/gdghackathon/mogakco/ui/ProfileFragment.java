package gdghackathon.mogakco.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import gdghackathon.mogakco.R;
import gdghackathon.mogakco.model.Event;
import gdghackathon.mogakco.model.Profile;
import gdghackathon.mogakco.model.UserInfoStatic;
import gdghackathon.mogakco.tools.EventsInProfileAdapter;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static gdghackathon.mogakco.R.layout.fragment_profile;

/**
 * Created by choijinjoo on 2017. 2. 16..
 */

public class ProfileFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = ProfileFragment.class.getSimpleName();


    RecyclerView mRecyclerView;
    private EventsInProfileAdapter eventsInProfileAdapter;

    @Bind(R.id.update_name_edittext_in_fragment_profile)
    EditText nameUpdateEdittext;
    @Bind(R.id.update_email_edittext_in_fragment_profile)
    EditText emailUpdateEdittext;
    @Bind(R.id.update_name_button_in_fragment_profile)
    ImageButton nameUpdateButton;
    @Bind(R.id.update_email_button_in_fragment_profile)
    ImageButton emailUpdateButton;
    @Bind(R.id.set_up_name_button_in_fragment_profile)
    ImageButton setUpNameButton;
    @Bind(R.id.set_up_email_button_in_fragment_profile)
    ImageButton setUpEmailButton;

    @Bind(R.id.profile_image_in_fragment_profile)
    ImageView profile_image_in_fragment_profile;

//    private FirebaseAuth mAuth;

    private String mFirebaseUid;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private FirebaseDatabase firebaseDatabase_event = FirebaseDatabase.getInstance();

    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference databaseReference_event = firebaseDatabase_event.getReference();

    private ValueEventListener mProfileListener;
//    private FirebaseAuth.AuthStateListener mAuthListener;

    private ArrayList<Profile> mProfileList = new ArrayList<>();
    private ArrayList<Event> mEventList = new ArrayList<>();

    private String uid;

    // key 가져오기
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "파이어베이스테스트 onCreate");


        // Auth
        mAuth = FirebaseAuth.getInstance();

        databaseReference = databaseReference.child("profiles");
        databaseReference_event = databaseReference_event.child("events");

//        mAuth = FirebaseAuth.getInstance();
//        mAuthListener = new FirebaseAuth.AuthStateListener(){
//
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if(user != null){
//                    Log.d(TAG, "파이어베이스테스트 sing in : " + user.getUid());
//                }else{
//                    Log.d(TAG, "파이어베이스테스트 sign out");
//                }
//            }
//        };

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "인증테스트onAuthStateChanged:signed_in:" + user.getUid());

                    uid = user.getUid();



                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "파이어베이스테스트 onstart");
        mAuth.addAuthStateListener(mAuthListener);

        ValueEventListener profileListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProfileList.clear();

                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Profile profile = Profile.parseSnapshot(child);
                    mProfileList.add(profile);

                    // 여기서 notify 해줘야됨.

                    Log.d(TAG, "프로필테스트 " + UserInfoStatic.getUserEmail());


                }

                for(int i=0; i<mProfileList.size(); i++){
                    if((mProfileList.get(i).firebaseUid).equals(uid)){
                        Glide.with(getActivity()).load(mProfileList.get(i).profileImgUrl).bitmapTransform(new CropCircleTransformation(getActivity())).into(profile_image_in_fragment_profile);
                        nameUpdateEdittext.setText(mProfileList.get(i).name);
                        emailUpdateEdittext.setText(mProfileList.get(i).email);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mEventList.clear();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Event event = Event.parseSnapshot(child);
                    mEventList.add(event);
                    eventsInProfileAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "파이어베이스테스트 " + databaseError.getMessage());
            }
        };

        if(databaseReference != null){
            databaseReference.addValueEventListener(profileListener);
            databaseReference_event.addValueEventListener(eventListener);
        }
        mProfileListener = profileListener;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(fragment_profile, container, false);
        ButterKnife.bind(this, v);
        initializeLayout(v);
//        nameUpdateEdittext =  (EditText)v.findViewById(R.id.update_name_edittext_in_fragment_profile);
//        emailUpdateEdittext = (EditText)v.findViewById(R.id.update_email_edittext_in_fragment_profile);

        nameUpdateEdittext.setEnabled(false);
        emailUpdateEdittext.setEnabled(false);

        setUpNameButton.setOnClickListener(this);
        setUpEmailButton.setOnClickListener(this);
        nameUpdateButton.setOnClickListener(this);
        emailUpdateButton.setOnClickListener(this);

        // 리사이클러 어댑터에 arraylist 넘겨주자


        return v;
    }

    private void initializeLayout(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_in_fragment_profile);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);



        // 리사이클러 어댑터에 arraylist 넘겨주자
        eventsInProfileAdapter = new EventsInProfileAdapter(getContext(), mEventList);
        mRecyclerView.setAdapter(eventsInProfileAdapter);
    }


    @Override
    public void onClick(View v) {
        Log.d(TAG,"버튼테스트 name update");
        int viewId = v.getId();
        switch (viewId) {
            case R.id.update_name_button_in_fragment_profile:
                Log.d(TAG,"버튼테스트 name update");
                nameUpdateEdittext.setEnabled(true);
                break;
            case R.id.update_email_button_in_fragment_profile:
                Log.d(TAG,"버튼테스트 name update");
                emailUpdateEdittext.setEnabled(true);
                break;
            case R.id.set_up_name_button_in_fragment_profile:
                // 눌리면 이제 다시 못적게
                nameUpdateEdittext.setEnabled(false);

                break;
            case R.id.set_up_email_button_in_fragment_profile:
                emailUpdateEdittext.setEnabled(false);

                break;
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

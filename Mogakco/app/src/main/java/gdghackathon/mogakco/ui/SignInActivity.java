package gdghackathon.mogakco.ui;

/**
 * Created by mk on 2017-02-18.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import gdghackathon.mogakco.R;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mFirebaseAuthListener;

    SignInButton mSigninGoogleButton;
    GoogleApiClient mGoogleApiClient;

    // Auth///
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

//    LoginButton mSigninFacebookButton;
//    CallbackManager mFacebookCallbackManager;

    static final String TAG = SignInActivity.class.getName();
    static final int RC_GOOGLE_SIGN_IN = 9001;

    String uid = "";
    String name = "";
    String email = "";
    String profileImgUrl = "";

    public static Intent getStartIntent(Context context) {
        Log.d(TAG, "Facebook login canceled.");
        Intent intent = new Intent(context, SignInActivity.class);
        return intent;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        setTitle("Login");

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if ( user != null ){
                    Log.d(TAG, "sign in");

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Log.d(TAG, "sign out");
                }
            }
        };

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "인증테스트onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    uid = user.getUid();
                    intent.putExtra("uid", uid);
                    intent.putExtra("name", name);
                    intent.putExtra("email", email);
                    intent.putExtra("profileImgUrl", profileImgUrl);
                    startActivity(intent);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        mSigninGoogleButton = (SignInButton) findViewById(R.id.sign_in_google_button);
        mSigninGoogleButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Facebook

//        mFacebookCallbackManager = CallbackManager.Factory.create();
//        mSigninFacebookButton = (LoginButton) findViewById(R.id.sign_in_facebook_button);
//        mSigninFacebookButton.setReadPermissions("email", "public_profile");
//        mSigninFacebookButton.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
//                mFirebaseAuth.signInWithCredential(credential);
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "Facebook login canceled.");
//            }
//
//            @Override
//            public void onError(FacebookException error){
//                Log.d(TAG, "Facebook login Error", error);
//            }
//        });
    }



    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }


    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "인증테스트");
        if( requestCode == RC_GOOGLE_SIGN_IN ) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if( result.isSuccess()){
                String token = result.getSignInAccount().getIdToken();
                AuthCredential credential = GoogleAuthProvider.getCredential(token, null);

                name = result.getSignInAccount().getDisplayName();
                email = result.getSignInAccount().getEmail();
                profileImgUrl = result.getSignInAccount().getPhotoUrl().toString();

                mFirebaseAuth.signInWithCredential(credential);
            }
            else {
                Log.d(TAG, "Google Login Failed." + result.getStatus());
            }
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "테스트");
//
// ;
    }
}

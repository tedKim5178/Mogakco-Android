package gdghackathon.mogakco.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import gdghackathon.mogakco.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    @Bind(R.id.btnGoogle)
    ImageView btnGoogle;
    @Bind(R.id.btnGithub)
    ImageView btnGithub;
    @Bind(R.id.btnFB)
    ImageView btnFB;

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    GoogleApiClient mGoogleApiClient;

    String mUsername;
    String mPhotoUrl;

    final String TAG = MainActivity.class.getName();

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "LoginActiviy 테스트 : ");
//        findViewById(R.id.logout_button).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                new AlertDialog.Builder(LoginActivity.this)
//                        .setMessage("Signout ?")
//                        .setPositiveButton("signout", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int i) {
//                                mFirebaseAuth.signOut();
//                                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
//                                LoginManager.getInstance().logOut();
//
//                                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }
//                        }).show();
//
//            }
//        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if( mFirebaseUser == null ){
            Log.d(TAG, "로그인테스트1 : ");
            Toast.makeText(this, "로그인이 필요합니다", Toast.LENGTH_SHORT).show();
//
//            Intent intent = new Intent(this, SignInActivity.class);
//            startActivity(intent);
//            finish();
        }
        else {
            Log.d(TAG, "로그인테스트2 : ");
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null ){
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }

            TextView usernameTextView = (TextView) findViewById(R.id.username_textview);
            usernameTextView.setText(mUsername);

            Toast.makeText(this, mUsername + "님 환영합니다.", Toast.LENGTH_SHORT).show();
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        ButterKnife.bind(this);
        initializeLayout();
    }

    private void initializeLayout() {
        btnGithub.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGithub:
                startActivity(MainActivity.getStartIntent(LoginActivity.this));
                finish();
                break;
            case R.id.btnGoogle:
                Log.d(TAG, "asdfasdf");
                startActivity(SignInActivity.getStartIntent(LoginActivity.this));
                finish();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
}

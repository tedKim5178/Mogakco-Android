package gdghackathon.mogakco.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import gdghackathon.mogakco.Controller.ProfileController;
import gdghackathon.mogakco.R;
import gdghackathon.mogakco.model.Profile;
import gdghackathon.mogakco.model.UserInfoStatic;
import gdghackathon.mogakco.tools.BottomNavigationViewHelper;
import gdghackathon.mogakco.tools.RegisteredFragmentStatePagerAdapter;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.pager)
    ViewPager mPager;
    @Bind(R.id.bottomNavi)
    BottomNavigationView bottomNavi;
    MainFragmentAdapter mAdapter;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private ArrayList<Profile> mProfileList = new ArrayList<>();
    private static final String TAG = MainActivity.class.getSimpleName();

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String profileImgUrl = intent.getStringExtra("profileImgUrl");
        String uid = intent.getStringExtra("uid");

        UserInfoStatic.setUserName(name);
        UserInfoStatic.setUserEmail(email);
//        Log.d(TAG, "인증테스트 : " + name + email + profileImgUrl);

        databaseReference = databaseReference.child("profiles");
        ProfileController.createProfile(uid, email, name, profileImgUrl);

        ButterKnife.bind(this);
        initializeLayout();
    }


    private void initializeLayout() {

        bottomNavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        mPager.setCurrentItem(HOME_FRAGMENT, true);
                        bottomNavi.getMenu().getItem(0).setChecked(false);
                        bottomNavi.getMenu().getItem(1).setChecked(true);
                        bottomNavi.getMenu().getItem(2).setChecked(true);
                        bottomNavi.getMenu().getItem(3).setChecked(true);
                        break;
                    case R.id.menu_calendar:
                        mPager.setCurrentItem(CALENDAR_FRAGMENT, true);
                        bottomNavi.getMenu().getItem(0).setChecked(true);
                        bottomNavi.getMenu().getItem(1).setChecked(false);
                        bottomNavi.getMenu().getItem(2).setChecked(true);
                        bottomNavi.getMenu().getItem(3).setChecked(true);
                        break;
                    case R.id.menu_search:
                        mPager.setCurrentItem(SEARCH_FRAGMENT, true);
                        bottomNavi.getMenu().getItem(0).setChecked(true);
                        bottomNavi.getMenu().getItem(1).setChecked(true);
                        bottomNavi.getMenu().getItem(2).setChecked(false);
                        bottomNavi.getMenu().getItem(3).setChecked(true);
                        break;
                    case R.id.menu_profile:
                        mPager.setCurrentItem(PROFILE_FRAGMETN, true);
                        bottomNavi.getMenu().getItem(0).setChecked(true);
                        bottomNavi.getMenu().getItem(1).setChecked(true);
                        bottomNavi.getMenu().getItem(2).setChecked(true);
                        bottomNavi.getMenu().getItem(3).setChecked(false);
                        break;
                }
                return true;
            }
        });
        mAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);

        mPager.setCurrentItem(HOME_FRAGMENT);
        bottomNavi.getMenu().getItem(0).setChecked(false);
        BottomNavigationViewHelper.disableShiftMode(bottomNavi);




    }

    public final int HOME_FRAGMENT = 0, CALENDAR_FRAGMENT = 1, SEARCH_FRAGMENT = 2, PROFILE_FRAGMETN = 3;

    public class MainFragmentAdapter extends RegisteredFragmentStatePagerAdapter {

        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == HOME_FRAGMENT) {
                return new HomeFragment();
            } else if (position == CALENDAR_FRAGMENT) {
                return new CalendarFragment();
            } else if (position == SEARCH_FRAGMENT) {
                return new SearchFragment();
            } else if (position == PROFILE_FRAGMETN) {
                return new ProfileFragment();
            } else {
                throw new RuntimeException("Out of bounds.");
            }
        }

        public HomeFragment getHomeFragment() {
            return (HomeFragment) getRegisteredFragment(HOME_FRAGMENT);
        }

        public CalendarFragment getCalendarFragment() {
            return (CalendarFragment) getRegisteredFragment(CALENDAR_FRAGMENT);
        }

        public SearchFragment getSearchFragment() {
            return (SearchFragment) getRegisteredFragment(SEARCH_FRAGMENT);
        }

        public ProfileFragment getProfileFragment() {
            return (ProfileFragment) getRegisteredFragment(PROFILE_FRAGMETN);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position % 4) {

                default:
                    return "";
            }
        }

    }
}

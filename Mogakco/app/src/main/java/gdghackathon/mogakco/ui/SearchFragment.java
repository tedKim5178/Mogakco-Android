package gdghackathon.mogakco.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;
import gdghackathon.mogakco.R;
import gdghackathon.mogakco.model.MogakcoEvent;

/**
 * Created by choijinjoo on 2017. 2. 16..
 */

public class SearchFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.recvEvent)
    RecyclerView recvEvent;
    @Bind(R.id.btnSearch)
    Button btnSearch;
    @Bind(R.id.edtxSearch)
    EditText edtxSearch;

    EventAdapter mAdapter;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, v);
        initializeLayout();
        return v;
    }

    private void initializeLayout() {
        btnSearch.setOnClickListener(this);
        recvEvent.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    private void startSearch(String name) {
        if (mAdapter != null)
            mAdapter.cleanup();
        mAdapter = new EventAdapter(getActivity(), databaseReference.child("events").endAt(name), new EventAdapter.OnClickListener() {
            @Override
            public void OnClick(MogakcoEvent event) {
                startActivity(EventDetailActivity.getStartIntent(getActivity(), event));
            }
        });
        recvEvent.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:
                if (edtxSearch.getText().toString().length() > 0) {
                    startSearch(edtxSearch.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "검색어를 입력해주세요 ~", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}

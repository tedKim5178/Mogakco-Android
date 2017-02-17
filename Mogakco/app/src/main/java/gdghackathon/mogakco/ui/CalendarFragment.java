package gdghackathon.mogakco.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import gdghackathon.mogakco.R;
import gdghackathon.mogakco.model.MogakcoEvent;

/**
 * Created by choijinjoo on 2017. 2. 16..
 */

public class CalendarFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.compactcalendar_view)
    gdghackathon.mogakco.tools.CompactCalendarView compactCalendarView;
    @Bind(R.id.txtvYear)
    TextView txtvYear;
    @Bind(R.id.txtvMonth)
    TextView txtvMonth;

    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MM", Locale.getDefault());
    private SimpleDateFormat dateFormatForYear = new SimpleDateFormat("yyyy", Locale.getDefault());

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    HashMap<String, gdghackathon.mogakco.model.Event> map = new HashMap<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, v);
        initializeLayout();
        return v;
    }

    private void initializeLayout() {
        // Set first day of week to Monday, defaults to Monday so calling setFirstDayOfWeek is not necessary
        // Use constants provided by Java Calendar class
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);


        databaseReference.child("events").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("s", "s");
                gdghackathon.mogakco.model.Event event = gdghackathon.mogakco.model.Event.parseSnapshot(dataSnapshot);
                if (!map.containsKey(dataSnapshot.getKey())) {
                    map.put(dataSnapshot.getKey(), event);
                    drawEvent(event);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("s", "s");

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("s", "s");

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                if (map.containsKey(dataSnapshot.getKey())) {

                }
                Log.d("s", "s");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("s", "s");

            }
        });

        // Add yesterday (mock data)
        Event ev1 = new Event(Color.BLUE, new DateTime().minusDays(1).getMillis(), new MogakcoEvent("GDG 세미나", "http://storage.googleapis.com/mathpresso-storage/uploads/banners/16_giftpageimage_1.png"));
        Event ev2 = new Event(Color.RED, new DateTime().minusDays(1).getMillis(), new MogakcoEvent("홍대에서 같이 코딩합시다!", "http://storage.googleapis.com/mathpresso-storage/uploads/banners/16_giftpageimage_1.png"));
        compactCalendarView.addEvent(ev1);
        compactCalendarView.addEvent(ev2);

        // Add now (mock data)
        Event ev3 = new Event(Color.GREEN, new DateTime().getMillis(), new MogakcoEvent("해커톤"));
        compactCalendarView.addEvent(ev3);
        // Query for events on Sun, 07 Jun 2015 GMT.
        // Time is not relevant when querying for events, since events are returned by day.
        // So you can pass in any arbitary DateTime and you will receive all events for that day.
        List<Event> events = compactCalendarView.getEvents(1433701251000L); // can also take a Date object

        // events has size 2 with the 2 events inserted previously

        // define a listener to receive callbacks when certain events happen.
        compactCalendarView.setListener(new gdghackathon.mogakco.tools.CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);

                if (events.size() > 0)
                    showEventListDialog(events);

                txtvYear.setText(dateFormatForYear.format(dateClicked) + "년");
                txtvMonth.setText(dateFormatForMonth.format(dateClicked) + "월");

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                txtvYear.setText(dateFormatForYear.format(firstDayOfNewMonth) + "년");
                txtvMonth.setText(dateFormatForMonth.format(firstDayOfNewMonth) + "월");

            }
        });

    }

    //TODO 
    private void drawEvent(gdghackathon.mogakco.model.Event event) {
        if (event.getType() == "모각코") {
            Event ev1 = new Event(Color.BLUE, new DateTime().minusDays(1).getMillis(), new MogakcoEvent("GDG 세미나", "http://storage.googleapis.com/mathpresso-storage/uploads/banners/16_giftpageimage_1.png"));

        } else if (event.getType() == "세미나") {
            Event ev1 = new Event(Color.BLUE, new DateTime().minusDays(1).getMillis(), new MogakcoEvent("GDG 세미나", "http://storage.googleapis.com/mathpresso-storage/uploads/banners/16_giftpageimage_1.png"));

        } else if (event.getType() == "컨퍼런스") {
            Event ev1 = new Event(Color.BLUE, new DateTime().minusDays(1).getMillis(), new MogakcoEvent("GDG 세미나", "http://storage.googleapis.com/mathpresso-storage/uploads/banners/16_giftpageimage_1.png"));

        }

    }

    private void showEventListDialog(List<Event> events) {
        EventListDialog.init(getActivity(), events).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}


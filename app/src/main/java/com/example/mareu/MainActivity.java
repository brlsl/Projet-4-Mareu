package com.example.mareu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.mareu.di.DI;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    //for design
    static RecyclerView mRecyclerView;
    static MyMeetingAdapter mMeetingAdapter;
    private FloatingActionButton fab_button;
    private RecyclerView.LayoutManager mLayoutManager;

    //for data

    private MeetingApiService mMeetingApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMeetingApiService = DI.getMeetingApiService();
        mMeetingApiService.getMeetingsList();

        buildRecyclerView();



        // when user clicks on fab, it opens a dialog window
        fab_button = findViewById(R.id.fab_add_reunion);
        fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView); //ok
        // get List and adapt to RecyclerView
        mMeetingAdapter= new MyMeetingAdapter(mMeetingApiService.getMeetingsList());

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMeetingAdapter);

        mMeetingAdapter.setOnItemClickListener(new MyMeetingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "Test click sur itemView", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
                Toast.makeText(MainActivity.this, "Suppression item en test", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void removeItem(int position){
        MyMeetingAdapter.mMeetingList.remove(position);
        mMeetingAdapter.notifyItemRemoved(position);
    }

    /*
        public removeItem(int position){
    
    
        }
    */
    //for menu item in action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.item_1){
            mMeetingApiService.sortMeetingsPlaceAZ();
        }
        if(id == R.id.item_2){
            mMeetingApiService.sortMeetingsPlaceZA();
        }

        if(id == R.id.item_3){
            mMeetingApiService.sortMeetingsChronologicalOrder();
        }
        if(id == R.id.item_4){
            mMeetingApiService.sortMeetingsAntiChronological();
        }
        mMeetingAdapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }


    public void openDialog(){
        MeetingDialog meetingDialog = new MeetingDialog();
        meetingDialog.show(getSupportFragmentManager(),"opens the dialog box");
    }
/*
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        mMeetingApiService.deleteMeeting(event.meeting);
    }

 */
}

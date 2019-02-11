package com.example.test;

import android.arch.persistence.room.Room;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class RoomPerfomance extends AppCompatActivity implements SearchView.OnQueryTextListener{
    Toolbar toolbar;
    TextView tvTimeRoom;
    UserListAdapter userListAdapter;
    List<UserListItem> userListItems;
    RecyclerView rvUser;
    public MyAppDatabase myAppDatabase;
    User user;
    Button btnSortList;
    RadioGroup rg;
    RadioButton rbFirstname,rbLastName;
    int sortId;
    public Long start_insert_time, end_insert_time,cost_insert_time, start_view_time, end_view_time, cost_view_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_room_perfomance);
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "userdb").allowMainThreadQueries().build();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTimeRoom = findViewById(R.id.tvTimeRoom);
        rvUser = findViewById(R.id.rvViewUser);
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        userListItems = new ArrayList<>();

        userListAdapter = new UserListAdapter(this, userListItems);
        rvUser.setAdapter(userListAdapter);

        btnSortList = findViewById(R.id.btnSortList);
        rbFirstname = findViewById(R.id.rbFirstName);
        sortId = R.id.rbFirstName;
        rbLastName = findViewById(R.id.rbLastName);
        rbFirstname.setChecked(true);
        rg = findViewById(R.id.rgSortGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbFirstName:
                        sortId = R.id.rbFirstName;
                        break;
                    case R.id.rbLastName:
                        sortId = R.id.rbLastName;
                        break;
                }
            }
        });
        btnSortList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread sort = new Thread(){
                    @Override
                    public void run() {
                        Looper.prepare();
                        Collections.sort(userListItems, new Comparator<UserListItem>() {
                            @Override
                            public int compare(UserListItem o1, UserListItem o2) {
                                switch (sortId){
                                    case R.id.rbLastName:
                                        return o1.getLastName().compareTo(o2.getLastName());
                                    default:
                                        return o1.getFirstName().compareTo(o2.getFirstName());
                                }
                            }
                        });
                        Looper.loop();
                        userListAdapter.notifyDataSetChanged();
                    }
                };
                sort.start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_insert:
                insertOperation();
                Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_view:
                userListItems.clear();
                readOperation();
                userListAdapter.notifyDataSetChanged();
                Toast.makeText(this, "View Data", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public void insertOperation() {
        start_insert_time = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
        user = new User(randomString(),randomString(),randomString(),randomInt());
        this.myAppDatabase.myDao().insertUser(user);
        }

        end_insert_time = System.currentTimeMillis();
        cost_insert_time = end_insert_time - start_insert_time;
        tvTimeRoom.setText(cost_insert_time +" milli second taken by 1000 insertion.");
    }

    public void readOperation() {
        start_view_time = System.currentTimeMillis();

        List<User> users = this.myAppDatabase.myDao().getUser();
        String f_name, l_name, email, phone_number;
        for (User urs : users) {
            f_name = urs.getFirst_name();
            l_name = urs.getLast_name();
            email = urs.getEmail();
            phone_number = urs.getPhone_number();
            userListItems.add(new UserListItem(f_name, l_name, email, phone_number));
        }

        end_view_time = System.currentTimeMillis();
        cost_view_time = end_view_time - start_view_time;
        tvTimeRoom.setText(cost_view_time +" milli second taken by view.");
    }

    public static String randomString() {
        int txtInt = 65;
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        if (randomLength < 5)
            randomLength = 5;
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            do {
                txtInt = generator.nextInt(90);
            } while (txtInt < 65);

            tempChar = (char) (txtInt);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public static String randomInt() {
        int txtInt = 65;
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        char tempChar;
        for (int i = 0; i < 10; i++) {
            do {
                txtInt = generator.nextInt(57);
            } while (txtInt < 48);
            tempChar = (char) (txtInt);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String userInput = s.toLowerCase();
        List<UserListItem> newList = new ArrayList<>();

        for (UserListItem currentUserItem: userListItems){
            String name = currentUserItem.getFirstName();
            if (name.toLowerCase().contains(userInput)){
                newList.add(currentUserItem);
            }
        }
        userListAdapter.updateList(newList);
        return true;
    }
}

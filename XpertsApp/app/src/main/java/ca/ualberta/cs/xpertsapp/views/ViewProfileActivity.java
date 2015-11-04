package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.User;

public class ViewProfileActivity extends Activity {
    private Button editProfile;
    public Button getEditProfile() {return editProfile;};
    private Button addService;
    public Button getAddService() {return addService;};
    private ListView serviceList;
    public ListView getServiceList() {return serviceList;};
    private ArrayAdapter<Service> serviceArrayAdapter;
    private ViewProfileActivity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        editProfile = (Button) findViewById(R.id.editButton);
        addService = (Button) findViewById(R.id.add);
        serviceList = (ListView) findViewById(R.id.serviceList);

        serviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, AddServiceActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_profile, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        User user = MyApplication.getLocalUser();
        List<Service> Services = user.getServices();
        serviceArrayAdapter = new ArrayAdapter<Service>(this, R.layout.placeholderlistitem,Services);
        serviceList.setAdapter(serviceArrayAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void editProfile(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    public void addService(View view) {
        Intent intent = new Intent(this, AddServiceActivity.class);
        startActivity(intent);
    }
}
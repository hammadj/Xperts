package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.adapters.TopTraderAdapter;
import ca.ualberta.cs.xpertsapp.controllers.ProfileController;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

/**
 * This activity displays the list of top traders using the app
 */
public class TopTradersActivity extends Activity {
    private ListView getTraderList;
    private TopTradersActivity activity = this;
    private ProfileController pc = new ProfileController();
    public ListView getTraderList() {return getTraderList;};
    private TopTraderAdapter topTraderAdapter;

    // For UI Test
    public TopTraderAdapter getTopTraderAdapter() {
        return topTraderAdapter;
    }

    /**
     * This sets the click listener for the list of top traders.
     * Navigates to the profile of the user that was clicked on.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_traders);

        getTraderList = (ListView) findViewById(R.id.listView);

        getTraderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String email = topTraderAdapter.getItem(position).getEmail();
                Intent intent;
                if (email.equals(MyApplication.getLocalUser().getEmail())) {
                    intent = new Intent(activity, ViewProfileActivity.class);
                } else {
                    intent = new Intent(activity, FriendProfileActivity.class);
                    intent.putExtra("INTENT_EMAIL", email);
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_traders, menu);
        return true;
    }

    /**
     * This initializes the TopTraderAdapter to display the top traders.
     * Loads all users and sorts them based on the number of active and complete trades.
     */
    @Override
    protected void onStart() {
        super.onStart();
        UserManager.sharedManager().clearCache();
        List<User> AllUsers = new ArrayList<User>();
        try {
            AllUsers = UserManager.sharedManager().findUsers("*");
        } catch (Exception e) {
            Context context = getApplicationContext();
            CharSequence text = "Server busy. Please try again.";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        Collections.sort(AllUsers, new Comparator<User>() {
            public int compare(User one, User two) {
                if (getTraderScore(one) >= getTraderScore(two)) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        topTraderAdapter = new TopTraderAdapter(this,AllUsers);
        getTraderList.setAdapter(topTraderAdapter);
    }

    /**
     * Calculates the top trader score for a user
     * @param user The user to calculate the trader score for
     * @return The number of in progress and completed trades for the user
     */
    public Integer getTraderScore(User user) {
        List<Trade> trades = user.getTrades();
        Integer score = 0;
        for(Trade t : trades) {
            int status = t.getStatus();
            if (status == 1 || status == 4)
                score++;
        }
        return score;
    }

}

package ca.ualberta.cs.xpertsapp.view;

import android.app.Activity;
import android.os.Bundle;

import ca.ualberta.cs.xpertsapp.R;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
	}
}
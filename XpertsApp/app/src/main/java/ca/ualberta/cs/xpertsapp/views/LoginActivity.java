package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.AlertDialogManager;
import ca.ualberta.cs.xpertsapp.model.UserManager;

/**
 * Activity that allows user to create or login to their account by entering their email.
 */
public class LoginActivity extends Activity {

    EditText emailField;
    Button loginButton;
    AlertDialogManager alert = new AlertDialogManager();

    /**
     * Sets the click listener for the login button. It will call isValidEmail to check if the email
     * entered is valid.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = (EditText) findViewById(R.id.login_email);
        loginButton = (Button) findViewById(R.id.btn_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();

                // Check if username, password is filled
                if (isValidEmail(email)) {
                    UserManager.sharedManager().registerUser(email);
                    MyApplication.login(email);
                    Intent main = new Intent(MyApplication.getContext(), MainActivity.class);
                    startActivity(main);
                    finish();
                } else {
                    alert.showAlertDialog(LoginActivity.this, "Login failed", "Please enter a valid email");
                }
            }
        });
    }

    /**
     * Checks if email entered is valid by checking if it matches an email pattern.
     * @param email
     * @return true if valid email, false if invalid
     */
    private static boolean isValidEmail(CharSequence email) {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

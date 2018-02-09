package dam.android.dependeciapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dam.android.dependeciapp.Controladores.Conexion;
import dam.android.dependeciapp.Pojo.Usuario;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    private static final String MYPREFS = "LoginPreferences";
    // UI references.
    private EditText mDNIView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Conexion con;
    private CheckBox cbGuardaUsuarioPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
<<<<<<< HEAD
        startActivity(new Intent(this, MainActivity.class));
        //setUI();
        //con = new Conexion();
=======
        setUI();
        cargaPreferencias();
        //TODO Si no se pudiera establecer conexion usar la SQLite
        con = new Conexion();
>>>>>>> 7331d5ce37357124cb58ea80b1e973c2013d617b
    }

    private void setUI() {
        cbGuardaUsuarioPass = (CheckBox) findViewById(R.id.cbGuarda);
        mDNIView = (EditText) findViewById(R.id.DNI);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        Button btLogin = (Button) findViewById(R.id.DNI_sign_in_button);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        mDNIView.setError(null);
        mPasswordView.setError(null);

        String DNI = mDNIView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(DNI)) {
            cancel = true;
            Toast.makeText(this, R.string.error_field_required, Toast.LENGTH_LONG).show();

        } else if (!dniValido(DNI)) {
            Toast.makeText(this, R.string.error_invalid_DNI, Toast.LENGTH_LONG).show();
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            // focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask();
            mAuthTask.execute(DNI, password);
        }
    }

    //Metodo para comprobar que un DNI es real
    private boolean dniValido(String dniAComprobar) {
        char[] letraDni = {
                'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'
        };
        String num = "";
        int ind = 0;
        if (dniAComprobar.length() == 8) {
            dniAComprobar = "0" + dniAComprobar;
        }
        if (!Character.isLetter(dniAComprobar.charAt(8))) {
            return false;
        }
        if (dniAComprobar.length() != 9) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            if (!Character.isDigit(dniAComprobar.charAt(i))) {
                return false;
            }
            num += dniAComprobar.charAt(i);
        }
        ind = Integer.parseInt(num);
        ind %= 23;
        if ((Character.toUpperCase(dniAComprobar.charAt(8))) != letraDni[ind]) {
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password) {

        return true;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    //Si el CheckBox guarda usuario contraseña esta pulsado se ejecutara
    private void GuardaUsuarioPass(String usuario, String pass) {
        SharedPreferences pref = getSharedPreferences(MYPREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user", usuario);
        editor.putString("pass", pass);
        editor.putBoolean("guardaUserPass", true);
        editor.commit();
    }

    //Carga las preferencias guardadas, si no las hay carga las predeterminadas
    private void cargaPreferencias() {
        SharedPreferences prefs = getSharedPreferences(MYPREFS, MODE_PRIVATE);

        cbGuardaUsuarioPass.setChecked(prefs.getBoolean("guardaUserPass", false));
        mDNIView.setText(prefs.getString("usuario", "DNI"));
        mPasswordView.setText(prefs.getString("pass", ""));

    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<String, Void, Boolean> {
        private Usuario user;

        @Override
        protected Boolean doInBackground(String... strings) {
            String usuario = strings[0];
            String pass = strings[1];

            ResultSet rs = con.IniciaSesion(usuario, pass);
            if (rs != null) {
                try {
                    //Si el Se ha marcado el CheckBox de guardar usuari y contraseña, se guardan
                    if (cbGuardaUsuarioPass.isChecked()) {
                        GuardaUsuarioPass(usuario, pass);
                    }
                    this.user = new Usuario(rs);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return true;
            } else
                return false;
        }

        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                //i.putExtra("user",user);
                startActivity(i);
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}


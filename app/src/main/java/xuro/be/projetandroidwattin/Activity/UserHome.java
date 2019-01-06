package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import xuro.be.projetandroidwattin.Models.SessionManagement;
import xuro.be.projetandroidwattin.R;

public class UserHome extends Activity {

    TextView tv_userhome_loggedAs;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        session = new SessionManagement(getApplicationContext());

        String mail = session.getUserEmail();
        Log.v("sessionmanager","userhome" +" "+ mail);

        tv_userhome_loggedAs = (TextView)findViewById(R.id.tv_userhome_loggedAs);
        tv_userhome_loggedAs.setText(getString(R.string.connectedAs) + mail);
    }
}

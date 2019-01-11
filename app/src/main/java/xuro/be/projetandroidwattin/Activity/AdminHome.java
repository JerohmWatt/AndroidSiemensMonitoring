package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import xuro.be.projetandroidwattin.Models.SessionManagement;
import xuro.be.projetandroidwattin.R;

public class AdminHome extends Activity {

    private TextView tv_adminhome_loggedAs;
    private SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        session = new SessionManagement(getApplicationContext());

        String mail = session.getUserEmail();
        String loggedAs = getString(R.string.connectedAs) + mail;
        tv_adminhome_loggedAs = findViewById(R.id.tv_adminhome_loggedAs);
        tv_adminhome_loggedAs.setText(loggedAs);
    }

    public void onAdminHomeClickManager(View v) {

        switch (v.getId()) {
            case R.id.bt_adminhome_editusers:
                Intent intent = new Intent(getApplicationContext(),EditUsers.class);
                startActivity(intent);
                    break;

            case R.id.fab_adminhome_logout:
                session.logoutUser();
                break;

            default:
                    break;


        }
    }

}

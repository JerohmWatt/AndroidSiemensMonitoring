package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import xuro.be.projetandroidwattin.Models.SessionManagement;
import xuro.be.projetandroidwattin.R;

public class AdminHome extends Activity {

    TextView tv_adminhome_loggedAs;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        session = new SessionManagement(getApplicationContext());

        String mail = session.getUserEmail();
        Log.v("sessionmanager", "userhome" + " " + mail);

        tv_adminhome_loggedAs = (TextView) findViewById(R.id.tv_adminhome_loggedAs);
        tv_adminhome_loggedAs.setText(getString(R.string.connectedAs) + mail);
    }

    public void onMainClickManager(View v) {
        //récup la vue et accès au bouton

        switch (v.getId()) {
            case R.id.bt_adminhome_editusers:
                Log.i("rightss","ouiouioui");
                Intent intent = new Intent(this,EditUsers.class);
                startActivity(intent);
                    break;

            default:
                Log.i("rightss","non");
                    break;


        }
    }

}

package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import xuro.be.projetandroidwattin.Models.SessionManagement;
import xuro.be.projetandroidwattin.R;

public class UserHome extends Activity {

    TextView tv_userhome_loggedAs;
    SessionManagement session;
    Button bt_userhome_readdatas;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        session = new SessionManagement(getApplicationContext());

        String mail = session.getUserEmail();
        Log.v("sessionmanager","userhome" +" "+ mail);

        tv_userhome_loggedAs = (TextView)findViewById(R.id.tv_userhome_loggedAs);
        tv_userhome_loggedAs.setText(getString(R.string.connectedAs) + mail);
        bt_userhome_readdatas = findViewById(R.id.bt_userhome_readdatas);
        thread = new Thread();

    }

    public void onUserHomeClickManager(View v) {

        switch (v.getId()) {
            case R.id.bt_userhome_readdatas:
                Log.i("datasss","yes");
                Intent intent = new Intent(getApplicationContext(),ReadDatas.class);
                startActivity(intent);
                break;

        }
    }

}

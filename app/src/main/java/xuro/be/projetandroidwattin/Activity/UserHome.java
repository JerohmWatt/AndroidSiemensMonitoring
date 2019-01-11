package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import xuro.be.projetandroidwattin.Models.SessionManagement;
import xuro.be.projetandroidwattin.R;

public class UserHome extends Activity {

    TextView tv_userhome_loggedAs;
    SessionManagement session;
    Button bt_userhome_goToPills, bt_userhome_goToLiquid, bt_userhome_accessPillApi, bt_userhome_accessLiquidApi;
    Thread thread;
    private TextView tv_namepills, tv_ipPills, tv_rackPills, tv_slotPills, tv_nameLiquid, tv_ipLiquid, tv_rackLiquid, tv_slotLiquid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        session = new SessionManagement(getApplicationContext());

        String mail = session.getUserEmail();
        Log.v("sessionmanager","userhome" +" "+ mail);

        tv_userhome_loggedAs = findViewById(R.id.tv_userhome_loggedAs);
        tv_userhome_loggedAs.setText(getString(R.string.connectedAs) + mail);
        bt_userhome_goToPills = findViewById(R.id.bt_userhome_goToPills);
        bt_userhome_goToLiquid = findViewById(R.id.bt_userhome_goToLiquid);
        bt_userhome_accessPillApi = findViewById(R.id.bt_userhome_accessPillApi);
        bt_userhome_accessLiquidApi = findViewById(R.id.bt_userhome_accessLiquidApi);
        tv_namepills = findViewById(R.id.tv_userhome_namePills);
        tv_ipPills = findViewById(R.id.tv_userhome_ipPills);
        tv_rackPills = findViewById(R.id.tv_userhome_rackPills);
        tv_slotPills = findViewById(R.id.tv_userhome_slotPills);

        tv_nameLiquid = findViewById(R.id.tv_userhome_nameLiquid);
        tv_ipLiquid = findViewById(R.id.tv_userhome_ipLiquid);
        tv_rackLiquid = findViewById(R.id.tv_userhome_rackLiquid);
        tv_slotLiquid = findViewById(R.id.tv_userhome_slotLiquid);
        thread = new Thread();

        try {
            readDatas();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onUserHomeClickManager(View v) {

        Intent intent4 = new Intent(getApplicationContext(),WebActivity.class);
        switch (v.getId()) {
            case R.id.bt_userhome_goToPills:
                Log.i("datasss","yes");
                Intent intent = new Intent(getApplicationContext(),PillsActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_userhome_goToLiquid:
                Intent intent2 = new Intent(getApplicationContext(),LiquidActivity.class);
                startActivity(intent2);
                break;

            case R.id.bt_userhome_editParams:
                Intent intent3 = new Intent(getApplicationContext(),EditParamsActivity.class);
                startActivity(intent3);
                break;
            case R.id.bt_userhome_accessPillApi:
                intent4.putExtra("AUTOMATE","pill");
                startActivity(intent4);
                break;
            case R.id.bt_userhome_accessLiquidApi:
                intent4.putExtra("AUTOMATE","liquid");
                startActivity(intent4);
                break;
        }

    }

    public void readDatas() throws IOException {
        FileInputStream ins = openFileInput("config.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        StringBuilder out = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null){
            out.append(line);
        }
        reader.close();
        ins.close();
        String[] items = out.toString().split("#");
        int i=0;
        String [] logDatas = {"a","b","c","d","e","f"};
        for (String item : items)
        {
            logDatas[i] = item;
            i++;
        }
        updateValue(logDatas);
    }

    public void updateValue(String[] logDatas){
        tv_ipPills.setText(logDatas[0]);
        tv_rackPills.setText(logDatas[1]);
        tv_slotPills.setText(logDatas[2]);
        tv_ipLiquid.setText(logDatas[3]);
        tv_rackLiquid.setText(logDatas[4]);
        tv_slotLiquid.setText(logDatas[5]);
    }

}

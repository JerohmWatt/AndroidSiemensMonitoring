package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import xuro.be.projetandroidwattin.Automat.ReadLiquidS7;
import xuro.be.projetandroidwattin.Automat.ReadPillsS7;
import xuro.be.projetandroidwattin.Automat.WritePillsS7;
import xuro.be.projetandroidwattin.Models.SessionManagement;
import xuro.be.projetandroidwattin.R;

public class LiquidActivity extends Activity {

    private Button btn_connect;
    private TextView tv_plc;
    private TextView tv_liquid;
    private ReadLiquidS7 readS7;
    private NetworkInfo network;
    private ConnectivityManager connexStatus;
    private EditText et_value;
    private EditText et_dbb;
    private Button bt_pushdata;
    private WritePillsS7 writeS7;

    private String ip, rack, slot;

    SessionManagement session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liquid);

        btn_connect = (Button) findViewById(R.id.bt_readdatas_ConnexS7);
        tv_plc = (TextView) findViewById(R.id.tv_readdatas_plc);
        et_dbb = findViewById(R.id.et_readdatas_dbb);
        et_value = findViewById(R.id.et_readdatas_value);
        bt_pushdata = findViewById(R.id.bt_readdatas_pushdata);
        tv_liquid = (TextView) findViewById(R.id.tv_liquid_level);

        et_dbb.setText("5");

        session = new SessionManagement(getApplicationContext());

        connexStatus = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        network = connexStatus.getActiveNetworkInfo();

        try {
            getConnexionValues();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onReadDatasClickManager(View v) {
        switch (v.getId()) {

            case R.id.bt_readdatas_ConnexS7:
                if(network != null && network.isConnectedOrConnecting())
                {
                    if (btn_connect.getText().equals("Connexion")){
                        Toast.makeText(this,network.getTypeName(),Toast.LENGTH_SHORT).show();
                        btn_connect.setText("Déconnexion");
                        readS7 = new ReadLiquidS7(v, btn_connect, tv_plc, tv_liquid);
                        //writeS7 = new WritePillsS7(et_dbb);
                        readS7.Start(ip,rack,slot);
                        //writeS7.Start(ip,rack,slot);
                    }
                    else{
                        readS7.Stop();
                        //writeS7.Stop();
                        btn_connect.setText("Connexion");
                        Toast.makeText(getApplication(), "Traitement interrompu par l'utilisateur ! ",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(this,"! Connexion réseau IMPOSSIBLE !",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bt_readdatas_pushdata:

                if(session.getUserEmail() == "2") {
                    if (et_dbb.getText().toString().isEmpty() || et_value.getText().toString().isEmpty()) {
                        Toast.makeText(this, "Un des champs n'est pas rempli", Toast.LENGTH_SHORT).show();
                    } else {
                        if (Integer.parseInt(et_dbb.getText().toString()) >= 0 && Integer.parseInt(et_dbb.getText().toString()) <= 15) {
                            writeS7.setDbb(Integer.parseInt(et_dbb.getText().toString()));
                            writeS7.setWriteBool(et_value.getText().toString());
                        } else {
                            writeS7.setDbb(Integer.parseInt(et_dbb.getText().toString()));
                            writeS7.setWriteInt(et_value.getText().toString());
                        }
                    }
                }
                else{
                    Toast.makeText(this, "Vous n'avez pas les droits pour écrire des données", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    public void getConnexionValues() throws IOException {
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

        ip = logDatas[0];
        rack = logDatas[1];
        slot = logDatas[2];
    }

}


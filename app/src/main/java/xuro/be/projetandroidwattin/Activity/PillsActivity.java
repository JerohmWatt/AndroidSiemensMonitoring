package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import xuro.be.projetandroidwattin.Automat.ReadPillsS7;
import xuro.be.projetandroidwattin.Automat.WritePillsS7;
import xuro.be.projetandroidwattin.Models.SessionManagement;
import xuro.be.projetandroidwattin.R;

public class PillsActivity extends Activity {

    private Button btn_connect;
    private TextView tv_plc;
    private TextView tv_fullbottles;
    private ReadPillsS7 readS7;
    private NetworkInfo network;
    private ConnectivityManager connexStatus;
    private EditText et_value;
    private EditText et_dbb;
    private Button bt_pushdata;
    private WritePillsS7 writeS7;
    private TextView tv_pillsamount;
    private ImageView iv_status;
    private TextView tv_status;

    SessionManagement session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_datas);

        btn_connect = (Button) findViewById(R.id.bt_readdatas_ConnexS7);
        tv_plc = (TextView) findViewById(R.id.tv_readdatas_plc);
        tv_fullbottles = findViewById(R.id.tv_readdatas_fullBottles);
        et_dbb = findViewById(R.id.et_readdatas_dbb);
        et_value = findViewById(R.id.et_readdatas_value);
        bt_pushdata = findViewById(R.id.bt_readdatas_pushdata);
        tv_pillsamount = findViewById(R.id.tv_readdatas_pillsamount);
        iv_status = findViewById(R.id.iv_readdatas_status);
        tv_status = findViewById(R.id.tv_readdatas_status);

        et_dbb.setText("5");

        session = new SessionManagement(getApplicationContext());

        connexStatus = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        network = connexStatus.getActiveNetworkInfo();
    }

    public void onReadDatasClickManager(View v) {
        switch (v.getId()) {

            case R.id.bt_readdatas_ConnexS7:
                if(network != null && network.isConnectedOrConnecting())
                {
                    if (btn_connect.getText().equals("Connexion")){
                        Toast.makeText(this,network.getTypeName(),Toast.LENGTH_SHORT).show();
                        btn_connect.setText("Déconnexion");
                        readS7 = new ReadPillsS7(v, btn_connect, tv_plc, tv_fullbottles,tv_pillsamount, tv_status);
                        writeS7 = new WritePillsS7(et_dbb);
                        readS7.Start("192.168.0.15","0", "2");
                        writeS7.Start("192.168.0.15", "0", "2");
                    }
                    else{
                        readS7.Stop();
                        writeS7.Stop();
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

}
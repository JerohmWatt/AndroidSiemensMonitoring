package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import xuro.be.projetandroidwattin.Automat.ReadTaskS7;
import xuro.be.projetandroidwattin.Automat.WriteTaskS7;
import xuro.be.projetandroidwattin.R;

public class ReadDatas extends Activity {

    private ProgressBar pb_main_progressionS7;
    private Button bt_main_ConnexS7;
    private TextView tv_main_plc;
    private TextView tv_readdatas_fullBottles;
    private ReadTaskS7 readS7;
    private NetworkInfo network;
    private ConnectivityManager connexStatus;
    private EditText et_readdatas_value;
    private EditText et_readdatas_dbb;
    private Button bt_readdatas_pushdata;
    private WriteTaskS7 writeS7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_datas);
        bt_main_ConnexS7 = (Button) findViewById(R.id.bt_readdatas_ConnexS7);
        tv_main_plc = (TextView) findViewById(R.id.tv_readdatas_plc);
        tv_readdatas_fullBottles = findViewById(R.id.tv_readdatas_fullBottles);
        et_readdatas_dbb = findViewById(R.id.et_readdatas_dbb);
        et_readdatas_dbb.setText("5");
        et_readdatas_value = findViewById(R.id.et_readdatas_value);
        bt_readdatas_pushdata = findViewById(R.id.bt_readdatas_pushdata);
        connexStatus = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        network = connexStatus.getActiveNetworkInfo();
    }

    public void onReadDatasClickManager(View v) {
        switch (v.getId()) {

            case R.id.bt_readdatas_ConnexS7:
                if(network != null && network.isConnectedOrConnecting())
                {
                    if (bt_main_ConnexS7.getText().equals("Connexion")){
                        Toast.makeText(this,network.getTypeName(),Toast.LENGTH_SHORT).show();
                        bt_main_ConnexS7.setText("Déconnexion");
                        readS7 = new ReadTaskS7(v, bt_main_ConnexS7, pb_main_progressionS7, tv_main_plc, tv_readdatas_fullBottles);
                        writeS7 = new WriteTaskS7(et_readdatas_dbb);
                        readS7.Start("192.168.0.15","0", "2");
                        writeS7.Start("192.168.0.15", "0", "2");
                    }
                    else{
                        readS7.Stop();
                        writeS7.Stop();
                        bt_main_ConnexS7.setText("Connexion");
                        Toast.makeText(getApplication(), "Traitement interrompu par l'utilisateur !!! ",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(this,"! Connexion réseau IMPOSSIBLE !",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bt_readdatas_pushdata:
                if(et_readdatas_dbb.getText().toString().isEmpty() || et_readdatas_value.getText().toString().isEmpty()){
                    Toast.makeText(this, "Un des champs n'est pas rempli", Toast.LENGTH_SHORT).show();
                }else{
                    if(Integer.parseInt(et_readdatas_dbb.getText().toString()) >= 0 && Integer.parseInt(et_readdatas_dbb.getText().toString()) <=15){
                        writeS7.setDbb(Integer.parseInt(et_readdatas_dbb.getText().toString()));
                        writeS7.setWriteBool(et_readdatas_value.getText().toString());
                    }else{
                        writeS7.setDbb(Integer.parseInt(et_readdatas_dbb.getText().toString()));
                        writeS7.setWriteInt(et_readdatas_value.getText().toString());
                    }
                }

                break;

      /*       case R.id.ch_main_activerouv:
                writeS7.setWriteBool(1, ch_main_activerouv.isChecked() ? 1 : 0);
                break;
            case R.id.ch_main_activerfer:
                writeS7.setWriteBool(2, ch_main_activerfer.isChecked() ? 1 : 0);
                break;
            case R.id.ch_main_aru:
                writeS7.setWriteBool(4, ch_main_aru.isChecked() ? 1 : 0);
                break;
                */
        }
    }

}
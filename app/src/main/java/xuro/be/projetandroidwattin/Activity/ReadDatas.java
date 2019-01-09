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
    private ReadTaskS7 readS7;
    private NetworkInfo network;
    private ConnectivityManager connexStatus;
    private WriteTaskS7 writeS7;
    private LinearLayout ln_main_ecrireS7;
    private CheckBox ch_main_activerouv;
    private CheckBox ch_main_activerfer;
    private CheckBox ch_main_aru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_datas);
        pb_main_progressionS7 = (ProgressBar) findViewById(R.id.pb_main_progressionS7);
        bt_main_ConnexS7 = (Button) findViewById(R.id.bt_main_ConnexS7);
        tv_main_plc = (TextView) findViewById(R.id.tv_main_plc);
        connexStatus = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        network = connexStatus.getActiveNetworkInfo();

    }

    public void onReadDatasClickManager(View v) {
        switch (v.getId()) {

            case R.id.bt_main_ConnexS7:
                if(network != null && network.isConnectedOrConnecting())
                {
                    if (bt_main_ConnexS7.getText().equals("Connexion_S7")){
                        Toast.makeText(this,network.getTypeName(),Toast.LENGTH_SHORT).show();
                        bt_main_ConnexS7.setText("Déconnexion_S7");
                        readS7 = new ReadTaskS7(v, bt_main_ConnexS7, pb_main_progressionS7, tv_main_plc);
                        readS7.Start("192.168.0.15","0", "2");
                    }
                    else{
                        readS7.Stop();
                        bt_main_ConnexS7.setText("Connexion_S7");
                        Toast.makeText(getApplication(), "Traitement interrompu par l'utilisateur !!! ",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(this,"! Connexion réseau IMPOSSIBLE !",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ch_main_activerouv:
                writeS7.setWriteBool(1, ch_main_activerouv.isChecked() ? 1 : 0);
                break;
            case R.id.ch_main_activerfer:
                writeS7.setWriteBool(2, ch_main_activerfer.isChecked() ? 1 : 0);
                break;
            case R.id.ch_main_aru:
                writeS7.setWriteBool(4, ch_main_aru.isChecked() ? 1 : 0);
                break;
        }
    }

}
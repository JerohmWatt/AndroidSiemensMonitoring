package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import xuro.be.projetandroidwattin.R;

public class EditParamsActivity extends Activity {

    RadioGroup rg_automate;
    EditText et_ip;
    EditText et_rack;
    EditText et_slot;
    Button bt_confirm;
    Button bt_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_params);
        rg_automate = findViewById(R.id.rg_editparams_pillLiquid);
        et_ip = findViewById(R.id.et_editparams_ip);
        et_rack = findViewById(R.id.et_editparams_rack);
        et_slot = findViewById(R.id.et_editparams_slot);
        bt_confirm = findViewById(R.id.bt_editparams_confirm);
        bt_cancel = findViewById(R.id.bt_editparams_cancel);
    }

    public void onEditParamsClickManager(View v) {
        Intent intent = new Intent(getApplicationContext(),UserHome.class);
        startActivity(intent);
        switch (v.getId()) {
            case R.id.bt_editparams_confirm :
                try {
                    editValues();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
                break;
            case R.id.bt_editparams_cancel :
                startActivity(intent);
                break;
        }
    }

    public void editValues() throws IOException {
        boolean editPills = true;
        int checkedButton = rg_automate.getCheckedRadioButtonId();
        if(checkedButton == -1){
        }
        else {
            if(checkedButton == R.id.rb_editparams_pills){
                editPills = true;
            }
            else{
                Log.i("texte","liquide");
                editPills = false;
            }
        }

        String [] params = readTxt();
        String ip = et_ip.getText().toString();
        String rack = et_rack.getText().toString();
        String slot = et_slot.getText().toString();
        if(editPills){
            params[0] = ip + "#";
            params[1] = rack + "#";
            params[2] = slot + "#";
        }
        else {
            Log.i("texte","liquide2 ");
            params[3] = ip + "#";
            params[4] = rack + "#";
            params[5] = slot + "#";
        }

        pushParams(params);
    }

    public String [] readTxt() throws IOException {
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
            logDatas[i] = item+"#";
            i++;
        }

        return logDatas;
    }

    public void pushParams(String [] logdatas){
        try {
            FileOutputStream confile = openFileOutput("config.txt", MODE_PRIVATE);
            for (int i = 0; i<=5; i++){
                byte[] tab;
                String a = logdatas[i];
                tab = a.getBytes();
                confile.write(tab);
            }
            confile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package xuro.be.projetandroidwattin.Automat;

import android.util.Log;
import android.widget.EditText;

import java.util.concurrent.atomic.AtomicBoolean;

import xuro.be.projetandroidwattin.Simatic_S7.S7;
import xuro.be.projetandroidwattin.Simatic_S7.S7Client;

public class WriteTaskS7{
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private Thread writeThread;
    private AutomateS7 plcS7;
    private S7Client comS7;
    private String[] parConnexion = new String[10];
    private byte[] motCommande = new byte[2];
    private int dbb;
    public void setDbb(int d){
        this.dbb = d;
    }
    public WriteTaskS7(EditText ddb){
//monAPI = new AutomateS7();
        comS7 = new S7Client();
        plcS7 = new AutomateS7();
        writeThread = new Thread(plcS7);
        this.dbb = Integer.parseInt(ddb.getText().toString());
    }
    public void Stop(){
        isRunning.set(false);
        comS7.Disconnect();
        writeThread.interrupt();
    }
    public void Start(String a, String r, String s){
        if (!writeThread.isAlive()) {
            parConnexion[0] = a;
            parConnexion[1] = r;
            parConnexion[2] = s;
            writeThread.start();
            isRunning.set(true);
        }
    }

    private class AutomateS7 implements Runnable{
        @Override
        public void run() {
            try {
                comS7.SetConnectionType(S7.S7_BASIC);
                Integer res = comS7.ConnectTo(parConnexion[0],
                        Integer.valueOf(parConnexion[1]),Integer.valueOf(parConnexion[2]));
                while(isRunning.get() && (res.equals(0))){
                    Integer writePLC = comS7.WriteArea(S7.S7AreaDB,5,dbb,2,motCommande);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void setWriteBool(String value){
        char[] array = value.toCharArray();
        for(int i =0; i< value.length(); i++){
            S7.SetBitAt(motCommande, 0, i, array[array.length-(i+1)] == '1' ? true : false);
        }
    }
    public void setWriteInt(String value){
        S7.SetWordAt(motCommande, 0, Integer.parseInt(value));
    }
}
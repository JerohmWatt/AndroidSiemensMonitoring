package xuro.be.projetandroidwattin.Automat;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.concurrent.atomic.AtomicBoolean;

import xuro.be.projetandroidwattin.R;
import xuro.be.projetandroidwattin.Simatic_S7.*;

public class ReadPillsS7 {
    private static final int MESSAGE_PRE_EXECUTE = 1;
    private static final int MESSAGE_PROGRESS_UPDATE = 2;
    private static final int MESSAGE_POST_EXECUTE = 3;
    private static final int BOTTLE_AMOUNT_UPDATE = 4;
    private static final int PILL_AMOUNT_UPDATE = 5;
    private static final int MACHINE_IS_RUNNING = 6;
    //ici toutes les constantes en fonction des données à récupérer
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private ProgressBar pb_main_progressionS7;
    private Button bt_connex;
    private View vi_main_ui;
    private TextView tv_plc;
    private TextView tv_fullBottles;
    private TextView tv_pillsAmount;
    private TextView tv_status;
    private AutomateS7 plcS7;
    private Thread readThread;
    private S7Client comS7;
    private String[] param = new String[10];
    private byte[] datasPLC = new byte[512]; //chaine de 8 bits
    private byte[] pillsPLC = new byte[2];
    private byte[] bottlesPLC = new byte[2];
    private byte[] statusPLC = new byte[2];

    public ReadPillsS7(View v, Button b, TextView t, TextView bo, TextView pa, TextView s){
        vi_main_ui = v;
        bt_connex = b;
        tv_plc = t;
        tv_fullBottles = bo;
        tv_pillsAmount = pa;
        tv_status = s;
        comS7 = new S7Client();
        plcS7 = new AutomateS7();
        readThread = new Thread(plcS7);
    }

    public void Stop(){
        isRunning.set(false);
        comS7.Disconnect();
        readThread.interrupt();
    }
    public void Start(String a, String r, String s){
        if (!readThread.isAlive()) {
            param[0] = a;
            param[1] = r;
            param[2] = s;
            readThread.start();
            isRunning.set(true);
        }
    }

    private void downloadOnPreExecute(int t) {
        Toast.makeText(vi_main_ui.getContext(),
                "Le traitement de la tâche de fond est démarré !" + "\n"
                , Toast.LENGTH_SHORT).show();
        tv_plc.setText("PLC : " + String.valueOf(t));
    }
    private void downloadOnProgressUpdate(int progress) {
        //pb_main_progressionS7.setProgress(progress);
    }
    private void downloadOnPostExecute() {
        Toast.makeText(vi_main_ui.getContext(),
                "Le traitement de la tâche de fond est terminé !"
                ,Toast.LENGTH_LONG).show();
       // pb_main_progressionS7.setProgress(0);
        tv_plc.setText("PLC : /!\\");
    }

    private void updateBottleAmount(int value){
        tv_fullBottles.setText("Nombre de bouteilles produites : "+ String.valueOf(value));
        Log.i("siemens3",String.valueOf(value));
    }

    private void updatePillAmount(int value){
        tv_pillsAmount.setText("Comprimés par bouteille : "+ String.valueOf(value));
    }

    private void updateServiceStatus(int value){
        tv_status.setText((value ==1 ? "Service : allumé" : "Service : éteint"));
    }
    private Handler monHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_PRE_EXECUTE:
                    downloadOnPreExecute(msg.arg1);
                    break;
                case MESSAGE_PROGRESS_UPDATE:
                    downloadOnProgressUpdate(msg.arg1);
                    break;
                case MESSAGE_POST_EXECUTE:
                    downloadOnPostExecute();
                    break;
                case BOTTLE_AMOUNT_UPDATE:
                    updateBottleAmount(msg.arg1);
                case PILL_AMOUNT_UPDATE:
                    updatePillAmount(msg.arg1);
                case MACHINE_IS_RUNNING:
                    updateServiceStatus(msg.arg1);
                default:
                    break;
            }
        }
    };

    private class AutomateS7 implements Runnable{
        @Override
        public void run() {
            try {
                comS7.SetConnectionType(S7.S7_BASIC);
                Integer res = comS7.ConnectTo(param[0],Integer.valueOf(param[1]),Integer.valueOf(param[2]));

                S7OrderCode orderCode = new S7OrderCode();
                Integer result = comS7.GetOrderCode(orderCode);

                int numCPU;
                if (res.equals(0) && result.equals(0)){

                    numCPU = Integer.valueOf(orderCode.Code().substring(5, 8));
                }
                else numCPU=0000;
                sendPreExecuteMessage(numCPU);
                while(isRunning.get()){
                    if (res.equals(0)){
                        int retInfo = comS7.ReadArea(S7.S7AreaDB,5,0,2,datasPLC);//info qu'on récupère > automate est activé boolean
                        int nbrpills = comS7.ReadArea(S7.S7AreaDB, 5, 15,2, pillsPLC);
                        int nbrbottles = comS7.ReadArea(S7.S7AreaDB, 5, 16, 2, bottlesPLC); //DB5 DBW16
                        int statusRead = comS7.ReadArea(S7.S7AreaDB,5, 0, 2, statusPLC);
                        int plc=1, pills,bottles,status;
//int dataB=0;
                        if (retInfo ==0) {
                            plc = S7.GetWordAt(datasPLC, 0);
                            sendProgressMessage(plc);
                        }

                        if (nbrbottles == 0) {
                            bottles = S7.GetWordAt(bottlesPLC, 0);
                            sendBottlesAmount(bottles);
                            Log.i("siemens",String.valueOf(bottles));
                        }

                        if (nbrpills == 0){
                            pills = S7.BCDtoByte(pillsPLC[0]);
                            Log.i("siemens pills",String.valueOf(pills));
                            sendPillsAmount(pills);
                        }

                        if (statusRead == 0) {
                            status = S7.GetBitAt(statusPLC, 0, 0) ? 1 : 0;
                            setServiceStatus(status);
                        }

                        Log.i("Variable A.P.I. -> ", String.valueOf(plc));
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                sendPostExecuteMessage();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        private void sendPostExecuteMessage() {
            Message postExecuteMsg = new Message();
            postExecuteMsg.what = MESSAGE_POST_EXECUTE;
            monHandler.sendMessage(postExecuteMsg);
        }
        private void sendPreExecuteMessage(int v) {
            Message preExecuteMsg = new Message();
            preExecuteMsg.what = MESSAGE_PRE_EXECUTE;
            preExecuteMsg.arg1 = v;
            monHandler.sendMessage(preExecuteMsg);
        }
        private void sendProgressMessage(int i) {
            Message progressMsg = new Message();
            progressMsg.what = MESSAGE_PROGRESS_UPDATE;
            progressMsg.arg1 = i;
            monHandler.sendMessage(progressMsg);
        }

        private void sendBottlesAmount(int i){
            Log.i("siemens 2",String.valueOf(i));
            Message bottlesAmountMsg = new Message();
            bottlesAmountMsg.what = BOTTLE_AMOUNT_UPDATE;
            bottlesAmountMsg.arg1 = i;
            monHandler.sendMessage(bottlesAmountMsg);
        }

        private void sendPillsAmount(int i){
            Message pillsAmountMsg = new Message();
            pillsAmountMsg.what = PILL_AMOUNT_UPDATE;
            pillsAmountMsg.arg1 = i;
            monHandler.sendMessage(pillsAmountMsg);
        }

        private void setServiceStatus(int i){
            Message serviceStatusMsg = new Message();
            serviceStatusMsg.what = MACHINE_IS_RUNNING;
            serviceStatusMsg.arg1 = i;
            monHandler.sendMessage(serviceStatusMsg);
        }
    }



}


package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import xuro.be.projetandroidwattin.BDD.User;
import xuro.be.projetandroidwattin.BDD.UserAccessDB;
import xuro.be.projetandroidwattin.Models.SessionManagement;
import xuro.be.projetandroidwattin.R;

public class EditUsers extends Activity {

    private TextView tv_file_datas;
    private ListView lv_file_liste;
    private SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_users);

        session = new SessionManagement(getApplicationContext());
        tv_file_datas = findViewById(R.id.tv_file_datas);
        lv_file_liste = findViewById(R.id.lv_file_liste);
        updateList();

        //when an item of the list is clicked, start the editing rights' dialog
        lv_file_liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Toast.makeText(getApplicationContext(),R.string.impossibleEditSUrights,Toast.LENGTH_LONG).show();
                }
                else {
                    dialogRights(position);
                }

            }
        });
    }
        public void onEditUsersClickManager(View v) {

            switch (v.getId()) {

                case R.id.fab_adminhome_logout:
                    session.logoutUser();
                    break;

                case R.id.bt_adminhome_editpwd:
                    Intent intent1 = new Intent(getApplicationContext(),EditSUpwd.class);
                    startActivity(intent1);

                default:
                    break;


            }
    }

    /**
     * Show a dialog asking which rights allows to the user
     * @param idUser is the position of which item of the list has been clicked
     */
    public void dialogRights(final int idUser){

            String[] singleChoiceItems = getResources().getStringArray(R.array.dialog_single_choice_array);
            int itemSelected = 0;
            final int[] choice = new int[1];
            new AlertDialog.Builder(this)
                    .setTitle(R.string.whichrights)
                    .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                            choice[0] = selectedIndex;
                        }
                    })
                    .setPositiveButton(R.string.confirmation, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            int rights;
                            if (choice[0] == 0){
                                rights = 1;
                            }
                            else {
                                rights = 2;
                            }
                            editRights(idUser+1,rights);

                        }
                    })
                    .setNegativeButton(R.string.cancelDialog, null)
                    .show();
        }

    /**
     * Edit the rights of a defined user is the DB
     * @param id user's id in the DB
     * @param rights which right should be granted to the user
     */
    public void editRights (int id, int rights){

        UserAccessDB userDB = new UserAccessDB(this);
        userDB.openForWrite();
        User user1 = new User();
        user1 = userDB.getUser(id);
        user1.setRights(rights);
        userDB.updateUser(id,user1);
        userDB.Close();
        updateList();
        }

    /**
     * Get the list of the users in the DB
      */
    public void updateList(){
        UserAccessDB userDB = new UserAccessDB(this);
        userDB.openForRead();
        ArrayList<User> tab_user = userDB.getAllUsers();
        userDB.Close();
        if (tab_user.isEmpty()) Toast.makeText(this, R.string.emptyDB,
                Toast.LENGTH_SHORT).show();
        else {
            ArrayAdapter<User> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, tab_user);
            lv_file_liste.setAdapter(adapter);
        }
    }
}

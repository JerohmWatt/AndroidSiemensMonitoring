package xuro.be.projetandroidwattin.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import xuro.be.projetandroidwattin.BDD.User;
import xuro.be.projetandroidwattin.BDD.UserAccessDB;
import xuro.be.projetandroidwattin.R;

public class EditSUpwd extends AppCompatActivity {

    private EditText et_editsupwd_pwd;
    private EditText getEt_editsupwd_pwdconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_supwd);
        et_editsupwd_pwd = findViewById(R.id.et_editsupwd_pwd);
        getEt_editsupwd_pwdconfirm = findViewById(R.id.et_editsupwd_pwdconfirm);
    }

    public void onEditUsersClickManager(View v) {
        Intent intent = new Intent(getApplicationContext(),AdminHome.class);
        switch (v.getId()) {
            case R.id.bt_editsuwd_confirm:
                String newPwd = et_editsupwd_pwd.getText().toString();
                String newPwdConfirm = getEt_editsupwd_pwdconfirm.getText().toString();
                if(newPwd.equals(newPwdConfirm)) {
                    UserAccessDB userDB = new UserAccessDB(this);
                    userDB.openForWrite();
                    User user1 = new User();
                    user1 = userDB.getUser(1);
                    user1.setPassword(newPwd);
                    userDB.updatePwd(1, user1);
                    userDB.Close();
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Les mots de passe ne correspondent pas",Toast.LENGTH_LONG).show();
                    et_editsupwd_pwd.setText("");
                    getEt_editsupwd_pwdconfirm.setText("");
                    break;
                }
                break;

            case R.id.bt_editsuwd_cancel:
                startActivity(intent);
                break;
        }
    }
}

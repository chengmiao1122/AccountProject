package guohuayu.com.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;


public class addbillActivity extends AppCompatActivity {
    private MySQLiteHelper mySQLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbill);

        mySQLiteHelper = new MySQLiteHelper(this,"finance.db", null, 1);
        /*
        *    for input a bill
        * */
    }
}
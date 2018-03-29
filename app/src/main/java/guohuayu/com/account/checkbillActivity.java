package guohuayu.com.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class checkbillActivity extends AppCompatActivity {
    private MySQLiteHelper mySQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbill);

        mySQLiteHelper = new MySQLiteHelper(this,"finance.db", null, 1);

        /*
        *    for list all the bill here
        * */
    }
}

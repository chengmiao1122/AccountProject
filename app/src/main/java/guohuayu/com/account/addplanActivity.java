package guohuayu.com.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class addplanActivity extends AppCompatActivity {
    private MySQLiteHelper mySQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplan);

        mySQLiteHelper = new MySQLiteHelper(this, "plab.db", null, 1);
        /*
        *   for input a plan data
        * */
    }
}

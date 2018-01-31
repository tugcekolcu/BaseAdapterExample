package android.tugcekolcu.baseadapterexample;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<CounterUIBean> counterList = new ArrayList<CounterUIBean>();
    private Button btnUpdateCounter;
    private Database db = new Database(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        int size = db.getListCount();
        for (int i = 0; i < 10; i++) {

            CounterUIBean counterUIBean = new CounterUIBean("Counter" + (i + 1), String.valueOf(i + 1));
            Log.d("Insert",("Inserting counters... "+counterUIBean.getCounterName()));
            if(size==0) {
                db.addCounter(counterUIBean);
            }
            counterList.add(counterUIBean);
        }

        CounterAdapter myListAdapter = new CounterAdapter(MainActivity.this.getBaseContext(), counterList);
        ListView listView = (ListView) findViewById(R.id.listViewMain);
        listView.setAdapter(myListAdapter);
        btnUpdateCounter = (Button) findViewById(R.id.btnUpdateCounters);
        btnUpdateCounter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnUpdateCounters:


                try {
                    Log.d("Update: ", "Updating ..");
                    ProgressDialog pd = new ProgressDialog(MainActivity.this);
                    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pd.setMessage("İşlem Gerçekleştiriliyor.Lütfen Bekleyiniz..");
                    pd.show();
                    db.updateCounterValues(counterList);
                    counterList = db.getAllList();
                    pd.dismiss();

                } catch (Exception e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("ERROR");
                    builder.setMessage(e.getMessage());
                    builder.show();
                } finally {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("BaseAdapter for Counters");
                    builder.setMessage("Update Operation SUCCESSFUL!");
                    builder.show();
                }

                break;

            default:
                break;
        }

    }

}

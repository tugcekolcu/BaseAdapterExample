package android.tugcekolcu.baseadapterexample;

import android.content.ClipData;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tugcekolcu on 31.01.2018.
 */

public class CounterAdapter extends BaseAdapter {

    private Context context; //context
    private ArrayList<CounterUIBean> counterList; //data source of the list adapter

    public CounterAdapter(Context context, ArrayList<CounterUIBean> counterList) {
        this.context = context;
        this.counterList = counterList;
    }

    @Override
    public int getCount() {
        return counterList.size(); //returns total of counters in the list
    }

    @Override
    public Object getItem(int position) {
        return counterList.get(position); //returns list counter at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ViewHolder holder = null;
        final ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.base_adapter_list_view_with_edittext, null);
            holder.etCounterValue = (EditText) convertView.findViewById(R.id.etCounterValue);
            holder.textViewCounterName = (TextView) convertView.findViewById(R.id.textViewCounterName);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.ref = position;

        holder.textViewCounterName.setText(counterList.get(position).getCounterName());
        holder.etCounterValue.setText(counterList.get(position).getCounterValue());
        holder.etCounterValue.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                counterList.get(holder.ref).setCounterValue(s.toString());
            }



        });

        return convertView;
    }

    private class ViewHolder {
        EditText etCounterValue;
        TextView textViewCounterName;
        int ref;
    }

}

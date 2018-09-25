package com.seventhmoon.kotzen.datatabletest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seventhmoon.kotzen.datatabletest.data.Constants;
import com.seventhmoon.kotzen.datatabletest.data.Item;
import com.seventhmoon.kotzen.datatabletest.data.ItemAdapter;
import com.seventhmoon.kotzen.datatabletest.service.TestDataTableService;
import com.seventhmoon.kotzen.datatabletest.table.DataColumn;
import com.seventhmoon.kotzen.datatabletest.table.DataRow;
import com.seventhmoon.kotzen.datatabletest.table.DataTable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private static BroadcastReceiver mReceiver = null;
    private static boolean isRegister = false;

    public static DataTable dataTable;
    public static ArrayList<Item> myList = new ArrayList<>();

    private EditText textViewName1;
    private EditText textViewQuantity;
    private EditText textViewQualtity;
    private EditText textViewCheck;
    private ListView listView;

    private ItemAdapter itemAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getBaseContext();

        textViewName1 = findViewById(R.id.textView1);
        textViewQuantity = findViewById(R.id.textView2);
        textViewQualtity = findViewById(R.id.textView3);
        textViewCheck = findViewById(R.id.textView4);
        listView = findViewById(R.id.listView);


        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSend = findViewById(R.id.btnSend);

        dataTable = new DataTable();

        DataColumn col_1 = new DataColumn("name");
        DataColumn col_2 = new DataColumn("quantity");
        DataColumn col_3 = new DataColumn("quality");
        DataColumn col_4 = new DataColumn("check");

        dataTable.Columns.Add(col_1);
        dataTable.Columns.Add(col_2);
        dataTable.Columns.Add(col_3);
        dataTable.Columns.Add(col_4);


        myList.clear();

        itemAdapter = new ItemAdapter(this, R.layout.list_item, myList);
        listView.setAdapter(itemAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataRow rw = dataTable.NewRow();

                rw.setValue("name", textViewName1.getText().toString());
                rw.setValue("quantity", textViewQuantity.getText().toString());
                rw.setValue("quality", textViewQualtity.getText().toString());
                rw.setValue("check", textViewCheck.getText().toString());

                dataTable.Rows.add(rw);

                myList.clear();
                if (itemAdapter != null) {
                    itemAdapter.notifyDataSetChanged();
                }

                for (int i=0; i<dataTable.Rows.size(); i++) {
                    Item item = new Item();
                    item.setName(dataTable.Rows.get(i).getValue("name").toString());
                    item.setQuantity(dataTable.Rows.get(i).getValue("quantity").toString());
                    item.setQuality(dataTable.Rows.get(i).getValue("quality").toString());
                    item.setCheck(dataTable.Rows.get(i).getValue("check").toString());
                    myList.add(item);
                }

                if (itemAdapter != null) {
                    itemAdapter.notifyDataSetChanged();
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getintent = new Intent(context, TestDataTableService.class);
                getintent.setAction(Constants.ACTION.ACTION_SEND_DATATABLE_ACTION);
                context.startService(getintent);
            }
        });


        final IntentFilter filter;

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //Log.e(TAG, "intent.getAction() =>>>> "+intent.getAction().toString());

                if (intent.getAction() != null) {

                    if (intent.getAction().equalsIgnoreCase(Constants.ACTION.ACTION_SEND_DATATABLE_FAILED)) {
                        Log.d(TAG, "get ACTION_SEND_DATATABLE_FAILED");
                    } else if (intent.getAction().equalsIgnoreCase(Constants.ACTION.ACTION_SEND_DATATABLE_SUCCESS)) {
                        Log.d(TAG, "get ACTION_SEND_DATATABLE_SUCCESS");
                    }
                }
            }
        };

        if (!isRegister) {
            filter = new IntentFilter();
            filter.addAction(Constants.ACTION.ACTION_SEND_DATATABLE_FAILED);
            filter.addAction(Constants.ACTION.ACTION_SEND_DATATABLE_SUCCESS);

            Log.d(TAG, "registerReceiver mReceiver");
        }
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");

        if (isRegister && mReceiver != null) {
            try {
                unregisterReceiver(mReceiver);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            isRegister = false;
            mReceiver = null;
            Log.d(TAG, "unregisterReceiver mReceiver");
        }

        super.onDestroy();
    }

    public void toast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
}

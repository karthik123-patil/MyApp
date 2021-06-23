package com.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.test.R;
import com.test.adapter.ItemDetailAdapter;
import com.test.util.AppUtil;
import com.test.util.DBUtilAsyncTask;
import com.test.util.GetItemAsyncTask;
import com.test.util.ItemTitleDAO;
import com.test.util.StringConstants;
import com.test.util.interfaces.DBUtilAsyncTaskImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DBUtilAsyncTaskImpl {

    @BindView(R.id.lstItems) ListView lstItems;
    @BindView(R.id.txtSearch)  EditText txtSearch;

    private Context mContext;
    private ArrayList<ItemTitleDAO> titleDAOS;
    private ItemDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppUtil.clearTransStatusBar(this);
        mContext = getApplicationContext();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getItems();
        setListeners();
    }

    private void getItems() {
        GetItemAsyncTask asyncTask = new GetItemAsyncTask(MainActivity.this, StringConstants.DB_URL);
        asyncTask.execute();
    }

    private void setListeners() {
        txtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                MainActivity.this.adapter.getFilter().filter(cs.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                MainActivity.this.adapter.getFilter().filter(arg0);
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void onBackgroundTaskCompleted(JSONObject response) {
        try{
            if(response != null) {
                JSONObject array = new JSONObject(response.getString(StringConstants.QUERY));
                JSONArray array1 = array.getJSONArray(StringConstants.PAGES);
                titleDAOS = new ArrayList<>();
                JSONObject obj;
                JSONObject obj1 = null;
                JSONObject obj2 = null;
                String strSource = "";
                String strTerms = "";
                if(array1.length() > 0) {
                    for(int i=0; i<array1.length(); i++) {
                        obj = array1.getJSONObject(i);
                        for(int j=0 ; j<obj.length(); j++){
                            if(obj.has(StringConstants.THUMBNAIL)){
                                obj1 = obj.getJSONObject(StringConstants.THUMBNAIL);
                                strSource = obj1.getString(StringConstants.SOURCE);
                            }else{
                                strSource = null;
                            }
                            if(obj.has(StringConstants.TERMS)) {
                                obj2 = obj.getJSONObject(StringConstants.TERMS);
                                strTerms = obj2.getString(StringConstants.DESCRIPTION).substring(2,obj2.getString(StringConstants.DESCRIPTION).length()-2);
                            }else{
                                strTerms = null;
                            }
                        }
                     titleDAOS.add(new ItemTitleDAO(obj.getString(StringConstants.TITLE), strTerms, strSource));

                    }
                }
                adapter = new ItemDetailAdapter(mContext, titleDAOS, R.layout.item_layout);
                lstItems.setAdapter(adapter);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
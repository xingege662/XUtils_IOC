package com.ioc.xinchang.xutilsinject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ioc.xinchang.xutilsinject.annotion.ContentView;
import com.ioc.xinchang.xutilsinject.annotion.OnLongClick;
import com.ioc.xinchang.xutilsinject.annotion.Onclick;
import com.ioc.xinchang.xutilsinject.annotion.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    public final static String TAG = "tag";
    @ViewInject(R.id.tv)
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: "+tv.getText().toString());
    }
    @Onclick(R.id.btn1 )
    public void onClick(View view){
        Toast.makeText(this,"点击",Toast.LENGTH_SHORT).show();
    }
    @OnLongClick(R.id.btn2)
    public boolean onLongClick(View view){
        Toast.makeText(this,"长按点击",Toast.LENGTH_SHORT).show();
        return false;
    }
}

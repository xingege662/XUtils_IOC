package com.ioc.xinchang.xutilsinject;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by xinchang on 2017/3/3.
 */

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.inject(this);
    }
}

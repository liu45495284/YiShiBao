package com.xnliang.yishibao.presenter;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by JackLiu on 2018-01-31.
 */

public interface ItmeCallBackListener {
    public void gridViewItemClickListener(AdapterView<?> parent, View view, int position, long id);
    public void travelBackListener();
}

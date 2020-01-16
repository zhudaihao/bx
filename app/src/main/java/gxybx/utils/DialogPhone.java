package gxybx.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import cn.gxybx.R;

/**
 * 打电话 对话框
 */
public class DialogPhone extends Dialog {
    private String phone;
    private Context context;


    public DialogPhone(Context context, String phone) {
        super(context, R.style.CursorDialogNotFloatTheme);
        this.context = context;
        this.phone = phone;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_phone);

        TextView tvCancel = findViewById(R.id.tv_cancel);
        TextView tvYes = findViewById(R.id.tv_yes);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                context.startActivity(intent);
            }
        });


    }
}

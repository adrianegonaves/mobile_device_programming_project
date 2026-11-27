// RespondTime.java
package pdm.projeto.vivabemapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RespondTime extends AppCompatActivity {
    public static final String KEY_H = "KEY_H";
    public static final String KEY_MIN = "KEY_MIN";

    NumberPicker mNpHour;
    NumberPicker mNpMinute;
    Button mBtnConfirm;
    Button mBtnCancel;

    View.OnClickListener mBtnHandler = new View.OnClickListener() {
        @Override
        public void onClick(View pView) {
            boolean bConfirm = pView.getId() == R.id.idBtnConfirm;
            boolean bCancel = pView.getId() == R.id.idBtnCancel;

            if (bConfirm) handleConfirmResult();
            if (bCancel) handleCancelResult();
        }
    };

    void handleConfirmResult() {
        Intent intentData = new Intent();
        intentData.putExtra(KEY_H, mNpHour.getValue());
        intentData.putExtra(KEY_MIN, mNpMinute.getValue());
        RespondTime.this.setResult(RESULT_OK, intentData);
        finish();
    }//handleConfirmResult

    void handleCancelResult() {
        Intent intentData = new Intent();
        RespondTime.this.setResult(RESULT_CANCELED, intentData);
        finish();
    }//handleCancelResult

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_respond_time);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new androidx.core.view.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            }
        });

        init();
    }//onCreate

    void init() {
        mNpHour = findViewById(R.id.idNPHour);
        mNpMinute = findViewById(R.id.idNPMinute);
        mBtnConfirm = findViewById(R.id.idBtnConfirm);
        mBtnCancel = findViewById(R.id.idBtnCancel);

        mNpHour.setMinValue(0);
        mNpHour.setMaxValue(23);
        mNpHour.setValue(8);

        mNpMinute.setMinValue(0);
        mNpMinute.setMaxValue(59);
        mNpMinute.setValue(0);

        mBtnConfirm.setOnClickListener(mBtnHandler);
        mBtnCancel.setOnClickListener(mBtnHandler);
    }//init
}//class RespondTime

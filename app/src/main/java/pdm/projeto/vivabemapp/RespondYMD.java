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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RespondYMD extends AppCompatActivity {
    NumberPicker.OnValueChangeListener
            mNpHandler = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            int y = mNpYear.getValue();
            int m = mNpMonth.getValue();
            int dMax = AmDate.numberOfDaysInMonth(
                    y, m
            );
            mNpDay.setMaxValue(dMax);
        }
    };


    public static final String KEY_Y = "KEY_Y";
    public static final String KEY_M = "KEY_M";
    public static final String KEY_D = "KEY_D";

    NumberPicker mNpYear, mNpMonth, mNpDay;
    Button mBtnConfirm, mBtnCancel;

    View.OnClickListener mBtnHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean bConfirm, bCancel;
            bConfirm = view.getId()==R.id.idBtnConfirm;
            bCancel = view.getId()==R.id.idBtnCancel;

            if (bConfirm) handleConfirmResult();
            if (bCancel) handleCancelResult();
        }
    };

    void handleConfirmResult(){
        Intent intentData = new Intent();
        intentData.putExtra(
                KEY_Y,mNpYear.getValue()
        );
        intentData.putExtra(
                KEY_M,mNpMonth.getValue()
        );
        intentData.putExtra(
                KEY_D,mNpDay.getValue()
        );
        RespondYMD.this.setResult(
                RESULT_OK,
                intentData
        );
        finish();
    }//handleConfirmResult

    void handleCancelResult() {
        Intent intentData = new Intent();
        RespondYMD.this.setResult(
                RESULT_CANCELED,
                intentData
        );
        finish();
    }//handleCancelResult

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_respond_ymd);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
    }

    void init(){
        mNpYear = findViewById(R.id.idNPYear);
        mNpMonth = findViewById(R.id.idNPMonth);
        mNpDay = findViewById(R.id.idNPDay);

        initNumberPickers();

        mBtnConfirm = findViewById(R.id.idBtnConfirm);
        mBtnCancel = findViewById(R.id.idBtnCancel);

        // Behaviours
        mBtnConfirm.setOnClickListener(mBtnHandler);
        mBtnCancel.setOnClickListener(mBtnHandler);

        mNpYear.setOnValueChangedListener(
                mNpHandler
        );
        mNpMonth.setOnValueChangedListener(
                mNpHandler
        );
        mNpDay.setOnValueChangedListener(
                mNpHandler
        );
    }

    // TODO : put it in AmUtil
    Map<String, Integer> currentYMD (){
        Map<String, Integer> ret = new HashMap<>();
        Calendar c = Calendar.getInstance();
        int y,m,d;
        y = c.get(Calendar.YEAR);
        m = c.get(Calendar.MONTH)+1;
        d = c.get(Calendar.DAY_OF_MONTH);

        ret.put("Y", y);
        ret.put("M", m);
        ret.put("D", d);

        return ret;
    }//currentYMD

    void initNumberPickers(){
        Map<String, Integer> dictYMD =
                currentYMD();
        mNpYear.setMinValue(1900);
        mNpYear.setMaxValue(2100);
        mNpYear.setValue(dictYMD.get("Y"));

        mNpMonth.setMinValue(1);
        mNpMonth.setMaxValue(12);
        mNpMonth.setValue(dictYMD.get("M"));

        mNpDay.setMinValue(1);
        mNpDay.setMaxValue(31);
        mNpDay.setValue(dictYMD.get("D"));
    }//initNumberPickers
}
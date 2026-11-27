// RespondFrequency.java
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

public class RespondFrequency extends AppCompatActivity {
    public static final String KEY_QTY = "KEY_QTY";
    public static final String KEY_UNIT = "KEY_UNIT";
    public static final String[] UNITS = { "Hora", "Dia", "Semana" };

    NumberPicker mNpQty;
    NumberPicker mNpUnit;
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
        intentData.putExtra(KEY_QTY, mNpQty.getValue());
        intentData.putExtra(KEY_UNIT, UNITS[mNpUnit.getValue()]);
        RespondFrequency.this.setResult(RESULT_OK, intentData);
        finish();
    }//handleConfirmResult

    void handleCancelResult() {
        Intent intentData = new Intent();
        RespondFrequency.this.setResult(RESULT_CANCELED, intentData);
        finish();
    }//handleCancelResult

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_respond_frequency);
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
        mNpQty = findViewById(R.id.idNPQty);
        mNpUnit = findViewById(R.id.idNPUnit);
        mBtnConfirm = findViewById(R.id.idBtnConfirm);
        mBtnCancel = findViewById(R.id.idBtnCancel);

        mNpQty.setMinValue(1);
        mNpQty.setMaxValue(30);
        mNpQty.setValue(1);

        mNpUnit.setMinValue(0);
        mNpUnit.setMaxValue(UNITS.length - 1);
        mNpUnit.setDisplayedValues(UNITS);
        mNpUnit.setValue(1);

        mBtnConfirm.setOnClickListener(mBtnHandler);
        mBtnCancel.setOnClickListener(mBtnHandler);
    }//init
}//class RespondFrequency

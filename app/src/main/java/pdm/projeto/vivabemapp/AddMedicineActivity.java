package pdm.projeto.vivabemapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

import java.util.Calendar;

public class AddMedicineActivity extends AppCompatActivity {

    // membros de dados
    VivaBemUtil mUtil;
    EditText mEtMedicineName;
    EditText mEtMedicineNote;
    MaterialCardView mCardDate;
    TextView mTvDateValue;
    MaterialCardView mCardTime;
    TextView mTvTimeValue;
    MaterialCardView mCardFrequency;
    TextView mTvFrequencyValue;
    MaterialCardView mCardDose;
    TextView mTvDoseValue;
    LinearLayout mCustomBottomNav;

    int default_year, default_month, default_day;
    int default_hour = 8;
    int default_minute = 0;
    int default_qty = 1;
    AmDate mDate;

    ActivityResultContract<Intent, ActivityResult> arcForGetDate =
            new ActivityResultContracts.StartActivityForResult();

    ActivityResultCallback<ActivityResult> arcbForGetDate =
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    boolean bConfirm = o.getResultCode() == RESULT_OK;
                    boolean bCancel = o.getResultCode() == RESULT_CANCELED;

                    if (bConfirm) {
                        Intent data = o.getData();
                        boolean bCanExtractYMD =
                                data != null
                                        && data.getExtras() != null
                                        && data.getExtras().containsKey(RespondYMD.KEY_Y)
                                        && data.getExtras().containsKey(RespondYMD.KEY_M)
                                        && data.getExtras().containsKey(RespondYMD.KEY_D);

                        if (bCanExtractYMD) {
                            int y = data.getIntExtra(RespondYMD.KEY_Y, default_year);
                            int m = data.getIntExtra(RespondYMD.KEY_M, default_month);
                            int d = data.getIntExtra(RespondYMD.KEY_D, default_day);
                            receiveDate(y, m, d);
                        }
                    }

                    if (bCancel) {
                        // a data no cartão permanece
                    }
                }
            };

    ActivityResultLauncher<Intent> arlForGetDate =
            registerForActivityResult(arcForGetDate, arcbForGetDate);

    ActivityResultContract<Intent, ActivityResult> arcForGetTime =
            new ActivityResultContracts.StartActivityForResult();

    ActivityResultCallback<ActivityResult> arcbForGetTime =
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    boolean bConfirm = o.getResultCode() == RESULT_OK;
                    boolean bCancel = o.getResultCode() == RESULT_CANCELED;

                    if (bConfirm) {
                        Intent data = o.getData();
                        boolean bCanExtractHM =
                                data != null
                                        && data.getExtras() != null
                                        && data.getExtras().containsKey(RespondTime.KEY_H)
                                        && data.getExtras().containsKey(RespondTime.KEY_MIN);

                        if (bCanExtractHM) {
                            int h = data.getIntExtra(RespondTime.KEY_H, default_hour);
                            int min = data.getIntExtra(RespondTime.KEY_MIN, default_minute);
                            receiveTime(h, min);
                        }
                    }

                    if (bCancel) {
                        // o horário no cartão permanece
                    }
                }
            };

    ActivityResultLauncher<Intent> arlForGetTime =
            registerForActivityResult(arcForGetTime, arcbForGetTime);

    ActivityResultContract<Intent, ActivityResult> arcForGetFrequency =
            new ActivityResultContracts.StartActivityForResult();

    ActivityResultCallback<ActivityResult> arcbForGetFrequency =
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    boolean bConfirm = o.getResultCode() == RESULT_OK;
                    boolean bCancel = o.getResultCode() == RESULT_CANCELED;

                    if (bConfirm) {
                        Intent data = o.getData();
                        boolean bCanExtractFrequency =
                                data != null
                                        && data.getExtras() != null
                                        && data.getExtras().containsKey(RespondFrequency.KEY_QTY)
                                        && data.getExtras().containsKey(RespondFrequency.KEY_UNIT);

                        if (bCanExtractFrequency) {
                            int qty = data.getIntExtra(RespondFrequency.KEY_QTY, default_qty);
                            String unit = data.getStringExtra(RespondFrequency.KEY_UNIT);
                            receiveFrequency(qty, unit);
                        }
                    }

                    if (bCancel) {
                        // a frequência no cartão permanece
                    }
                }
            };

    ActivityResultLauncher<Intent> arlForGetFrequency =
            registerForActivityResult(arcForGetFrequency, arcbForGetFrequency);

    View.OnClickListener mBtnHandler = new View.OnClickListener() {
        @Override
        public void onClick(View pView) {
            boolean bDate = pView.getId() == R.id.idCardDate;
            boolean bTime = pView.getId() == R.id.idCardTime;
            boolean bFrequency = pView.getId() == R.id.idCardFrequency;
            boolean bDose = pView.getId() == R.id.idCardDose;

            if (bDate) handleGetDate();
            if (bTime) handleGetTime();
            if (bFrequency) handleGetFrequency();
            if (bDose) handleGetDose();
        }
    };

    void handleGetDate() {
        Intent goRespondYMD = new Intent(
                AddMedicineActivity.this,
                RespondYMD.class
        );
        arlForGetDate.launch(goRespondYMD);
    }//handleGetDate

    void receiveDate(int pY, int pM, int pD) {
        mDate = new AmDate(pY, pM, pD);
        mTvDateValue.setText(mDate.toString());
    }//receiveDate

    void handleGetTime() {
        Intent goRespondTime = new Intent(
                AddMedicineActivity.this,
                RespondTime.class
        );
        arlForGetTime.launch(goRespondTime);
    }//handleGetTime

    void receiveTime(int pH, int pMin) {
        String hm = String.format("%02d:%02d", pH, pMin);
        mTvTimeValue.setText(hm);
    }//receiveTime

    void handleGetFrequency() {
        Intent goRespondFrequency = new Intent(
                AddMedicineActivity.this,
                RespondFrequency.class
        );
        arlForGetFrequency.launch(goRespondFrequency);
    }//handleGetFrequency

    void receiveFrequency(int pQty, String pUnit) {
        String text = String.format("%d / %s", pQty, pUnit);
        mTvFrequencyValue.setText(text);
    }//receiveFrequency

    void handleGetDose() {
        final EditText etDose = new EditText(this);
        etDose.setHint(R.string.str_hint_dose);

        AlertDialog.Builder db = new AlertDialog.Builder(this);
        db.setTitle(R.string.str_title_dose);
        db.setView(etDose);
        db.setPositiveButton(R.string.str_btn_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String dose = etDose.getText().toString().trim();
                boolean bHasText = dose.length() > 0;
                if (bHasText) {
                    mTvDoseValue.setText(dose);
                }
            }
        });
        db.setNegativeButton(R.string.str_btn_cancel, null);
        db.show();
    }//handleGetDose

    void initDefaults() {
        Calendar c = Calendar.getInstance();
        default_year = c.get(Calendar.YEAR);
        default_month = c.get(Calendar.MONTH) + 1;
        default_day = c.get(Calendar.DAY_OF_MONTH);
    }//initDefaults

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_medicine);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.idClAddMedicine), new androidx.core.view.OnApplyWindowInsetsListener() {
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
        mUtil = new VivaBemUtil(this);
        initDefaults();

        mEtMedicineName = findViewById(R.id.idEtMedicineName);
        mEtMedicineNote = findViewById(R.id.idEtMedicineNote);
        mCardDate = findViewById(R.id.idCardDate);
        mTvDateValue = findViewById(R.id.idTvDateValue);
        mCardTime = findViewById(R.id.idCardTime);
        mTvTimeValue = findViewById(R.id.idTvTimeValue);
        mCardFrequency = findViewById(R.id.idCardFrequency);
        mTvFrequencyValue = findViewById(R.id.idTvFrequencyValue);
        mCardDose = findViewById(R.id.idCardDose);
        mTvDoseValue = findViewById(R.id.idTvDoseValue);
        mCustomBottomNav = findViewById(R.id.idCustomBottomNav);

        Object[] aRelevant = {
                mEtMedicineName, mEtMedicineNote,
                mCardDate, mTvDateValue,
                mCardTime, mTvTimeValue,
                mCardFrequency, mTvFrequencyValue,
                mCardDose, mTvDoseValue,
                mCustomBottomNav
        };

        for (Object o : aRelevant) {
            if (o == null) {
                String msg = String.format(
                        "Objeto relevante encontrado nulo!"
                );
                mUtil.fb(msg);
                finish();
                return;
            }
        }

        mCardDate.setOnClickListener(mBtnHandler);
        mCardTime.setOnClickListener(mBtnHandler);
        mCardFrequency.setOnClickListener(mBtnHandler);
        mCardDose.setOnClickListener(mBtnHandler);
        BottomNav.setup(this, mCustomBottomNav, R.id.idNavMedicines);
    }//init
}//class AddMedicineActivity

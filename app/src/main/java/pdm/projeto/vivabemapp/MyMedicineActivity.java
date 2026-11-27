package pdm.projeto.vivabemapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

public class MyMedicineActivity extends AppCompatActivity {

    // menbros de dados
    VivaBemUtil mUtil;
    TextView mTvCurrentDate;
    Button mBtnAddMedicine;
    MaterialCardView mCardMedication;
    TextView mTvMedicineTime;
    TextView mTvMedicineDosage;
    Button mBtnTakeMedicine;
    ImageButton mIbDeleteMedicine;
    LinearLayout mCustomBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_medicine);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.idClMyMedicines), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

    }
    View.OnClickListener mBtnHandler = new View.OnClickListener() {
        @Override
        public void onClick(View pView) {
            boolean bAdd = pView.getId() == R.id.idBtnAddMedicine;
            if (bAdd) actionAddMedicine();
        }
    };

    void actionAddMedicine() {
        Intent goForm = new Intent(
                MyMedicineActivity.this,
                AddMedicineActivity.class
        );
        startActivity(goForm);
    }

    void init () {
        mUtil = new VivaBemUtil(this);

        mTvCurrentDate = findViewById(R.id.idTvCurrentDate);
        mBtnAddMedicine = findViewById(R.id.idBtnAddMedicine);
        mCardMedication = findViewById(R.id.idCardMedication);
        mTvMedicineTime = findViewById(R.id.idTvMedicineTime);
        mTvMedicineDosage = findViewById(R.id.idTvMedicineDosage);
        mBtnTakeMedicine = findViewById(R.id.idBtnTakeMedicine);
        mIbDeleteMedicine = findViewById(R.id.idIbDeleteMedicine);
        mCustomBottomNav = findViewById(R.id.idCustomBottomNav);


        Object[] aRelevant = {
                mTvCurrentDate, mBtnAddMedicine,
                mCardMedication, mTvMedicineTime, mTvMedicineDosage,
                mBtnTakeMedicine, mIbDeleteMedicine,
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

        BottomNav.setup(this, mCustomBottomNav, R.id.idNavMedicines);
        mBtnAddMedicine.setOnClickListener(mBtnHandler);
    }

}
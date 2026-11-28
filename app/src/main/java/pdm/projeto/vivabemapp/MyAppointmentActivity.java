package pdm.projeto.vivabemapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MyAppointmentActivity extends AppCompatActivity {

    // membros de dados
    VivaBemUtil mUtil;
    DBConsultas mDB;
    TextView mTvCurrentDate;
    Button mBtnScheduleAppointment;
    Button mBtnEmptySchedule;
    ListView mLvAppointments;
    LinearLayout mLlEmptyAppointments;
    LinearLayout mCustomBottomNav;

    View.OnClickListener mBtnHandler = new View.OnClickListener() {
        @Override
        public void onClick(View pView) {
            boolean bSchedule = pView.getId() == R.id.idBtnScheduleAppointment;
            boolean bEmptySchedule = pView.getId() == R.id.idBtnEmptySchedule;

            if (bSchedule) actionScheduleAppointment();
            if (bEmptySchedule) actionScheduleAppointment();
        }
    };

    void actionScheduleAppointment() {
        Intent goForm = new Intent(
                MyAppointmentActivity.this,
                AddAppointmentActivity.class
        );
        startActivity(goForm);
    }//actionScheduleAppointment

    void showAppointments() {
        boolean bReady = mDB != null
                && mLvAppointments != null
                && mLlEmptyAppointments != null;
        if (!bReady) {
            return;
        }

        ArrayList<Consulta> a = mDB.selectAllConsultas();
        boolean bEmpty = a.isEmpty();
        if (bEmpty) {
            mLvAppointments.setVisibility(View.GONE);
            mLlEmptyAppointments.setVisibility(View.VISIBLE);
            return;
        }

        mLlEmptyAppointments.setVisibility(View.GONE);
        mLvAppointments.setVisibility(View.VISIBLE);

        MyAdapterForConsulta adapter = new MyAdapterForConsulta(
                this,
                R.layout.item_appointment,
                a,
                mDB
        );
        mLvAppointments.setAdapter(adapter);
    }//showAppointments

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_appointment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.idClMyAppointment), new androidx.core.view.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            }
        });

        init();
    }//onCreate

    @Override
    protected void onResume() {
        super.onResume();
        showAppointments();
    }//onResume

    void init() {
        mUtil = new VivaBemUtil(this);
        mDB = new DBConsultas(this);

        mTvCurrentDate = findViewById(R.id.idTvCurrentDate);
        mBtnScheduleAppointment = findViewById(R.id.idBtnScheduleAppointment);
        mBtnEmptySchedule = findViewById(R.id.idBtnEmptySchedule);
        mLvAppointments = findViewById(R.id.idLvAppointments);
        mLlEmptyAppointments = findViewById(R.id.idLlEmptyAppointments);
        mCustomBottomNav = findViewById(R.id.idCustomBottomNav);

        Object[] aRelevant = {
                mTvCurrentDate, mBtnScheduleAppointment,
                mBtnEmptySchedule, mLvAppointments,
                mLlEmptyAppointments,
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

        mBtnScheduleAppointment.setOnClickListener(mBtnHandler);
        mBtnEmptySchedule.setOnClickListener(mBtnHandler);
        BottomNav.setup(this, mCustomBottomNav, R.id.idNavAppointments);
    }//init
}//class MyAppointmentActivity

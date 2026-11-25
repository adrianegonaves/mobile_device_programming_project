package pdm.projeto.vivabemapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // membros de dados
    TextView mTvCurrentDate;
    Button mBtnEmergency;
    MaterialCardView mCardMedication;
    RadioButton mRbMedicineCheck;
    TextView mTvMedicineTime;
    TextView mTvMedicineName;
    TextView mTvMedicineDosage;
    Button mBtnTakeMedicine;
    MaterialCardView mCardAppointment;
    TextView mTvMedicineCounter;

    // barra de navegação nativa
    LinearLayout mCustomBottomNav;
    List<View> mNavTabViews;

    VivaBemUtil mUtil;

    // variável para controlar o estado do remédio de teste
    boolean mIsTomado = false;

    // lista de dados fictícios
    List<Medicamento> mListaMedicamentosMock;

    // manipuladores de eventos
    View.OnClickListener mBtnClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View pView) {
            boolean bBtnEmergency = pView.getId() == R.id.idBtnEmergency;
            boolean bBtnPlaySound = pView.getId() == R.id.idBtnTakeMedicine;

            if (bBtnEmergency) {
                respondBtnEmergency();
            } else if (bBtnPlaySound) {
                respondBtnTakeMedicine();
            }
        } // onClick
    }; // mBtnClickHandler

    void respondBtnEmergency() {
        String msg = String.format(
                "Botão de emergência clicado"
        );
        mUtil.fb(msg);
    }

    void respondBtnTakeMedicine() {
        // 1. Inverte o estado booleano local
        mIsTomado = !mIsTomado;

        // 2. Sincroniza o estado no objeto da lista mock
        if (mListaMedicamentosMock != null && !mListaMedicamentosMock.isEmpty()) {
            mListaMedicamentosMock.get(0).setTomado(mIsTomado);
        }

        // 3. Atualiza os componentes visuais do card e o contador no topo
        atualizarEstadoRemedio();
        atualizarContador();

        // 4. Feedback visual via Toast
        if (mIsTomado) {
            mUtil.fb("Medicamento marcado como TOMADO!");
        } else {
            mUtil.fb("Medicamento desmarcado!");
        }
    }

    void atualizarEstadoRemedio() {
        if (mIsTomado) {
            String textPlayMedicatioCheck = ContextCompat.getString(this, R.string.play_medication_check);
            mBtnTakeMedicine.setText(textPlayMedicatioCheck);

            int corVerde = ContextCompat.getColor(this, R.color.accents_green);
            int corVerdeClaro = ContextCompat.getColor(this, R.color.accents_green_claro);

            mBtnTakeMedicine.setBackgroundTintList(android.content.res.ColorStateList.valueOf(corVerde));

            mCardMedication.setCardBackgroundColor(corVerdeClaro);
            mCardMedication.setStrokeColor(corVerde);

            mRbMedicineCheck.setChecked(true);
            mRbMedicineCheck.setButtonDrawable(R.drawable.outline_check_circle_24);
            mRbMedicineCheck.setBackground(null);

        } else {
            mBtnTakeMedicine.setText(R.string.play_medication_sound);

            int corTextoPadrao = ContextCompat.getColor(this, R.color.white);
            int corPrimary = ContextCompat.getColor(this, R.color.primary);
            int corGrey_3 = ContextCompat.getColor(this, R.color.grey_3);

            mBtnTakeMedicine.setBackgroundTintList(android.content.res.ColorStateList.valueOf(corPrimary));

            mBtnTakeMedicine.setTextColor(corTextoPadrao);
            mCardMedication.setCardBackgroundColor(corTextoPadrao);
            mCardMedication.setStrokeColor(corGrey_3);

            mRbMedicineCheck.setChecked(false);
            mRbMedicineCheck.setButtonDrawable(R.drawable.outline_circle_24);
            mRbMedicineCheck.setBackground(null);
        }
    }

    void atualizarContador() {
        if (mListaMedicamentosMock == null || mListaMedicamentosMock.isEmpty()) {
            mTvMedicineCounter.setText("0 de 0");
            return;
        }

        int totalMedicamentos = mListaMedicamentosMock.size();
        int tomados = 0;

        for (Medicamento med : mListaMedicamentosMock) {
            if (med.isTomado()) {
                tomados++;
            }
        }

        String textoContador = tomados + " de " + totalMedicamentos;
        mTvMedicineCounter.setText(textoContador);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        mUtil = new VivaBemUtil(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        setupCustomBottomNav(R.menu.bottom_nav_menu);
        carregarDadosMock();

    } // onCreate

    // padrão init
    void init() {
        // inicialização dos membros de dados
        mTvCurrentDate = findViewById(R.id.idTvCurrentDate);
        mBtnEmergency = findViewById(R.id.idBtnEmergency);
        mCardMedication = findViewById(R.id.idCardMedication);
        mRbMedicineCheck = findViewById(R.id.idRbMedicineCheck);
        mTvMedicineTime = findViewById(R.id.idTvMedicineTime);
        mTvMedicineName = findViewById(R.id.idTvMedicineName);
        mTvMedicineDosage = findViewById(R.id.idTvMedicineDosage);
        mBtnTakeMedicine = findViewById(R.id.idBtnTakeMedicine);
        mCardAppointment = findViewById(R.id.idCardAppointment);
        mTvMedicineCounter = findViewById(R.id.idTvMedicineCounter);
        mCustomBottomNav = findViewById(R.id.idCustomBottomNav);

        Object[] aRelevant = {
                mTvCurrentDate, mBtnEmergency,
                mCardMedication, mRbMedicineCheck,
                mTvMedicineTime, mTvMedicineName, mTvMedicineDosage,
                mBtnTakeMedicine, mCardAppointment, mTvMedicineCounter,
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

        // definição dos comportamentos (listeners)
        mBtnEmergency.setOnClickListener(mBtnClickHandler);
        mBtnTakeMedicine.setOnClickListener(mBtnClickHandler);

        mRbMedicineCheck.setOnClickListener(v -> {
            boolean checked = mRbMedicineCheck.isChecked();
            String msg = String.format(
                    "Medicamento checado: " + checked
            );
            mUtil.fb(msg);
        });

        mCardMedication.setOnLongClickListener(v -> {
            boolean novoEstado = !mCardMedication.isChecked();
            mCardMedication.setChecked(novoEstado);
            return true;
        });
    } // init

    void setupCustomBottomNav(int menuResId) {
        PopupMenu popupMenu = new PopupMenu(this, mCustomBottomNav);
        getMenuInflater().inflate(menuResId, popupMenu.getMenu());
        Menu menu = popupMenu.getMenu();

        mNavTabViews = new ArrayList<>();

        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            View itemView = getLayoutInflater().inflate(R.layout.item_bottom_nav, mCustomBottomNav, false);

            ImageView ivIcon = itemView.findViewById(R.id.idNavIcon);
            TextView tvTitle = itemView.findViewById(R.id.idNavTitle);

            ivIcon.setImageDrawable(menuItem.getIcon());
            tvTitle.setText(menuItem.getTitle());

            final int itemId = menuItem.getItemId();
            itemView.setOnClickListener(v -> {
                for (View tab : mNavTabViews) {
                    tab.setSelected(false);
                }
                itemView.setSelected(true);
            });

            // Deixa a primeira aba ("Hoje") selecionada por padrão
            if (i == 0) {
                itemView.setSelected(true);
            }

            mNavTabViews.add(itemView);
            mCustomBottomNav.addView(itemView);
        }
    }

    void carregarDadosMock() {
        mListaMedicamentosMock = new ArrayList<>();

        mListaMedicamentosMock.add(new Medicamento("08:00", "Losartana", "50 mg — 1 comprimido", false));
        mListaMedicamentosMock.add(new Medicamento("12:00", "Paracetamol", "750 mg — 1 comprimido", false));

        if (!mListaMedicamentosMock.isEmpty()) {
            Medicamento med = mListaMedicamentosMock.get(0);
            mTvMedicineTime.setText(med.getHora());
            mTvMedicineName.setText(med.getNome());
            mTvMedicineDosage.setText(med.getDosagem());
            mIsTomado = med.isTomado();
            atualizarEstadoRemedio();
        }

        atualizarContador();
    }
} // MainActivity
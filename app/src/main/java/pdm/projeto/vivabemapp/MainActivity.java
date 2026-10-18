package pdm.projeto.vivabemapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. Encontre o card pelo ID definido no XML
        MaterialCardView card = findViewById(R.id.card);

        // 3. Configure o clique longo em Java correto
        card.setOnLongClickListener(v -> {
            // Alterna o estado de checado
            boolean novoEstado = !card.isChecked();
            card.setChecked(novoEstado);

            // Retorna true indicando que o clique longo foi consumido
            return true;
        });

        // falta o padrão init do prof, resolver!!
    }
}
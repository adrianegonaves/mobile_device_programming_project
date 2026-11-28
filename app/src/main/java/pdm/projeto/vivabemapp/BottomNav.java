package pdm.projeto.vivabemapp;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BottomNav {

    public static void setup(AppCompatActivity activity, LinearLayout container, int selectedItemId) {
        PopupMenu popupMenu = new PopupMenu(activity, container);
        activity.getMenuInflater().inflate(R.menu.bottom_nav_menu, popupMenu.getMenu());
        Menu menu = popupMenu.getMenu();

        List<View> tabs = new ArrayList<>();

        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            View itemView = activity.getLayoutInflater()
                    .inflate(R.layout.item_bottom_nav, container, false);

            ImageView ivIcon = itemView.findViewById(R.id.idNavIcon);
            TextView tvTitle = itemView.findViewById(R.id.idNavTitle);
            ivIcon.setImageDrawable(menuItem.getIcon());
            tvTitle.setText(menuItem.getTitle());

            final int itemId = menuItem.getItemId();
            itemView.setSelected(itemId == selectedItemId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View pView) {
                    boolean bSame = itemId == selectedItemId;
                    boolean bHome = itemId == R.id.idNavHome;
                    boolean bMedicines = itemId == R.id.idNavMedicines;
                    boolean bAppointments = itemId == R.id.idNavAppointments;

                    if (bSame) {
                        return;
                    }

                    Class<?> destino = null;
                    if (bHome) {
                        destino = MainActivity.class;
                    }
                    if (bMedicines) {
                        destino = MyMedicineActivity.class;
                    }
                    if (bAppointments) {
                        destino = MyAppointmentActivity.class;
                    }

                    if (destino != null) {
                        Intent intent = new Intent(activity, destino);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        activity.startActivity(intent);
                        return;
                    }

                    for (View tab : tabs) {
                        tab.setSelected(false);
                    }
                    itemView.setSelected(true);
                }
            });

            tabs.add(itemView);
            container.addView(itemView);
        }
    }
}
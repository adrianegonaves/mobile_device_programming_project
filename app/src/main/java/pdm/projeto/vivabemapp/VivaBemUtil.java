package pdm.projeto.vivabemapp;

import android.app.Activity;
import android.widget.Toast;

public class VivaBemUtil {
    public static final String STAMP = "@VivaBemUtil";
    Activity mB;

    public VivaBemUtil(Activity pA){
        this.mB = pA;
    }//

    public void fb(String pMsg){
        Toast t = Toast.makeText(
                this.mB, // context
                pMsg, // message
                Toast.LENGTH_SHORT // duration
        );
        t.show();
    }//fb

}

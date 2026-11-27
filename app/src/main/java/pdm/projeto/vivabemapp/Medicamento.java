// Medicamento.java
package pdm.projeto.vivabemapp;

public class Medicamento {
    String mHora;
    String mNome;
    String mDosagem;
    boolean mTomado;

    public Medicamento(
            String pHora,
            String pNome,
            String pDosagem,
            boolean pTomado
    ) {
        this.mHora = pHora;
        this.mNome = pNome;
        this.mDosagem = pDosagem;
        this.mTomado = pTomado;
    }//Medicamento

    @Override
    public String toString() {
        return String.format(
                "%s - %s - %s",
                this.mHora,
                this.mNome,
                this.mDosagem
        );
    }//toString
}//class Medicamento

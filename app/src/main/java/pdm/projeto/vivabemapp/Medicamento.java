package pdm.projeto.vivabemapp;

public class Medicamento {
    private String hora;
    private String nome;
    private String dosagem;
    private boolean tomado;

    // Construtor
    public Medicamento(String hora, String nome, String dosagem, boolean tomado) {
        this.hora = hora;
        this.nome = nome;
        this.dosagem = dosagem;
        this.tomado = tomado;
    }

    // Getters
    public String getHora() { return hora; }
    public String getNome() { return nome; }
    public String getDosagem() { return dosagem; }
    public boolean isTomado() { return tomado; }


    public void setTomado(boolean tomado) {
        this.tomado = tomado;
    }
}
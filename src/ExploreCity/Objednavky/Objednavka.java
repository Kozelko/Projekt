package ExploreCity.Objednavky;

public class Objednavka {
    public String Mesto;
    boolean Zrusena;
    public int Cena;
    public Boolean Kostol;
    public Boolean Restauracie;
    public Boolean Kino;
    public Boolean Park;
    public Boolean Muzeum;
    public Boolean Hrad;
    public String niecoOstatne;

    public Objednavka(String Mesto, Boolean Kostol, Boolean Restauracie, Boolean Kino, Boolean Park, Boolean Muzeum, Boolean Hrad, String niecoOstatne) {
        this.Mesto = Mesto;
        this.Zrusena = false;
        // this.Cena = Cena;
        this.Kostol = Kostol;
        this.Restauracie = Restauracie;
        this.Kino = Kino;
        this.Park = Park;
        this.Muzeum = Muzeum;
        this.Hrad = Hrad;
        this.niecoOstatne = niecoOstatne;
    }

    public String vymazSa() {
        Zrusena = true;
        return "Objednavka bola vymazana";
    }


    public String getMesto() {
        return Mesto;
    }

    public Boolean getKostol() {
        return Kostol;
    }

    public Boolean getRestauracie() {
        return Restauracie;
    }

    public Boolean getKino() {
        return Kino;
    }

    public Boolean getPark() {
        return Park;
    }

    public Boolean getMuzeum() {
        return Muzeum;
    }

    public Boolean getHrad() {
        return Hrad;
    }

    public String getNiecoOstatne() {
        return niecoOstatne;
    }
}

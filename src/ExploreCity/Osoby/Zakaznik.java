package ExploreCity.Osoby;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ExploreCity.Objednavky.Objednavka;

public class Zakaznik extends Osoba {
    private List<Objednavka> objednavky;

    public Zakaznik(String Meno, String Heslo, int ID) {
        super(Meno, Heslo, ID);
        this.Meno = Meno;
        this.Heslo = Heslo;
        this.ID = ID;
        this.objednavky = new ArrayList<>();
    }

    public void pridajObjednavku(Objednavka objednavka) {
        this.objednavky.add(objednavka);
    }
    
    public void vymazObjednavku(Objednavka objednavka) {
        this.objednavky.remove(objednavka);
    }

    public String getMeno() {
        return Meno;
    }

    public String getHeslo() {
        return Heslo;
    }

    public void odstranUcet() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Kozel\\Desktop\\Fiit\\2semester part 2\\OOP\\Projekt\\src\\users.txt"));
            String line;
            String input = "";
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                int id = Integer.parseInt(parts[1]);
                if (id != ID) {
                    input += line + System.lineSeparator();
                }
            }
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Kozel\\Desktop\\Fiit\\2semester part 2\\OOP\\Projekt\\src\\users.txt"));
            writer.write(input);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void odtranObjednavky(){
        
    }

    public int getID() {
        return ID;
    }
}


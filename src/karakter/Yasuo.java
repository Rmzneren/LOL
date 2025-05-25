package karakter; 

import java.util.ArrayList; 
import java.util.List;

public class Yasuo extends Karakter { 

    public Yasuo() { 
        super("Yasuo", 100, 20); 
    }

    @Override
    public List<String> skill(Karakter hedef) { 
        List<String> logs = new ArrayList<>(); 
        int hasar = saldiriGucu + 5; 
        String line1 = "\n🌪️ " + isim + " skill kullandı: 'Rüzgar Darbesi'! → " + hasar; 
        logs.add(line1); 
        System.out.println(line1);
        logs.addAll(hedef.hasarAl(hasar));
        return logs; 											
    }

    @Override 
    public List<String> ulti(Karakter hedef) { 
        List<String> logs = new ArrayList<>();

        if (ultiKullanildi) {
            String warn = "\n⚠️ " + isim + " ultisini zaten kullandı!"; 
            logs.add(warn);
            System.out.println(warn); 
            return logs; 
        }

        int hasar = saldiriGucu * 2; 
        String line1 = "\n⚔️ " + isim + " ULTI kullandı: 'Son Nefes'! Kritik hasar: " + hasar; 
        logs.add(line1); 
        System.out.println(line1); /
        logs.addAll(hedef.hasarAl(hasar)); 
        ultiKullanildi = true; 
        return logs; 
    }
}

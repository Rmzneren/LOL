package karakter; 

import java.util.ArrayList;
import java.util.List; 

public abstract class Karakter { 

   
    protected String isim; 
    protected int can; 
    protected int saldiriGucu; 
    protected boolean ultiKullanildi = false; 

   
    public Karakter(String isim, int can, int saldiriGucu) { 
        this.isim = isim; 
        this.can = can; 
        this.saldiriGucu = saldiriGucu;
    }


    public List<String> normalSaldiri(Karakter hedef) {
        List<String> logs = new ArrayList<>();
        String line1 = String.format("🗡️ %s normal saldırı yaptı → %s %d hasar aldı.",
            isim, hedef.getIsim(), saldiriGucu);
        logs.add(line1); 
        System.out.println("\n" + line1); 
        logs.addAll(hedef.hasarAl(saldiriGucu)); 
        return logs;
    }

  
    public List<String> hasarAl(int miktar) { 
        List<String> logs = new ArrayList<>(); 
        if (bloklandiMi()) { 
            String blk = String.format("🛡️ %s saldırıyı BLOKLADI! Hiç hasar almadı.", isim);
            logs.add(blk); 
            System.out.println(blk); 
            return logs; 
        }

        can -= miktar; 
        if (can < 0) can = 0;
        String line = String.format("🩸 %s kalan can: %d", isim, can);
        logs.add(line); 
        System.out.println(line);
        return logs; 
    }

 
    public List<String> sifaVer(int miktar) { 
        List<String> logs = new ArrayList<>(); 
        can += miktar; 
        String line = String.format("💊 %s şifa kullandı. +%d can! Yeni can: %d",
            isim, miktar, can);
        logs.add(line); 
        System.out.println(line);
        return logs; 
    }

 
    public boolean hayattaMi() { 
        return can > 0; 
    }

    
    public boolean isUltiKullanildi() {
        return ultiKullanildi;
    }

    public void setUltiKullanildi(boolean kullanildi) { 
        this.ultiKullanildi = kullanildi;
    }

    
    public String getIsim() { 
        return isim;
    }

    public int getCan() { 
        return can;
    }

    
    public boolean bloklandiMi() {
        double sans = Math.random(); 
        return sans <= 0.2; 
    }

    
    public abstract List<String> skill(Karakter hedef); 

    public abstract List<String> ulti(Karakter hedef); 
}

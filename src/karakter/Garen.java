
package karakter;

import java.util.ArrayList;
import java.util.List;

public class Garen extends Karakter {

    public Garen() {
        super("Garen", 120, 15);
    }

    @Override
    public List<String> skill(Karakter hedef) {
        List<String> logs = new ArrayList<>();
        int hasar = saldiriGucu + 3;
        String line1 = "\n🌀 " + isim + " skill kullandı: 'Dönerek Saldırı'! → " + hasar;
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
        int hasar = saldiriGucu + 5;
        String line1 = "\n⚖️ " + isim + " ULTI kullandı: 'Adaletin Yumruğu'! → " + hasar;
        logs.add(line1);
        System.out.println(line1);
        logs.addAll(hedef.hasarAl(hasar));
        String line2 = "\n💊 " + isim + " sifa kullandı: +10 can";
        logs.add(line2);
        System.out.println(line2);
        logs.addAll(this.sifaVer(10));
        ultiKullanildi = true;
        return logs;
    }
}

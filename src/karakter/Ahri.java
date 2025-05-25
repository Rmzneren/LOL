
package karakter;

import java.util.ArrayList;
import java.util.List;

public class Ahri extends Karakter {

    public Ahri() {
        super("Ahri", 90, 18);
    }

    @Override
    public List<String> skill(Karakter hedef) {
        List<String> logs = new ArrayList<>();
        int hasar = 15;
        String line1 = "\n🔥 " + isim + " skill kullandı: 'Tilki Ateşi'! → " + hasar;
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
        int hasar = saldiriGucu + 10;
        String line1 = "\n🔮 " + isim + " ULTI kullandı: 'Büyü Küresi'! → " + hasar;
        logs.add(line1);
        System.out.println(line1);
        logs.addAll(hedef.hasarAl(hasar));
        ultiKullanildi = true;
        return logs;
    }
}

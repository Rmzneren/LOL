
package karakter;

import java.util.ArrayList;
import java.util.List;

public class Lux extends Karakter {

    public Lux() {
        super("Lux", 85, 22);
    }

    @Override
    public List<String> skill(Karakter hedef) {
        List<String> logs = new ArrayList<>();
        int hasar = 20;
        String line1 = "\n💥 " + isim + " skill kullandı: 'Işık Patlaması'! → " + hasar;
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
        int hasar = saldiriGucu + 15;
        String line1 = "\n🌈 " + isim + " ULTI kullandı: 'Işığın Hükmü'! → " + hasar;
        logs.add(line1);
        System.out.println(line1);
        logs.addAll(hedef.hasarAl(hasar));
        ultiKullanildi = true;
        return logs;
    }
}

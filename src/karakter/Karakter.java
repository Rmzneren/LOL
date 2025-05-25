package karakter; // Bu sınıfın "karakter" adındaki bir paketin parçası olduğunu belirtir.

import java.util.ArrayList;
import java.util.List; // Listelerle çalışmak için gerekli kütüphaneler.

public abstract class Karakter { // Karakter sınıfı başka sınıflar tarafından miras alınmak üzere tasarlanmıştır.

    // === KARAKTER NİTELİKLERİ ===
    protected String isim; // Karakterin adı. Protected: alt sınıflar erişebilir, dışarıdan erişilemez.
    protected int can; // Karakterin mevcut can (sağlık) puanı.
    protected int saldiriGucu; // Karakterin normal saldırılarda verdiği hasar gücü.
    protected boolean ultiKullanildi = false; // Ulti yeteneğinin kullanılıp kullanılmadığını belirten bayrak. Varsayılan olarak false.

    // === YAPICI METOT ===
    public Karakter(String isim, int can, int saldiriGucu) { // Sınıfın yapıcı metodu. Karakter oluşturulurken bu çağrılır.
        this.isim = isim; // Parametre olarak gelen ismi nesnenin ismine atar.
        this.can = can; // Parametre olarak gelen can değerini nesnenin can'ına atar.
        this.saldiriGucu = saldiriGucu; // Saldırı gücü atanır.
    }

    // === NORMAL SALDIRI METODU ===
    public List<String> normalSaldiri(Karakter hedef) { // Hedef karaktere normal saldırı yapar. Açıklama mesajları döner.
        List<String> logs = new ArrayList<>(); // Olay mesajlarını tutacak boş bir liste oluşturulur.
        String line1 = String.format("🗡️ %s normal saldırı yaptı → %s %d hasar aldı.",
            isim, hedef.getIsim(), saldiriGucu); // Saldırı bilgisi formatlı şekilde yazılır.
        logs.add(line1); // Mesaj listeye eklenir.
        System.out.println("\n" + line1); // Konsola yazdırılır.
        logs.addAll(hedef.hasarAl(saldiriGucu)); // Hedefin canı azaltılır. Geri dönen mesajlar listeye eklenir.
        return logs; // Olay mesajları döndürülür.
    }

    // === HASAR ALMA METODU ===
    public List<String> hasarAl(int miktar) { // Karakter belirtilen miktarda hasar alır.
        List<String> logs = new ArrayList<>(); // Olay mesajlarını tutacak liste oluşturulur.
        if (bloklandiMi()) { // Eğer saldırı bloklandıysa:
            String blk = String.format("🛡️ %s saldırıyı BLOKLADI! Hiç hasar almadı.", isim); // Blok mesajı hazırlanır.
            logs.add(blk); // Listeye eklenir.
            System.out.println(blk); // Konsola yazdırılır.
            return logs; // Liste döndürülür.
        }

        can -= miktar; // Can miktarı, alınan hasar kadar azaltılır.
        if (can < 0) can = 0; // Can değeri sıfırın altına düşemez, sıfır yapılır.
        String line = String.format("🩸 %s kalan can: %d", isim, can); // Güncel can bilgisiyle mesaj oluşturulur.
        logs.add(line); // Listeye eklenir.
        System.out.println(line); // Konsola yazdırılır.
        return logs; // Olay mesajları döndürülür.
    }

    // === ŞİFA (CAN ARTTIRMA) METODU ===
    public List<String> sifaVer(int miktar) { // Karakterin canı belirli miktarda artırılır.
        List<String> logs = new ArrayList<>(); // Olay mesajlarını tutacak liste oluşturulur.
        can += miktar; // Can değeri belirtilen miktar kadar artırılır.
        String line = String.format("💊 %s şifa kullandı. +%d can! Yeni can: %d",
            isim, miktar, can); // Şifa mesajı hazırlanır.
        logs.add(line); // Listeye eklenir.
        System.out.println(line); // Konsola yazdırılır.
        return logs; // Mesaj listesi döndürülür.
    }

    // === HAYATTA MI KONTROLÜ ===
    public boolean hayattaMi() { // Karakterin hayatta olup olmadığını kontrol eder.
        return can > 0; // Can 0'dan büyükse true döner, aksi halde false.
    }

    // === ULTI KULLANILDI MI? ===
    public boolean isUltiKullanildi() { // Ulti kullanılıp kullanılmadığını kontrol eden getter metodu.
        return ultiKullanildi;
    }

    public void setUltiKullanildi(boolean kullanildi) { // Ulti kullanım durumunu ayarlayan setter metodu.
        this.ultiKullanildi = kullanildi;
    }

    // === GETTER METOTLAR ===
    public String getIsim() { // Karakterin ismini döner.
        return isim;
    }

    public int getCan() { // Karakterin mevcut can değerini döner.
        return can;
    }

    // === BLOKLAMA KONTROLÜ ===
    public boolean bloklandiMi() { // Saldırının bloklanıp bloklanmadığını %20 ihtimalle belirler.
        double sans = Math.random(); // 0.0 ile 1.0 arasında rastgele sayı üretilir.
        return sans <= 0.2; // %20 ihtimalle true döner.
    }

    // === ABSTRACT (SOYUT) METOTLAR ===
    public abstract List<String> skill(Karakter hedef); // Alt sınıflarda override edilmesi gereken özel yetenek (skill) metodu.

    public abstract List<String> ulti(Karakter hedef); // Alt sınıflarda override edilmesi gereken ulti metodu.
}

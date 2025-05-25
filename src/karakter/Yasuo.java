package karakter; // Bu sınıf, 'karakter' adlı paketin içindedir.

import java.util.ArrayList; // Dinamik liste kullanımı için gerekli sınıf.
import java.util.List; // List arayüzü; ArrayList gibi sınıfların genel temsilcisidir.

public class Yasuo extends Karakter { // Yasuo sınıfı, Karakter soyut sınıfından kalıtım alır.

    public Yasuo() { // Yapıcı metot: Yeni bir Yasuo nesnesi oluşturulduğunda çalışır.
        super("Yasuo", 100, 20); // Üst sınıf Karakter'in constructor'ı çağrılır. İsim: Yasuo, Can: 100, Saldırı Gücü: 20
    }

    @Override // Üst sınıftaki soyut skill metodunu override eder.
    public List<String> skill(Karakter hedef) { // Yasuo'nun özel yeteneği (skill) kullanıldığında çalışır.
        List<String> logs = new ArrayList<>(); // Olay mesajlarını tutmak için liste oluşturulur.
        int hasar = saldiriGucu + 5; // Skill, normal saldırıdan 5 fazladır.
        String line1 = "\n🌪️ " + isim + " skill kullandı: 'Rüzgar Darbesi'! → " + hasar; // Skill açıklama mesajı.
        logs.add(line1); // Mesaj listeye eklenir.
        System.out.println(line1); // Mesaj konsola yazdırılır.
        logs.addAll(hedef.hasarAl(hasar)); // Hedef karakter hasar alır, oluşan mesajlar listeye eklenir.
        return logs; // Tüm açıklama mesajlarını içeren liste döndürülür.												
    }

    @Override // Üst sınıftaki soyut ulti metodunu override eder.
    public List<String> ulti(Karakter hedef) { // Yasuo'nun ulti (en güçlü yetenek) saldırısı.
        List<String> logs = new ArrayList<>(); // Olay mesajlarını tutmak için liste oluşturulur.

        if (ultiKullanildi) { // Eğer ulti daha önce kullanılmışsa tekrar kullanılamaz.
            String warn = "\n⚠️ " + isim + " ultisini zaten kullandı!"; // Uyarı mesajı hazırlanır.
            logs.add(warn); // Listeye eklenir.
            System.out.println(warn); // Konsola yazdırılır.
            return logs; // Ulti gerçekleşmeden sadece uyarı mesajları döndürülür.
        }

        int hasar = saldiriGucu * 2; // Ulti, saldırı gücünün 2 katı hasar verir.
        String line1 = "\n⚔️ " + isim + " ULTI kullandı: 'Son Nefes'! Kritik hasar: " + hasar; // Ulti açıklama mesajı.
        logs.add(line1); // Mesaj listeye eklenir.
        System.out.println(line1); // Konsola yazdırılır.
        logs.addAll(hedef.hasarAl(hasar)); // Hedef karakter hasar alır, mesajları log'a eklenir.
        ultiKullanildi = true; // Ulti kullanıldığı için tekrar kullanım engellenir.
        return logs; // Olay mesajlarını içeren liste geri döndürülür.
    }
}


package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import karakter.Karakter;
import karakter.Yasuo;
import karakter.Ahri;
import karakter.Garen;
import karakter.Lux;

public class GameUI extends JFrame {
  
    private static final int WIDTH = 950, HEIGHT = 700;
    private static final Color BG      = new Color(30, 32, 45);
    private static final Color FG      = new Color(230, 230, 235);
    private static final Color ACCENT  = new Color(95, 159, 220);
    private static final Color BAR_BACK = new Color(50, 52, 65);
    private static final Font  FONT_L  = new Font("Segoe UI", Font.BOLD, 30);
    private static final Font  FONT_M  = new Font("Segoe UI", Font.PLAIN, 18);
    private static final Font  FONT_S  = new Font("Consolas", Font.PLAIN, 14);

  
    private final List<Karakter> pool = new ArrayList<>();
    private Karakter player, opponent;
    private final Random rnd = new Random();

   
    private BufferedImage imgY, imgA, imgG, imgL;

 
    private CardLayout cards;
    private JPanel     root;

    private JList<ChampItem>            lstChamps;
    private DefaultListModel<ChampItem> listModel;
    private JButton                     btnStart;

    private JLabel       lblPlayerImg, lblOpponentImg;
    private JProgressBar barPlayer, barOpponent;
    private JTextArea    txtLog;
    private JButton      btnNormal, btnSkill, btnUlti, btnHeal;

    public GameUI() throws Exception {
        super("League Combat Arena");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        loadImages();
        initPool();
        initUI();

        setVisible(true);
    }

    private void loadImages() throws Exception {
        imgY = ImageIO.read(new File("resources/images/yasuo.png"));
        imgA = ImageIO.read(new File("resources/images/ahri.png"));
        imgG = ImageIO.read(new File("resources/images/garen.png"));
        imgL = ImageIO.read(new File("resources/images/lux.png"));
    }

    private void initPool() {
        pool.add(new Yasuo());
        pool.add(new Ahri());
        pool.add(new Garen());
        pool.add(new Lux());
    }

    private void initUI() {
        cards = new CardLayout();
        root  = new JPanel(cards);
        root.setBackground(BG);
        setContentPane(root);

        root.add(buildSelectionPanel(), "SELECT");
        root.add(buildBattlePanel(),    "BATTLE");
        cards.show(root, "SELECT");
    }

    private JPanel buildSelectionPanel() {
        JPanel p = new JPanel(new BorderLayout(20,20));
        p.setBackground(BG);
        p.setBorder(new EmptyBorder(60,60,60,60));

        JLabel title = new JLabel("🌟 ŞAMPİYONUNUZU SEÇİN 🌟", SwingConstants.CENTER);
        title.setFont(FONT_L);
        title.setForeground(ACCENT);
        p.add(title, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        listModel.addElement(new ChampItem("Yasuo", imgY));
        listModel.addElement(new ChampItem("Ahri",  imgA));
        listModel.addElement(new ChampItem("Garen", imgG));
        listModel.addElement(new ChampItem("Lux",   imgL));

        lstChamps = new JList<>(listModel);
        lstChamps.setCellRenderer((list,value,index,sel,focus)-> {
            JPanel panel = new JPanel(new BorderLayout(10,10));
            panel.setBackground(sel ? ACCENT.darker() : BG);
            JLabel icon = new JLabel(new ImageIcon(value.img.getScaledInstance(60,60,Image.SCALE_SMOOTH)));
            JLabel name = new JLabel(value.name);
            name.setFont(FONT_M); name.setForeground(FG);
            panel.add(icon, BorderLayout.WEST);
            panel.add(name, BorderLayout.CENTER);
            return panel;
        });
        lstChamps.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstChamps.setBackground(BG.darker());
        lstChamps.setFixedCellHeight(80);
        p.add(new JScrollPane(lstChamps), BorderLayout.CENTER);

        btnStart = new JButton("BAŞLA");
        btnStart.setFont(FONT_M);
        btnStart.setBackground(ACCENT);
        btnStart.setForeground(FG);
        btnStart.setFocusPainted(false);
        btnStart.addActionListener(e-> startBattle());
        p.add(btnStart, BorderLayout.SOUTH);

        return p;
    }

    private JPanel buildBattlePanel() {
        JPanel p = new JPanel(new BorderLayout(10,10));
        p.setBackground(BG);
        p.setBorder(new EmptyBorder(10,10,10,10));

        JPanel top = new JPanel(new GridLayout(2,2,10,10));
        top.setBackground(BG);
        lblPlayerImg   = new JLabel("", SwingConstants.CENTER);
        lblOpponentImg = new JLabel("", SwingConstants.CENTER);
        barPlayer      = stylizedBar();
        barOpponent    = stylizedBar();
        top.add(lblPlayerImg);
        top.add(lblOpponentImg);
        top.add(barPlayer);
        top.add(barOpponent);
        p.add(top, BorderLayout.NORTH);

        txtLog = new JTextArea();
        txtLog.setFont(FONT_S);
        txtLog.setForeground(FG);
        txtLog.setBackground(BG.darker());
        txtLog.setEditable(false);
        JScrollPane sp = new JScrollPane(txtLog);
        sp.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ACCENT,2), "📜 Savaş Günlüğü"));
        p.add(sp, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER,15,10));
        actions.setBackground(BG);
        btnNormal = actionBtn("Normal");
        btnSkill  = actionBtn("Skill");
        btnUlti   = actionBtn("Ulti");
        btnHeal   = actionBtn("Şifa");
        btnNormal.addActionListener(e-> doAction(1));
        btnSkill .addActionListener(e-> doAction(2));
        btnUlti  .addActionListener(e-> doAction(3));
        btnHeal  .addActionListener(e-> doAction(4));
        actions.add(btnNormal);
        actions.add(btnSkill);
        actions.add(btnUlti);
        actions.add(btnHeal);
        p.add(actions, BorderLayout.SOUTH);

        return p;
    }

    private JProgressBar stylizedBar() {
        JProgressBar b = new JProgressBar(0,100);
        b.setStringPainted(true);
        b.setForeground(ACCENT);
        b.setBackground(BAR_BACK);
        b.setPreferredSize(new Dimension(200,25));
        return b;
    }

    private JButton actionBtn(String txt) {
        JButton b = new JButton(txt);
        b.setFont(FONT_M);
        b.setBackground(ACCENT.darker());
        b.setForeground(FG);
        b.setEnabled(false);
        b.setBorder(BorderFactory.createLineBorder(FG,1,true));
        return b;
    }

    private void startBattle() {
        int idx = lstChamps.getSelectedIndex();
        if (idx<0) return;
        ChampItem ci = listModel.get(idx);
        player = switch(ci.name) {
            case "Yasuo" -> new Yasuo();
            case "Ahri"  -> new Ahri();
            case "Garen" -> new Garen();
            default      -> new Lux();
        };
        pool.remove(player);
        pickOpponent();
        updateView("▶️ " + player.getIsim() + " vs " + opponent.getIsim() + " başladı!");
        enableActions();
        cards.show(root, "BATTLE");
    }

    private void pickOpponent() {
        if (pool.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "🎉 Tüm rakipler yenildi!", "Oyun Bitti",
                JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        opponent = pool.get(rnd.nextInt(pool.size()));
    }

    private void doAction(int type) {
   
        if (!player.hayattaMi()) {
            JOptionPane.showMessageDialog(
                this,
                player.getIsim() + " öldü. Oyun bitti!",
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE
            );
            System.exit(0);
        }

     
        List<String> logs = switch(type) {
            case 1 -> player.normalSaldiri(opponent);
            case 2 -> player.skill(opponent);
            case 3 -> player.ulti(opponent);
            default -> player.sifaVer(20);
        };
        logs.forEach(l-> txtLog.append(l+"\n"));

     
        if (!opponent.hayattaMi()) {
            pool.remove(opponent);
            txtLog.append("🏆 " + opponent.getIsim() + " yenildi!\n");
            showVictory(player);

            if (pool.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "🎉 Tüm rakipler yenildi! Oyunu kazandınız.",
                    "Tamamlandı",
                    JOptionPane.INFORMATION_MESSAGE
                );
                System.exit(0);
            }

         
            String[] options = pool.stream()
                                    .map(Karakter::getIsim)
                                    .toArray(String[]::new);
            String choice = (String) JOptionPane.showInputDialog(
                this,
                "Yeni rakip seçin:",
                "Rakip Seçimi",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
            );
            opponent = pool.stream()
                           .filter(k->k.getIsim().equals(choice))
                           .findFirst().orElseThrow();
            txtLog.append("➡️ Yeni rakip: " + opponent.getIsim() + "\n");
        } else {
            
            List<String> rlogs = opponent.normalSaldiri(player);
            rlogs.forEach(l-> txtLog.append(l+"\n"));
        }

       
        updateBarsPortraits();

     
        if (!player.hayattaMi()) {
            disableActions();
        }
    }

    private void respawnPlayer() {
        
    }

    private void showVictory(Karakter w) {
        String msg = switch(w.getIsim()) {
            case "Yasuo" -> "HASEKİİ!";
            case "Garen" -> "DEMACIA!";
            case "Lux"   -> "Işığın gücü!";
            case "Ahri"  -> "Tilki ateşi!";
            default      -> "Zafer!";
        };
        JOptionPane.showMessageDialog(this,
            w.getIsim() + " kazandı: " + msg,
            "Zafer",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void updateView(String init) {
        if (init!=null) txtLog.setText(init+"\n");
        updateBarsPortraits();
    }

    private void updateBarsPortraits() {
        lblPlayerImg.setIcon(new ImageIcon(
            imgFor(player).getScaledInstance(160,160,Image.SCALE_SMOOTH)));
        lblOpponentImg.setIcon(new ImageIcon(
            imgFor(opponent).getScaledInstance(160,160,Image.SCALE_SMOOTH)));
        barPlayer.setValue(player.getCan());
        barOpponent.setValue(opponent.getCan());
    }

    private Image imgFor(Karakter k) {
        return switch(k.getIsim()) {
            case "Yasuo" -> imgY;
            case "Ahri"  -> imgA;
            case "Garen" -> imgG;
            case "Lux"   -> imgL;
            default      -> imgY;
        };
    }

    private void enableActions() {
        btnNormal.setEnabled(true);
        btnSkill .setEnabled(true);
        btnUlti  .setEnabled(true);
        btnHeal  .setEnabled(true);
    }

    private void disableActions() {
        btnNormal.setEnabled(false);
        btnSkill .setEnabled(false);
        btnUlti  .setEnabled(false);
        btnHeal  .setEnabled(false);
    }

    private static class ChampItem {
        String name; BufferedImage img;
        ChampItem(String n, BufferedImage i){ name=n; img=i; }
        @Override public String toString(){ return name; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { new GameUI(); }
            catch(Exception e){ e.printStackTrace(); }
        });
    }
}

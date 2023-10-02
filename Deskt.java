// public class HelloWorld {
//     public static void main(String[] args) {
//         System.out.println("Hello, world !");
//     }
// }

// import java.util.Random;

// public class Hello {
//     public static void main(String[] args) {

//         int number = 1;
//         String text = "texte";
//         Random random = new Random();
//         int nb;
//         nb = random.nextInt(9);
//         nb+=1;
        
//         do {

//             number++;

//             if(number%2 == 0){
//                 System.out.println("ok");
//             }else{
//                 System.out.println("Ko");
//             }

//         } while (number <= 100); 
//     }
// }

// import javax.swing.*;
// import java.awt.*;


// public class Deskt {

//     public static void main(String[] args) {
//         JFrame fenetre = new JFrame("Mon Application de Bureau");

//         ImageIcon imageIcon = new ImageIcon("logo.png");
//         JLabel imageLabel = new JLabel(imageIcon);

//         // Crée un JLabel avec un texte formaté
//         JLabel etiquette = new JLabel("<html><font color='red' size='5'><b>Hello, World!</b></font></html>");

//         JPanel panel = new JPanel();
//         panel.setLayout(new FlowLayout());

//         panel.add(etiquette);
//         panel.add(imageLabel);

//         fenetre.add(panel);

//         fenetre.setSize(400, 300);
//         fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         fenetre.setVisible(true);
//     }
// }

// import javax.swing.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class Deskt {
//     public static void main(String[] args) {
//         String[] optionsToChoose = {"1. Blanc", "2. Noir", "3. Rouge", "4. Bleu", "Autre couleur"};

//         JFrame jFrame = new JFrame("Menu des couleurs");

//         JComboBox<String> jComboBox = new JComboBox<>(optionsToChoose);
//         jComboBox.setBounds(80, 50, 140, 20);

//         JButton jButton = new JButton("Done");
//         jButton.setBounds(100, 100, 90, 20);

//         JLabel jLabel = new JLabel();
//         jLabel.setBounds(90, 100, 400, 100);

//         jFrame.add(jButton);
//         jFrame.add(jComboBox);
//         jFrame.add(jLabel);
        
//         jFrame.setLayout(null);
//         jFrame.setSize(350, 250);
//         jFrame.setVisible(true);

//         jButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 String selectedFruit = "You selected " + jComboBox.getItemAt(jComboBox.getSelectedIndex());
//                 jLabel.setText(selectedFruit);
//             }
//         });

//     }
// }

import javax.swing.*;

class Deskt
{
  JMenu menu, smenu;
  JMenuItem e1, e2, e3, e4, e5, e6;
  
  Deskt()
  {
    // Créer le frame
    JFrame frame = new JFrame("Exemple Menu");
    // Créer la barre de menu
    JMenuBar menubar = new JMenuBar();
    // Créer le menu
    menu = new JMenu("Menu");
    // Créer le sous menu
    smenu = new JMenu("Sous Menu");
    // Créer les éléments du menu et sous menu
    e1 = new JMenuItem("Element 1");
    e2 = new JMenuItem("Element 2");
    e3 = new JMenuItem("Element 3");
    e4 = new JMenuItem("Element 4");
    e5 = new JMenuItem("Element 5");
    e6 = new JMenuItem("Element 6");
    // Ajouter les éléments au menu
    menu.add(e1); 
    menu.add(e2); 
    menu.add(e3);
    // Ajouter les éléments au sous menu
    smenu.add(e4); 
    smenu.add(e5);
    smenu.add(e6);
    // Ajouter le sous menu au menu principale
    menu.add(smenu);
    // Ajouter le menu au barre de menu
    menubar.add(menu);
    // Ajouter la barre de menu au frame
    frame.setJMenuBar(menubar);
    frame.setSize(300,300);
    frame.setLayout(null);
    frame.setVisible(true);
  }
  public static void main(String args[])
  {
    new Deskt();
  }
}

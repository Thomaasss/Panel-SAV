import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.JTableHeader;
import javax.swing.ImageIcon;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.sql.*;

public class InsertClientSavProf extends JFrame {
    private JTextField nomField, prenomField, telephoneField, numeroTicketField;
    private JTextArea reclamationArea;
    private DefaultTableModel tableModel;
    private JTextField dateField;
    private JComboBox<String> etatTicketComboBox;
    private Connection conn; // Connexion à la base de données MySQL

    // Générez un numéro de ticket aléatoire
    Random random = new Random();

    private int genererNumeroTicketUnique() {
        int numeroTicket;
        do {
            numeroTicket = new Random().nextInt(1000) + 1;
        } while (ticketsTableContainsNumeroTicket(numeroTicket));

        return numeroTicket;
    }

    private boolean ticketsTableContainsNumeroTicket(int numeroTicket) {
        // Votre logique pour vérifier si le numéro de ticket existe déjà dans le tableau
        return false;
    }

    private JLabel createLogoLabel(int width, int height) {
        ImageIcon logoIcon = new ImageIcon("C:/Users/pavly/IdeaProjects/Application SAV/src/LogoMain.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        return logoLabel;
    }

    public InsertClientSavProf() {
        setTitle("Formulaire de demande d'assistance");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        try {
            //étape 1: charger la classe de driver
            Class.forName("com.mysql.jdbc.Driver");

            //étape 2: créer l'objet de connexion
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetsav?characterEncoding=UTF-8", "root", "");

            // Initialisez l'objet tableModel ici
            String[] columnNames = {"Nom", "Prénom", "Téléphone", "Réclamation", "Date", "Numéro de ticket", "État du ticket"};
            tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Rendre toutes les cellules du tableau non éditables
                    return false;
                }
            };

            //étape 3: créer l'objet statement
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM dossier_sav");

            //étape 4: exécuter la requête
            while (res.next()) {
                String nom = res.getString(1);
                String prenom = res.getString(2);
                String telephone = res.getString(3);
                String reclamation = res.getString(4);
                String date = res.getString(5);
                int numero_ticket = res.getInt(6);
                String etat_ticket = res.getString(7);
                // Ajouter les données au modèle de tableau

                tableModel.addRow(new Object[]{nom, prenom, telephone, reclamation, date, numero_ticket, etat_ticket});


                System.out.println(nom);

            }

            System.out.println("tes ok");

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("erreur");
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel formulairePanel = new JPanel(new BorderLayout(10, 10));
        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        JPanel tablePanel = new JPanel(new BorderLayout());

        add(formulairePanel);
        int logoWidth = 120;
        int logoHeight = 120;
        JLabel logoLabel = createLogoLabel(logoWidth, logoHeight);
        formulairePanel.add(logoLabel, BorderLayout.NORTH);

        inputPanel.add(new JLabel("Nom :"));
        nomField = new JTextField(20);
        inputPanel.add(nomField);

        inputPanel.add(new JLabel("Prénom :"));
        prenomField = new JTextField(20);
        inputPanel.add(prenomField);

        inputPanel.add(new JLabel("Téléphone :"));
        telephoneField = new JTextField(20);
        inputPanel.add(telephoneField);

        inputPanel.add(new JLabel("Réclamation :"));
        reclamationArea = new JTextArea(5, 20);
        reclamationArea.setWrapStyleWord(true);
        reclamationArea.setLineWrap(true);
        inputPanel.add(new JScrollPane(reclamationArea));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());
        inputPanel.add(new JLabel("Date d'aujourd'hui :"));
        dateField = new JTextField(currentDate);
        dateField.setEditable(false);
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Numéro de ticket :"));
        numeroTicketField = new JTextField(20);
        numeroTicketField.setEditable(false); // Le champ numéro de ticket ne sera pas éditable manuellement
        inputPanel.add(numeroTicketField);

        etatTicketComboBox = new JComboBox<>(new String[]{"Ouvert", "En cours", "Fermé"});
        inputPanel.add(new JLabel("État du ticket :"));
        inputPanel.add(etatTicketComboBox);

        JButton envoyerButton = new JButton("Envoyer");
        envoyerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String telephone = telephoneField.getText();
                String reclamation = reclamationArea.getText();
                String date = dateField.getText();
                int numeroTicket = genererNumeroTicketUnique(); // Générez le numéro de ticket aléatoire
                numeroTicketField.setText(String.valueOf(numeroTicket)); // Mettez à jour le champ numéro de ticket
                String etatTicket = etatTicketComboBox.getSelectedItem().toString();

                // Insérez les données dans la table de la base de données
                try {
                    String query = "INSERT INTO dossier_sav (nom, prenom, telephone, reclamation, date, numero_ticket, etat_ticket) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = conn.prepareStatement(query);
                    preparedStatement.setString(1, nom);
                    preparedStatement.setString(2, prenom);
                    preparedStatement.setString(3, telephone);
                    preparedStatement.setString(4, reclamation);
                    preparedStatement.setString(5, date);
                    preparedStatement.setInt(6, numeroTicket);
                    preparedStatement.setString(7, etatTicket);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'insertion des données : " + ex.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
                }

                // Ajoutez les données dans le modèle de la table Swing
                String[] rowData = {nom, prenom, telephone, reclamation, date, String.valueOf(numeroTicket), etatTicket};
                tableModel.addRow(rowData);
            }
        });
        inputPanel.add(envoyerButton);

        formulairePanel.add(inputPanel, BorderLayout.CENTER);

        String[] columnNames = {"Nom", "Prénom", "Téléphone", "Réclamation", "Date", "Numéro de ticket", "État du ticket"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Rendre toutes les cellules du tableau non éditables
                return false;
            }
        };
        JTable tableau = new JTable(tableModel);

        JTableHeader header = tableau.getTableHeader();
        tablePanel.add(header, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(tableau);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        formulairePanel.add(tablePanel, BorderLayout.SOUTH);

        add(formulairePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Ajoutez un bouton "Supprimer" à votre inputPanel
        JButton supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableau.getSelectedRow();
                if (selectedRow != -1) { // Vérifiez si une ligne est sélectionnée
                    // Supprimez la ligne de la table Swing
                    tableModel.removeRow(selectedRow);

                    // Supprimez la ligne correspondante de la base de données (vous devrez implémenter cette partie)
                    // Assurez-vous d'ajuster la logique de suppression en fonction de votre propre schéma de base de données
                    int numeroTicket = Integer.parseInt(tableModel.getValueAt(selectedRow, 5).toString());
                    supprimerLigneDeLaBaseDeDonnees(numeroTicket);
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner une ligne à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        inputPanel.add(supprimerButton);

        // Fermez la connexion à la base de données lors de la fermeture de l'application
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    // Méthode pour supprimer une ligne de la base de données
    private void supprimerLigneDeLaBaseDeDonnees(int numeroTicket) {
        // Implémentez la logique de suppression de la ligne correspondante dans votre base de données ici
        // Utilisez le numéro de ticket pour identifier la ligne à supprimer
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InsertClientSavProf();
            }
        });
    }
}

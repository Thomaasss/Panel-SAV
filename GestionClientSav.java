import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

public class GestionClientSav extends JFrame {
    private JTextField nomField, prenomField, telephoneField;
    private JTextArea reclamationArea;
    private DefaultTableModel tableModel;
    private JComboBox<String> typeReclamationComboBox;
    private int ticketCounter = 0;
    private int stateCounter = 1;

    public GestionClientSav() {
        setTitle("Formulaire de demande d'assistance");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        ImageIcon logoIcon = new ImageIcon("C:\\wamp64\\www\\Java\\logo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setPreferredSize(new Dimension(100, 225));

        mainPanel.add(logoLabel, BorderLayout.NORTH);

        JPanel formulairePanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Label et champ pour le nom
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        formulairePanel.add(new JLabel("Nom :"), constraints);

        constraints.gridx = 1;
        nomField = new JTextField(20);
        formulairePanel.add(nomField, constraints);

        // Label et champ pour le prénom
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        formulairePanel.add(new JLabel("Prénom :"), constraints);

        constraints.gridx = 1;
        prenomField = new JTextField(20);
        formulairePanel.add(prenomField, constraints);

        // Label et champ pour le téléphone
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        formulairePanel.add(new JLabel("Téléphone :"), constraints);

        constraints.gridx = 1;
        telephoneField = new JTextField(20);
        formulairePanel.add(telephoneField, constraints);

        // Label et champ pour la réclamation
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        formulairePanel.add(new JLabel("Réclamation :"), constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 2;
        reclamationArea = new JTextArea(5, 20);
        reclamationArea.setWrapStyleWord(true);
        reclamationArea.setLineWrap(true);
        formulairePanel.add(new JScrollPane(reclamationArea), constraints);

        // Ajout du menu déroulant pour le type de réclamation
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        formulairePanel.add(new JLabel("Type/Raison :"), constraints);

        constraints.gridx = 1;
        String[] typesReclamation = {"Produit défectueux", "Livraison tardive", "Service client médiocre", "Facturation incorrecte", "Autre"};
        typeReclamationComboBox = new JComboBox<>(typesReclamation);
        formulairePanel.add(typeReclamationComboBox, constraints);

        // Bouton Envoyer
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 3;
        JButton envoyerButton = new JButton("Envoyer");
        envoyerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticketCounter++; // Incrémente le compteur de numéro de ticket
                stateCounter++;  // Incrémente le compteur d'état

                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String telephone = telephoneField.getText();
                String reclamation = reclamationArea.getText();
                String typeReclamation = (String) typeReclamationComboBox.getSelectedItem();

                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dateFormatted = dateFormat.format(date);

                // Ajouter les données au modèle de tableau, y compris le numéro de ticket et l'état (toujours "Nouveau")
                String[] rowData = {Integer.toString(ticketCounter), nom, prenom, telephone, reclamation, typeReclamation, dateFormatted, "Nouveau"};
                tableModel.addRow(rowData);
            }
        });
        formulairePanel.add(envoyerButton, constraints);

        // Créer le modèle de tableau
        String[] columnNames = {"Numéro de Ticket", "Nom", "Prénom", "Téléphone", "Réclamation", "Type de Réclamation", "Date", "État"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Créer le tableau
        JTable tableau = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableau);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        formulairePanel.add(scrollPane, constraints);

        mainPanel.add(formulairePanel, BorderLayout.CENTER);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionClientSav();
            }
        });
    }
}

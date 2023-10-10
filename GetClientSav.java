import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetClientSav extends JFrame {
    // Déclarez les champs en dehors du constructeur pour y accéder plus tard
    private JTextField nameField;
    private JTextField firstNameField;
    private JTextField phoneField;
    private JTextField complaintField;
    private JComboBox<String> typeComplaintComboBox;
    private DefaultTableModel tableModel; // Déclaration du modèle de tableau en tant que champ

    public GetClientSav() {
        setTitle("Gestion service après-vente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Charger le logo depuis le chemin spécifié
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\userinsta\\OneDrive - INSTA\\Documents\\Java\\logo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setPreferredSize(new Dimension(100, 225));

        // Créer un JPanel pour contenir le logo et le inputPanel
        JPanel logoInputPanel = new JPanel();
        logoInputPanel.setLayout(new BorderLayout());
        logoInputPanel.add(logoLabel, BorderLayout.NORTH);

        // Créer un JPanel avec un GridBagLayout pour organiser les composants
        JPanel inputPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5); // Ajouter des marges

        // Champ "Nom"
        JLabel nameLabel = new JLabel("Nom:");
        inputPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        inputPanel.add(nameField, gbc);

        // Champ "Prénom"
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel firstNameLabel = new JLabel("Prénom:");
        inputPanel.add(firstNameLabel, gbc);

        gbc.gridx = 1;
        firstNameField = new JTextField(20);
        inputPanel.add(firstNameField, gbc);

        // Champ "Téléphone"
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel phoneLabel = new JLabel("Téléphone:");
        inputPanel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        phoneField = new JTextField(20);
        inputPanel.add(phoneField, gbc);

        // Champ "Réclamation"
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel complaintLabel = new JLabel("Réclamation:");
        inputPanel.add(complaintLabel, gbc);

        gbc.gridx = 1;
        complaintField = new JTextField(20);
        inputPanel.add(complaintField, gbc);

        // Champ "Type de Réclamation" (menu déroulant)
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel typeComplaintLabel = new JLabel("Type de Réclamation:");
        inputPanel.add(typeComplaintLabel, gbc);

        gbc.gridx = 1;
        String[] complaintTypes = {"Produit défectueux", "Livraison tardive", "Service client médiocre", "Facturation incorrecte", "Autre"};
        typeComplaintComboBox = new JComboBox<>(complaintTypes);
        inputPanel.add(typeComplaintComboBox, gbc);

        // Créer un bouton "Envoyer"
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Envoyer");
        inputPanel.add(addButton, gbc);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer les données saisies par l'utilisateur
                String nom = nameField.getText();
                String prenom = firstNameField.getText();
                String telephone = phoneField.getText();
                String reclamation = complaintField.getText();
                String typeReclamation = (String) typeComplaintComboBox.getSelectedItem(); // Obtenir le type de réclamation depuis le JComboBox

                // Insérer les données dans la base de données
                insertDataIntoDatabase(nom, prenom, telephone, reclamation, typeReclamation);

                // Rafraîchir les données du tableau après l'ajout
                tableModel.setRowCount(0); // Effacer toutes les lignes actuelles
                refreshTableData();

                // Effacer les champs après l'ajout
                nameField.setText("");
                firstNameField.setText("");
                phoneField.setText("");
                complaintField.setText("");
            }
        });

        // Ajouter le JPanel avec les champs au-dessus du titre
        mainPanel.add(logoInputPanel);
        logoInputPanel.add(inputPanel, BorderLayout.CENTER);

        // ... (Code précédent pour les éléments existants)

        // Créer le modèle de tableau
        String[] columnNames = {"Numéro de Ticket", "Nom", "Prénom", "Téléphone", "Réclamation", "Type de Réclamation", "Date", "État"};
        tableModel = new DefaultTableModel(columnNames, 0); // Initialisation du modèle de tableau

        // Créer le tableau
        JTable tableau = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableau);

        mainPanel.add(scrollPane);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Rafraîchir les données du tableau au démarrage
        refreshTableData();
    }

    private void refreshTableData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/dev";
            String user = "root";
            String password = "";

            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "SELECT * FROM sav";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String ticketNumber = resultSet.getString("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String telephone = resultSet.getString("telephone");
                String reclamation = resultSet.getString("reclamation");
                String typeReclamation = resultSet.getString("type_reclamation");
                String dateFormatted = resultSet.getString("date");
                String etat = resultSet.getString("etat");

                String[] rowData = {ticketNumber, nom, prenom, telephone, reclamation, typeReclamation, dateFormatted, etat};
                tableModel.addRow(rowData);
            }
            System.out.println("Ok");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("erreur");
        }
    }

    private void insertDataIntoDatabase(String nom, String prenom, String telephone, String reclamation, String typeReclamation) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/dev";
            String user = "root";
            String password = "";

            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "INSERT INTO sav (nom, prenom, telephone, reclamation, type_reclamation, date, etat) VALUES (?, ?, ?, ?, ?, NOW(), 'Nouveau')";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, telephone);
            preparedStatement.setString(4, reclamation);
            preparedStatement.setString(5, typeReclamation);

            preparedStatement.executeUpdate();

            conn.close();
            System.out.println("Données insérées avec succès.");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Erreur lors de l'insertion des données.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GetClientSav();
            }
        });
    }
}

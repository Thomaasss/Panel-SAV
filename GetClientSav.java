import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetClientSav extends JFrame {
    public GetClientSav() {
        setTitle("Liste des Réclamations");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Créer le modèle de tableau
        String[] columnNames = {"Numéro de Ticket", "Nom", "Prénom", "Téléphone", "Réclamation", "Type de Réclamation", "Date", "État"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Créer le tableau
        JTable tableau = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableau);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Rafraîchir les données du tableau au démarrage
        refreshTableData(tableModel);
    }

    private void refreshTableData(DefaultTableModel tableModel) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/sav";
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
        } catch(Exception e){ 
            System.out.println(e);
            System.out.println("erreur");
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

package org.example.datatable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDao {
    private DataSource dataSource;

    public EtudiantDao(){
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/myDatabase");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public List<Etudiant> getAllEtudiants(){
        List<Etudiant> etudiants = new ArrayList<>();
        String sql = "SELECT * FROM etudiants";

        try(Connection con = dataSource.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()){
                etudiants.add(new Etudiant(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("age")
                ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return etudiants;
    }

    public void ajouter(Etudiant etudiant){
        String sql = "INSERT INTO etudiants (nom,prenom,age) VALUES (?,?,?)";

        try(Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1,etudiant.getNom());
                ps.setString(2,etudiant.getPrenom());
                ps.setInt(3,etudiant.getAge());
                ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void modifier(Etudiant etudiant){
        String sql = "UPDATE etudiants SET nom = ?, prenom = ?, age = ? WHERE id = ?";

        try(Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,etudiant.getNom());
            ps.setString(2,etudiant.getPrenom());
            ps.setInt(3,etudiant.getAge());
            ps.setInt(4,etudiant.getId());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void supprimer(int id){
        String sql = "DELETE FROM etudiants WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

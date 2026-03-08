package org.example.datatable;

import jakarta.faces.component.html.HtmlCommandButton;

import java.util.ArrayList;
import java.util.List;

public class EtudiantBean {
    private List<Etudiant> etudiants;
    private Etudiant nouvelEtudiant = new Etudiant();
    private Etudiant etudiantSelectionne = null;
    private HtmlCommandButton htmlCommandSubmit = new HtmlCommandButton();
    private HtmlCommandButton htmlCommandEdit = new HtmlCommandButton();
    private HtmlCommandButton htmlCommandDelete = new HtmlCommandButton();

    public EtudiantBean() {
        etudiants = new ArrayList<>();
        etudiants.add(new Etudiant("Dupont", "Jean", 20));
        etudiants.add(new Etudiant("Martin", "Claire", 22));
        etudiants.add(new Etudiant("Nguyen", "Thi", 21));
        etudiants.add(new Etudiant("Mekrane", "Bahae Eddine", 21));
        htmlCommandEdit.setDisabled(true);
        htmlCommandDelete.setDisabled(true);
    }

    public void ajouter(){
        etudiants.add(new Etudiant(nouvelEtudiant.getNom(),nouvelEtudiant.getPrenom(),nouvelEtudiant.getAge()));
        nouvelEtudiant = new Etudiant();
    }

    public void select(Etudiant e){
        etudiantSelectionne = e;
        nouvelEtudiant.setNom(e.getNom());
        nouvelEtudiant.setPrenom(e.getPrenom());
        nouvelEtudiant.setAge(e.getAge());
        htmlCommandSubmit.setDisabled(true);
        htmlCommandEdit.setDisabled(false);
        htmlCommandDelete.setDisabled(false);
    }

    public void modifier(){
        if(etudiantSelectionne != null){
            etudiantSelectionne.setNom(nouvelEtudiant.getNom());
            etudiantSelectionne.setPrenom(nouvelEtudiant.getPrenom());
            etudiantSelectionne.setAge(nouvelEtudiant.getAge());
            etudiantSelectionne = null;
            nouvelEtudiant = new Etudiant();
            htmlCommandSubmit.setDisabled(false);
        }
    }

    public void delete(){
        if(etudiantSelectionne != null){
            etudiants.remove(etudiantSelectionne);
            etudiantSelectionne  = null;
            nouvelEtudiant = new Etudiant();
            htmlCommandSubmit.setDisabled(false);
        }
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public Etudiant getNouvelEtudiant() {
        return nouvelEtudiant;
    }

    public void setNouvelEtudiant(Etudiant nouvelEtudiant) {
        this.nouvelEtudiant = nouvelEtudiant;
    }

    public Etudiant getEtudiantSelectionne() {
        return etudiantSelectionne;
    }

    public void setEtudiantSelectionne(Etudiant etudiantSelectionne) {
        this.etudiantSelectionne = etudiantSelectionne;
    }

    public HtmlCommandButton getHtmlCommandSubmit() {
        return htmlCommandSubmit;
    }

    public void setHtmlCommandSubmit(HtmlCommandButton htmlCommandSubmit) {
        this.htmlCommandSubmit = htmlCommandSubmit;
    }

    public HtmlCommandButton getHtmlCommandEdit() {
        return htmlCommandEdit;
    }

    public void setHtmlCommandEdit(HtmlCommandButton htmlCommandEdit) {
        this.htmlCommandEdit = htmlCommandEdit;
    }

    public HtmlCommandButton getHtmlCommandDelete() {
        return htmlCommandDelete;
    }

    public void setHtmlCommandDelete(HtmlCommandButton htmlCommandDelete) {
        this.htmlCommandDelete = htmlCommandDelete;
    }
}

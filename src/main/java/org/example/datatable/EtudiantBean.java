package org.example.datatable;

import jakarta.faces.component.html.HtmlCommandButton;

import java.util.ArrayList;
import java.util.List;

public class EtudiantBean {
    private EtudiantJpa dao = new EtudiantJpa();
    private Etudiant nouvelEtudiant = new Etudiant();
    private Etudiant etudiantSelectionne = null;
    private HtmlCommandButton htmlCommandSubmit = new HtmlCommandButton();
    private HtmlCommandButton htmlCommandEdit = new HtmlCommandButton();
    private HtmlCommandButton htmlCommandDelete = new HtmlCommandButton();

    public EtudiantBean() {
        htmlCommandEdit.setDisabled(true);
        htmlCommandDelete.setDisabled(true);
    }

    public List<Etudiant> getEtudiants(){
        return dao.getAllEtudiants();
    }

    public void ajouter(){
        dao.ajouter(nouvelEtudiant);
        nouvelEtudiant = new Etudiant();
    }

    public void select(Etudiant e){
        etudiantSelectionne = e;
        nouvelEtudiant.setId(e.getId());
        nouvelEtudiant.setNom(e.getNom());
        nouvelEtudiant.setPrenom(e.getPrenom());
        nouvelEtudiant.setAge(e.getAge());
        htmlCommandSubmit.setDisabled(true);
        htmlCommandEdit.setDisabled(false);
        htmlCommandDelete.setDisabled(false);
    }

    public void modifier(){
        if(etudiantSelectionne != null){
            dao.modifier(nouvelEtudiant);
            etudiantSelectionne = null;
            nouvelEtudiant = new Etudiant();
            htmlCommandSubmit.setDisabled(false);
            htmlCommandEdit.setDisabled(true);
            htmlCommandDelete.setDisabled(true);
        }
    }

    public void delete(){
        if(etudiantSelectionne != null){
            dao.supprimer(etudiantSelectionne.getId());
            etudiantSelectionne  = null;
            nouvelEtudiant = new Etudiant();
            htmlCommandSubmit.setDisabled(false);
            htmlCommandEdit.setDisabled(true);
            htmlCommandDelete.setDisabled(true);
        }
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

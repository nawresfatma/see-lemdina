package com.example.bassametproject;

public class ProgramClass {

    private int formateur;
    private String poste , prg , bio,timing;

    public ProgramClass(int formateur, String poste, String prg, String bio, String timing) {
        this.formateur = formateur;
        this.poste = poste;
        this.prg = prg;
        this.bio = bio;
        this.timing = timing;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public int getFormateur() {
        return formateur;
    }

    public String getPoste() {
        return poste;
    }

    public String getPrg() {
        return prg;
    }

    public String getBio() {
        return bio;
    }

    public void setFormateur(int formateur) {
        this.formateur = formateur;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public void setPrg(String prg) {
        this.prg = prg;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}



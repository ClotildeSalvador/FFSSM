/**
 * @(#) Plongee.java
 */
package FFSSM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Plongee {

	public Site lieu;

	public Moniteur chefDePalanquee;

	public LocalDate date;

	public int profondeur;

	public int duree;
	
	public ArrayList<Plongeur> listePlongeurs ;

	public Plongee(Site lieu, Moniteur chefDePalanquee, LocalDate date, int profondeur, int duree) {
		this.lieu = lieu;
		this.chefDePalanquee = chefDePalanquee;
		this.date = date;
		this.profondeur = profondeur;
		this.duree = duree;
		this.listePlongeurs=new ArrayList<Plongeur>() ;
	}

	public void ajouteParticipant(Plongeur participant) {
		this.listePlongeurs.add(participant) ;
	}

	public LocalDate getDate() {
		return date;
	}

	/**
	 * Détermine si la plongée est conforme. 
	 * Une plongée est conforme si tous les plongeurs de la palanquée ont une
	 * licence valide à la date de la plongée
	 * @return vrai si la plongée est conforme
	 */
	public boolean estConforme(){
		boolean r = true ;
                
                if (listePlongeurs.isEmpty()) {
                    r=false ;
                }
		for (Plongeur p : listePlongeurs) {
                    if (p.getListeLicences().isEmpty()) {
                        r=false;
                    } else {
                        LocalDate dateExpir ;
                        Licence c = (Licence) p.derniereLicence() ;
                        dateExpir = c.getDelivrance().plusYears(1);
                        if (dateExpir.isBefore(this.date)) {
                             r=false ;
                        }
                    }
		}
		return r ;
	}

}

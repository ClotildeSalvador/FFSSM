/**
 * @(#) Moniteur.java
 */
package FFSSM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Moniteur extends Plongeur {

    public int numeroDiplome;
    public List<Embauche> listeEmplois ;
    public Embauche embaucheActuelle ;

    public Moniteur(String numeroINSEE, String nom, String prenom, String adresse, String telephone, LocalDate naissance, int numeroDiplome) {
        super(numeroINSEE, nom, prenom, adresse, telephone, naissance);
        this.numeroDiplome = numeroDiplome;
        this.listeEmplois = new ArrayList<Embauche>() ;
    }

    /**
     * Si ce moniteur n'a pas d'embauche, ou si sa dernière embauche est terminée,
     * ce moniteur n'a pas d'employeur.
     * @return l'employeur actuel de ce moniteur sous la forme d'un Optional
     */
    public Optional<Club> employeurActuel() {
    	Club c = null ;
    	for (Embauche e : this.listeEmplois) {
    		if(e.getFin()==null) {
    			c=e.getEmployeur() ;
    		}
    	}
    	return Optional.ofNullable(c); 
    }
    
    /**
     * Enregistrer une nouvelle embauche pour cet employeur
     * @param employeur le club employeur
     * @param debutNouvelle la date de début de l'embauche
     */
    public void nouvelleEmbauche(Club employeur, LocalDate debutNouvelle) {   
    	Embauche e = new Embauche(debutNouvelle, this, employeur) ;
    	this.listeEmplois.add(e) ;    
    }

    public List<Embauche> emplois() {
    	return this.listeEmplois ;
    }

}

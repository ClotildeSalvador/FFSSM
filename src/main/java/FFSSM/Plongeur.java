package FFSSM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class Plongeur extends Personne {
	
	public ArrayList<Licence> listeLicences ;
	public Club club ; 

	public Plongeur(String numeroINSEE, String nom, String prenom, String adresse, String telephone,
			LocalDate naissance) {
		super(numeroINSEE, nom, prenom, adresse, telephone, naissance);
		// TODO Auto-generated constructor stub
		this.listeLicences=new ArrayList<>() ;
	}
	
	public void ajouteLicence(String numero, LocalDate delivrance) {
		Licence l = new Licence(this, numero, delivrance, this.club) ; 
		this.listeLicences.add(l) ;
	}
	
	public Licence derniereLicence(){
                Licence r = this.listeLicences.get(0) ;
                for (Licence l : listeLicences) {
			if (l.getDelivrance().isAfter(r.getDelivrance())) {
				r=l ;
			}
                }
                return r;
        }

    public ArrayList<Licence> getListeLicences() {
        return listeLicences;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    @Override
    public String toString() {
        return "Plongeur{" + this.getNom()+'}';
    }
        
        
	
	
}

package FFSSM;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FFSSMJUnitTest {

	Plongeur untel ; 
	Moniteur untel2 ;
	Club club ; 
	LocalDate dateN = LocalDate.of(1990, 1, 1); 
//	date d'aujourdhui
	LocalDate dateAu = LocalDate.of(2021, 1, 1);
	Plongee plongee ; 
	Site lieu ;
	
	@BeforeEach
	public void setUp() {
		lieu = new Site("ici","la bas") ;
		untel = new Plongeur("numeroINSEE", "plonguer", "prenom", "adresse", "telephone", dateN);
		untel2 = new Moniteur("numeroINSEE", "moniteur", "prenom", "adresse", "telephone", dateN, 001);
		club = new Club(untel2, "Club", "01tel") ;
                club.setAdresse("ici") ;
		plongee = new Plongee(lieu, untel2, dateAu, 12, 3) ;
                untel2.nouvelleEmbauche(club, dateAu);
                club.organisePlongee(plongee) ;
	}
//	CLASSE LICENCE
	@Test
	public void testEstValide() { 
//		licence non valide au 1 janv 2021
		LocalDate d = LocalDate.of(2000,01,01) ;
		untel.ajouteLicence("001", d);
		assertFalse(untel.derniereLicence().estValide(dateAu)) ;
//		licence valide
		LocalDate e = LocalDate.of(2020, 12, 12) ; 
		untel.ajouteLicence("002", e);
		assertTrue(untel.derniereLicence().estValide(dateAu)) ;
	}
	
        @Test
        public void testGetPossesseur() {
            LocalDate d = LocalDate.of(2000,01,01) ;
            untel.ajouteLicence("001", d);
            Licence l = untel.derniereLicence() ;
            assertEquals(l.getPossesseur(), untel, "le possesseur de la licence n'est pas le bon") ;
        }
        
        @Test
        public void testGetNumero() {
            LocalDate d = LocalDate.of(2000,01,01) ;
            untel.ajouteLicence("001", d);
            Licence l = untel.derniereLicence() ;
            assertEquals(l.getNumero(), "001", "le numero de licence n'est pas le bon") ;
        }
        
        public void testGetDelivrance() {
            LocalDate d = LocalDate.of(2000,01,01) ;
            untel.ajouteLicence("001", d);
            Licence l = untel.derniereLicence() ;
            assertEquals(l.getDelivrance(), d, "la date de délivrance n'est pas la bonne") ;
        }
        
        @Test
        public void testGetClub() {
            LocalDate d = LocalDate.of(2000,01,01) ;
            untel.ajouteLicence("001", d);
            Licence l = untel.derniereLicence() ;
            assertEquals(l.getClub(), untel.getClub(), "le possesseur n'est pas le bon") ;
        }
        
//        TEST PLONGEE
        
        	@Test
	public void testAjouterParticipant() {
		plongee.ajouteParticipant(untel);
                assertEquals( plongee.listePlongeurs.get(0), untel, "la liste des plongeurs est incorrecte") ;
	}
        
        @Test
        public void testGetDate() {
            assertEquals(plongee.getDate(), dateAu, "la date de la plongée n'est pas bonne") ;
        }
        
        @Test
        public void testEstConforme() {
//          liste vide -> pas conforme
            assertFalse(plongee.estConforme()) ;
//          un plongeur valide -> conforme
            LocalDate dateValide = LocalDate.of(2020, 12, 12) ;
            Plongeur oui = new Plongeur("numeroINSEE", "plonguer", "prenom", "adresse", "telephone", dateN) ;
            oui.ajouteLicence("003", dateValide);
            plongee.ajouteParticipant(oui);
            assertTrue(plongee.estConforme()) ;
//          on ajoute un participant sans licence -> pas conforme
            plongee.ajouteParticipant(untel) ;
            assertFalse(plongee.estConforme()) ;
//          on ajoute un participant avec une licence invalide -> pas conforme
//          dans une nouvelle plongee car l'autre est deja invalide
            Plongee plongee2 = new Plongee(lieu, untel2, dateAu, 12, 4) ;
            LocalDate dateInvalide = LocalDate.of(2000, 12, 12) ;
            Plongeur non = new Plongeur("numeroINSEE", "plongeur", "prenom", "adresse", "telephone", dateN) ;
            non.ajouteLicence("004", dateInvalide);
            plongee2.ajouteParticipant(non);
            assertFalse(plongee2.estConforme()) ;
        }
        
//        TEST MONITEUR
//        
        
        @Test
        public void testEmplois() {
            assertEquals(untel2.emplois(), untel2.listeEmplois, "la liste des emplois n'est pas correcte") ;
        }
        
        @Test
        public void testNouvelleEmbauche() {
            untel2.nouvelleEmbauche(club, LocalDate.of(2020, 12, 31));
            assertEquals(untel2.emplois(), untel2.listeEmplois);
        }
        
        @Test
        public void testEmployeurActuel() {
//          untel2 a un employeur
            assertEquals(untel2.employeurActuel(), Optional.ofNullable(club), "l'employeur n'est pas le bon") ;
//           untel2 fini son embauche -> null
            untel2.emplois().get(untel2.emplois().size()-1).terminer(dateAu.plusMonths(2));
            assertEquals(untel2.employeurActuel(), Optional.empty() , "l'employeur devrait etre null") ;
        }
        
// TEST CLUB
        
//        @Test
//        public void testPlongeesNonConformes() {
////            ajouter des plonges conformes et non conformes
////          plongeur conforme :
//            LocalDate dateValide = LocalDate.of(2020, 12, 12) ;
//            Plongeur oui = new Plongeur("numeroINSEE", "plonguer", "prenom", "adresse", "telephone", dateN) ;
//            oui.ajouteLicence("003", dateValide);
////            plongeur non conforme :
//              LocalDate dateInvalide = LocalDate.of(2000, 12, 12) ;
//            Plongeur non = new Plongeur("numeroINSEE", "plongeur", "prenom", "adresse", "telephone", dateN) ;
//            non.ajouteLicence("004", dateInvalide);
////            plongees : plongee-> invalide, plonv -> valides, ploni -> invalides
////            plongee = new Plongee(lieu, untel2, dateAu, 12, 3) ;
//            Plongee plonv1 = new Plongee(lieu, untel2, dateAu, 30, 8) ;
//            plonv1.ajouteParticipant(oui);
//            Plongee plonv2 = new Plongee(lieu, untel2, dateAu, 20, 8) ;
//            plonv2.ajouteParticipant(oui);
//            Plongee ploni1 = new Plongee(lieu, untel2, dateAu, 10, 8) ;
//            ploni1.ajouteParticipant(non);
//            Plongee ploni2 = new Plongee(lieu, untel2, dateAu, 10, 2) ;
//            ploni2.ajouteParticipant(non);
////            on les ajoute au club
//            club.organisePlongee(plonv1) ;
//            club.organisePlongee(plonv2) ;
//            club.organisePlongee(ploni1) ;
//            club.organisePlongee(ploni2) ;
////            doit donc retourner une liste avec ploni1 ploni2 et plongee
//            HashSet<Plongee> r = new HashSet<>() ;
//            r.add(ploni1) ;r.add(ploni2) ; r.add(plongee) ;
//            assertEquals(club.plongeesNonConformes(),r, "les plongees non conformes ne sont pas les bonnes" ) ;
//            
//        }
        
        @Test
        public void testGetPresident() {
            assertEquals(club.getPresident(), untel2, "le president n'est pas le bon") ;
        }
        
        @Test
        public void testSetPresident() {
            Moniteur moni = new Moniteur("numeroINSEE", "moniteur2", "prenom", "adresse", "telephone", dateN, 002) ;
            club.setPresident(moni) ;
            assertEquals(club.getPresident(), moni, "le president n'a pas été changé") ;
        }
        
           @Test
        public void testGetNom() {
            assertEquals(club.getNom(), "Club", "le nom n'est pas le bon") ;
        }
        
        @Test
        public void testSetNom() {
           club.setNom("club") ;
            assertEquals(club.getNom(), "club", "le nom n'a pas été changé") ;
        }
        
        @Test
        public void testGetTelephone() {
            assertEquals(club.getTelephone(), "01tel", "le telephone n'est pas le bon") ;
        }
        
        @Test
        public void testSetTelephone() {
           club.setTelephone("club") ;
            assertEquals(club.getTelephone(), "club", "le telephone n'a pas été changé") ;
        }
        
        @Test
        public void testGetAdresse() {
            assertEquals(club.getAdresse(), "ici", "le telephone n'est pas le bon") ;
        }
        
        @Test
        public void testSetAdresse() {
           club.setAdresse("club") ;
            assertEquals(club.getAdresse(), "club", "le telephone n'a pas été changé") ;
        }
        
//        TEST PLONGEUR
        @Test
        public void testDerniereLicence() {
              Plongeur ok = new Plongeur("numeroINSEE", "plonguer", "prenom", "adresse", "telephone", dateN) ;
//              le plongeur a une seule licence
              ok.ajouteLicence("001", LocalDate.of(2019,01,01));
              assertEquals(ok.derniereLicence(), ok.listeLicences.get(0)) ;
//              le plongeur a une deuxieme licence plus recente que la premiere
               ok.ajouteLicence("002", LocalDate.of(2020,01,20));
              assertEquals(ok.derniereLicence(), ok.listeLicences.get(1)) ;
//              le plongeur ajoute une licence plus ancienne (la derniere ne change donc pas)
                ok.ajouteLicence("003", LocalDate.of(2018,01,20));
              assertEquals(ok.derniereLicence(), ok.listeLicences.get(1)) ;
//            
            }
        
        @Test
        public void testSetClub() {
            Club club2 = new Club(untel2, "ici", "etla") ;
           untel.setClub(club2) ;
            assertEquals(untel.getClub(), club2, "le club n'a pas été changé") ;
        }

}

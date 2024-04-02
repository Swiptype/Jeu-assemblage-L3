package jeu_assemblage.ecouteur;

public interface ModeleEcoutable {
    
    /**
    La fonction ajoutEcouteur(EcouteurModele e) permet de rajouter un nouveau ecouteur a notre liste d'ecouteurs.
    @param e est un element de la classe (interface) EcouteurModele
    */
    public void ajoutEcouteur(EcouteurModele e);
    
    /**
    La fonction retraitEcouteur(EcouteurModele e) permet de retirer un ecouteur a notre liste d'ecouteurs.
    @param e est un element de la classe (interface) EcouteurModele
    */
    public void retraitEcouteur(EcouteurModele e);

}
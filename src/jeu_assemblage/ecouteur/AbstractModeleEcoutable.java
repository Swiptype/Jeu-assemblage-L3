package jeu_assemblage.ecouteur;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModeleEcoutable implements ModeleEcoutable {
    
    protected List<EcouteurModele> lesEcouteurs;
    
    public AbstractModeleEcoutable() {
        this.lesEcouteurs = new ArrayList<>();
    }

    /**
    La fonction ajoutEcouteur(EcouteurModele e) permet de rajouter un nouveau ecouteur a notre liste d'ecouteurs.
    @param e est un element de la classe (interface) EcouteurModele
    */
    public void ajoutEcouteur(EcouteurModele e) {
        this.lesEcouteurs.add(e);
    }
    
    /**
    La fonction retraitEcouteur(EcouteurModele e) permet de retirer un ecouteur a notre liste d'ecouteurs.
    @param e est un element de la classe (interface) EcouteurModele
    */
    public void retraitEcouteur(EcouteurModele e) {
        this.lesEcouteurs.remove(e);
    }

    /**
    La fonction fireChange() permet de mettre a jour tous les ecouteurs.
    */
    public void fireChange() {
        for (EcouteurModele e : lesEcouteurs) {
            e.modeleMAJ(this);
        }
    }

}

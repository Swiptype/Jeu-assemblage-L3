package jeu_assemblage.modele.player;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import jeu_assemblage.modele.game.Game;
import jeu_assemblage.modele.game.JeuAssemblage;
import jeu_assemblage.modele.game.Transfert;

public class ComputerPlayer implements Player {

	private static final boolean NODECOUNTACTIVE = true;
	private static final long STARTIME = System.currentTimeMillis();;
	public static int NODECOUNT = 0;

    private Game game;
    private int iteration;
    private List<Transfert> transferts;

    public ComputerPlayer() {
        this.game = null;
        this.iteration = 0;
        this.transferts = null;
    }

    public Transfert chooseMove(Game jeu) {

        //On affiche la grille de jeu
        System.out.println(jeu.getJAGrid());
        
        //Si on n'a pas cree le plan d'action, on le cree
    	if (this.game != jeu) {
            this.game = jeu;
            this.iteration = 0;
            this.transferts = this.plan(this.game);
        }
        Transfert transfert = this.transferts.get(this.iteration);
        this.iteration++;

        if (this.iteration == this.transferts.size()) {
            jeu.finish();
        }

        return transfert;
    }
    
    /**
     * Planifie une séquence d'actions pour un jeu donné en utilisant un algorithme de recherche ASTAR.
     * 
     * Cette méthode utilise des files d'attente et des cartes pour gérer les états du jeu,
     * les scores, et les relations entre les états. Elle sélectionne et explore les états
     * avec le score le plus élevé jusqu'à trouver un état de jeu cible avec un score suffisamment élevé.
     * score d'un carre 6x6, le problème c'est que si on calcul en fonction du nombre de coup joué il y a trop ne noeud
     * @param jeu L'état initial du jeu à partir duquel planifier les actions.
     * @return Une liste de transferts représentant le plan d'actions pour atteindre un état de jeu
     *         avec un score élevé, ou null si aucun plan n'est trouvé.
     * @throws IllegalArgumentException si les paramètres fournis ne sont pas valides.
     */
    public List<Transfert> plan(Game jeu) {

    	Queue<Game> open_liste = new LinkedList<>();
        open_liste.offer(jeu);
        Queue<Game> closed_liste = new LinkedList<>();


    	Map<Game, Transfert> plan = new HashMap<>();

    	Map<Game, Game> father = new HashMap<>();
        father.put(jeu, null);

        Map<Game, Double> maxscore = new HashMap<>();
        maxscore.put(jeu, (double) jeu.getScore());

        Map<Game, Double> value = new HashMap<>();
        value.put(jeu, estimate(jeu, maxscore));




        while (!open_liste.isEmpty()) {
            if (NODECOUNTACTIVE) {
                NODECOUNT++;
            }

            Game currentState = null;
            double plusgrand = Double.NEGATIVE_INFINITY; 

          
            for (Map.Entry<Game, Double> d : maxscore.entrySet()) {
                if (open_liste.contains(d.getKey()) && d.getValue() > plusgrand) {
                    plusgrand = d.getValue();
                    currentState = d.getKey();
                }
            }

            if(currentState != null) {
                open_liste.remove(currentState);
            }

            closed_liste.offer(currentState);
            int scorecourant = currentState.getScore();
            if((scorecourant > jeu.getGameSetting().getPieces().size() && !((JeuAssemblage) currentState).getMethScore()) || (scorecourant > jeu.getGameSetting().getPieces().size()*2 && ((JeuAssemblage) currentState).getMethScore() )) {
                return get_BFS_Plan(father, plan, currentState);
            }
            
            Set<Transfert> actions = ((JeuAssemblage) currentState).getValidTransferts();
            System.out.println("SCORE COURRANT = " + currentState.getScore());
            for (Transfert action : actions) {
            	
                if (currentState.isValid(action)) {

                	if (NODECOUNTACTIVE) {
                		NODECOUNT++;
                    }

                	Game nextState = ((JeuAssemblage) currentState).executeS(action);
                	if (nextState.getScore() > currentState.getScore()) {
                		/* DEBUG */ //System.out.println("" + nextState.getScore());	
                        if(!closed_liste.contains(nextState)) {
                            if (!maxscore.containsKey(nextState) ) {
                                maxscore.put(nextState, (double) nextState.getScore());
                            }
                            if (maxscore.get(nextState) > maxscore.get(currentState)) {
                                maxscore.put(nextState, (double) nextState.getScore());
                                //val += estimate(nextState, maxscore);
                                //value.put(nextState, val);
                                father.put(nextState, currentState);
                                plan.put(nextState, action);
                                open_liste.offer(nextState);
                            }
                        } 
                    }
                }
            }
            actions.clear();

        }

        return null;
    }

    /**
     * Estime la valeur d'un état de jeu donné.
     * 
     *
     * @param state L'état de jeu pour lequel l'estimation est requise.
     * @param distance Une carte représentant les scores des différents états du jeu.
     * @return L'estimation calculée pour l'état de jeu donné.
     */
    private Double estimate(Game state, Map<Game, Double> distance) {
		Double f;
		Double g = distance.get(state);
		f = g ;
		return f;
	}
    /**
     * Génère un plan d'actions
     * 
     * Cette méthode construit un chemin d'actions depuis l'état de jeu cible en remontant
     * grâce aux relations de parenté entre les états. Le chemin est ensuite inversé
     * pour obtenir l'ordre correct des actions.
     *
     * @param father Une carte représentant les relations de parenté entre les états du jeu.
     * @param plan Une carte indiquant les actions à effectuer pour passer d'un état à un autre.
     * @param goals L'état cible du jeu.
     * @return Une liste de transferts représentant le plan d'actions pour atteindre l'état cible.
     * @throws IllegalStateException si le chemin ne peut pas être construit.
     */
	public List<Transfert> get_BFS_Plan(Map<Game, Game> father,Map<Game, Transfert> plan, Game goals) {
    	List<Transfert> BFS_plan = new LinkedList<>();
    	while (father.get(goals) != null) {
            BFS_plan.add(plan.get(goals));
            goals = father.get(goals);
    	}

    	//BFS_plan.remove(null);
    	if (NODECOUNTACTIVE) System.out.print("Nombre noeud exploré : " + NODECOUNT + " \n");
    	
        long endTime = System.currentTimeMillis();
		long totalTime = endTime - STARTIME;
        System.out.println("Le temps d'exécution de la méthode Astar est de : " + totalTime/1000 + " secondes");

        Collections.reverse(BFS_plan);
        System.out.println(BFS_plan);

        return BFS_plan;

    }
}

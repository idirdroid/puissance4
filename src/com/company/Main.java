package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    //Initialisation des couleurs
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";



    public static void main(String[] args) {
        // write your code here

        Scanner scan = new Scanner(System.in);

        int player = 1;

        //player = initUser(scan);

        //Déclaration du tableau, possibilité de modifier sa taille
        int largeur = 7;
        int hauteur = 6;
        String [][] game = new String [hauteur][largeur];

        int choice;
        String playerTurn = "Joueur 1";
        boolean gain = false;

        do {
            //Instructions
            printGame(game, largeur);

            choice = userChoise(scan, playerTurn, largeur);
            //System.out.println("Choix de l'Utilisateur: " + choice);

            //Enregistrer le jeu de l'utilisateur
            boolean recorded = recordGame(player, choice, game);

            if (recorded) {
                gain = checkGame(game, choice, player);
                //lastPlayer = playerTurn;
                System.out.println("Valeur Bool Gain: " + gain);
                if(!gain) {
                    if (playerTurn == "Joueur 1" && recorded) {
                        playerTurn = "Joueur 2";
                        player = 2;
                    } else if (playerTurn == "Joueur 2" && recorded) {
                        playerTurn = "Joueur 1";
                        player = 1;
                    }
                }
            }

        } while (!gain && !fullGrid(game));

        if(gain){
            System.out.println("*************************" );
            System.out.println("*************************" );
            System.out.println("Fin du jeu" );
            printGame(game, largeur);
            System.out.println("*************************" );
            System.out.println("Victoire du joueur: " + playerTurn );
        }
        if(fullGrid(game)){
            System.out.println("*************************" );
            System.out.println("*************************" );
            printGame(game, largeur);
            System.err.println("Fin du jeu - La grille est pleine, auncun vainqueur !" );
            System.out.println("*************************" );
            System.out.println("*************************" );
        }

    }

    private static boolean checkGame(String[][] game, int column, int player) {
        //Fonction de test de la grille
        boolean check = false;
        //Stockage de la ligne courante du dernier jeton joué
        int line = 0;


        //System.out.println("Position du jeton : " + position);

        //Recherche de la position du jeton
        //line représente la ligne du dernier jeton joué
        //column est la dernière colonne utilisée
        for(int i=game.length-1; i>=0; i--){
            if(game[i][column] == null){
                line = i + 1;
                break;
            }
        }

        //Test de la ligne
        int compteurLine =0;
        //System.out.println("Position: " + line);
        for(int i=0; i<game[line].length; i++){
            if(game[line][i] != null) {
                if (Integer.parseInt(game[line][i]) == player) {
                    compteurLine++;
                } else {
                    compteurLine = 0;
                }
            }
            else{
                compteurLine = 0;
            }
        }
        //System.out.println("Compteur : " + compteurLine);
        if (compteurLine >= 4){
            check = true;
        }

        //Test de la colonne
        int compteurCol =0;
        //System.out.println("Position: " + line);

        for(int j=0; j<game.length; j++){
            if(game[j][column] != null) {
                if (Integer.parseInt(game[j][column]) == player) {
                    compteurCol++;
                } else {
                    compteurCol = 0;
                }
            }
        }
        //System.out.println("Compteur Colonne : " + compteurCol);
        if (compteurCol >= 4){
            check = true;
        }

        //******************************************
        //Test des diagonales (4 tests à faire)
        //******************************************
        int compteurDiag = 1;

        //test vers bas gauche et haut droit
        //On commence par bas gauche
        int line_temp = line + 1;
        int column_temp = column -1;

        while((line_temp<=game.length -1) && (column_temp>=0) && game[line_temp][column_temp] != null)
        {
            System.out.println(player + " On entre dans la boucle avec  " + line_temp + " | " + column_temp + "Compteur: " + compteurDiag);
            if(Integer.parseInt(game[line_temp][column_temp]) == player){
                compteurDiag++;
            }
            else{
                break;
            }
            line_temp++;
            column_temp--;
        }

        System.out.println("Compteur Diag Bas - Gauche : " + compteurDiag);
        if (compteurDiag >= 4){
            check = true;
        }

        //On continue avec haut droite
        //Test vers haut droite
        //compteurDiag =1;
        line_temp = line - 1;
        column_temp = column + 1;

        while((line_temp>=0) && (column_temp<=game[0].length -1) && game[line_temp][column_temp] != null)
        {
            System.out.println(player + " On entre dans la boucle avec  " + line_temp + " | " + column_temp + "Compteur: " + compteurDiag);
            if(Integer.parseInt(game[line_temp][column_temp]) == player){
                compteurDiag++;
            }
            else{
                break;
            }
            line_temp--;
            column_temp++;
        }

        System.out.println("Compteur Diag haut - droite : " + compteurDiag);
        if (compteurDiag >= 4){
            check = true;
        }

        //Deuxieme diagonale a tester
        //Test vers bas droite
        compteurDiag =1;
        line_temp = line + 1;
        column_temp = column +1;

        while((line_temp<=game.length -1) && (column_temp<=game[0].length -1) && game[line_temp][column_temp] != null)
        {
            System.out.println(player + " On entre dans la boucle avec  " + line_temp + " | " + column_temp + "Compteur: " + compteurDiag);
            if(Integer.parseInt(game[line_temp][column_temp]) == player){
                compteurDiag++;
            }
            else{
                break;
            }
            line_temp++;
            column_temp++;
        }

        System.out.println("Compteur Diag Bas - droite : " + compteurDiag);
        if (compteurDiag >= 4){
            check = true;
        }

        //on enchaine avec l'autre côté de la diagonale
        //Test vers haut gauche

        line_temp = line - 1;
        column_temp = column - 1;


        while((line_temp>=0) && (column_temp>=0) && game[line_temp][column_temp] != null)
        {
            System.out.println(player + " On entre dans la boucle avec  " + line_temp + " | " + column_temp + "Compteur: " + compteurDiag);
            if(Integer.parseInt(game[line_temp][column_temp]) == player){
                compteurDiag++;
            }
            else{
                break;
            }
            line_temp--;
            column_temp--;
        }

        System.out.println("Compteur Diag haut - gauche : " + compteurDiag);
        if (compteurDiag >= 4){
            check = true;
        }

        //Envoi du resultat du test
        return check;
    }


    private static boolean fullGrid(String[][] grid) {
        boolean test = true;
        //Boucle test grille pleine
        for (int i=0; i< grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == null){
                    test = false;
                }
            }
        }
        return test;
    }

    private static boolean recordGame(int player, int choice, String [][] game) {
        //Varaible boolean qui permet de renvoyer la validation du jeu de l'utilisateur
        boolean recorded = false;
        for(int i=game.length-1; i>=0; i--){
            if(game[i][choice] == null){
                game[i][choice] = Integer.toString(player);
                recorded = true;
                break;
            }
        }

        if(!recorded) {
            System.err.println("Plus d'espace dans la colonne choisie !");
        }
        return recorded;
    }

    private static int userChoise(Scanner scan, String playerTurn, int largeur) {
        boolean check = false;
        int userInput = 0;
        do {
            System.out.println(playerTurn + ", Choisissez une colonne: ");
            try{
                userInput = scan.nextInt();

                if((userInput<1) || (userInput>largeur)){
                System.err.println(playerTurn + ", Merci de saisir un nombre entre 1 et " + largeur);
                check = false;
                }
                else {
                    check = true;
                }
            } catch (InputMismatchException e){
                check = false;
                scan.nextLine();
                System.err.println(playerTurn + ", La saisie " + userInput + " n'est pas une nombre entre 1 et " + largeur);
            }
        } while (!check);

        // On récupère la valeur de l'utilisateur
        //On retire 1 pour tenir compte de l'index des tableaux qui commence à zéro
        return userInput -1;
    }

    private static void printGame(String [][] game, int largeur) {
        //Affichage entête
        for(int i=0; i<largeur; i++){
            System.out.print("-----");
        }
        System.out.println("");

        System.out.print("|");

        for (int i=1; i< (game[0].length)+1; i++){
            System.out.print(" " + i + " |");
        }

        System.out.println("");
        for(int i=0; i<largeur; i++){
            System.out.print("-----");
        }
        System.out.println("");

        //Affichage du tableau
        for (int i=0; i< game.length; i++){
            System.out.print("| ");
            for(int j=0; j< game[i].length; j++){
                if(game[i][j] == null){
                    System.out.print(" " + " | ");
                }
                else {
                    //Affichage d'une croix ou d'un rond
                    switch (game[i][j]) {
                        case "1":
                            System.out.print(ANSI_RED + "X" +  ANSI_RESET + " | "); break;
                        case "2" :
                            System.out.print(ANSI_YELLOW + "O" + ANSI_RESET + " | "); break;

                    }
                    //System.out.print(game[i][j] + " | ");
                }
            }
            System.out.print("\n");
        }

        //Affichage footer

        for(int i=0; i<largeur; i++){
            System.out.print("-----");
        }
        System.out.println("");
    }


}


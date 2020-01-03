package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Scanner;

/**
 * Programme pour un client TCP
 */
public class ClientTCP {
	public static void main(String args[]){

		//on demande d'abord au client ce qu'il veut faire
		Scanner scannerPort = new Scanner(System.in);
		System.out.println("Entrer le numéro de port");
		int port = scannerPort.nextInt();

		Scanner scanner = new Scanner(System.in);

		// Création d'un socket client et connexion avec un serveur
		Socket sc = null;
		try {
			sc = new Socket("localhost", port);
		} catch (IOException e) {
			System.out.println("Impossible d'acceder au serveur, vérifier le numéro de port");
		}


		// Construction d'un BufferedReader pour lire du texte envoyé à travers la connexion socket
		BufferedReader entreeSocket = null;
		try {
			entreeSocket = new BufferedReader(new InputStreamReader(sc.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Construction d'un PrintStream pour envoyer du texte à travers la connexion socket
		PrintStream sortieSocket = null;
		try {
			sortieSocket = new PrintStream(sc.getOutputStream());
		} catch (IOException e) {
			System.out.println("Impossible d'ouvrir la connection");
		}

		String chaine = "";
		
		System.out.println("Tapez vos phrases ou FIN pour arrêter :");
					
		while(!chaine.equalsIgnoreCase("FIN")) {
				// lecture clavier
				chaine = scanner.nextLine();
				sortieSocket.println(chaine); // on envoie la chaine au serveur

				// lecture d'une chaine envoyée à travers la connexion socket
				try {
					chaine = entreeSocket.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Chaine reçue : "+chaine);

			}
		
		// on ferme nous aussi la connexion
		try {
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

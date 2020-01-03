# Welcome on doc !
Vous trouverez sur cette petite documentation complémentaire à la java doc, les instructions necessaires à la mise en oeuvre des serveurs d'identification
Par serveur d'identification on parle en fait de l'ensemble des servers du TPs.
Pareil pour requette d'identification qui comprends également les requetes d'ajout, modification, suppression ...

# Fonctionnement

## Comment demarrer les serveurs

1. Vérifier l'environement d'éxecution

Assurer vous d'abord de ne pas avoir les ports suivant d'ouvert sur la machine qui va lancer les serveurs :
   * `28414` : port du serveur Checker en UDP et TCP
   * `28415` : port du serveur Manager en TCP
   * `3244` : port du serveur de log
   
2.  Lancer le serveur de log

Lancer le programme `LogServiceApp` du paquetage `logService`

Celui-ci va vous proposer de selectionner un fichier json dans lequel enregistrer les logs, puis demarrera un serveur TCP auquel il aura rattacher un gestionnaire qui traite les requetes de log.
 
3. Lancer le programme `AuthenticationServiceApp` du paquetage `authenticationService`

Ce programme va successivement démmarer les trois serveurs necessaires pour traiter les demandes tel que specifiées dans le TP

    Les services sont désormais fonctionnelles, vous pouvez y acceder en utlisant les deux clients mis à votre disposition dans le paquetage client
 
## Utilliser les clients

### Avec TCP
Lancer le programme `ClientTCP` du paquetage `client`, au démarrage donner le port correspondant au service auquel vous connecter (voir liste des ports ci-dessus).

### Avec UDP
Lancer le programme `ClientTCP` du paquetage `client`, le client se connecte alors automatiquement au `28414` car c'est le seul à fournir des services pour une requête UDP.

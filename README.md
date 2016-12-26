Information sur les membres de l'équipe:
Eloi Brassard-Gourdeau eloibg eloibrassardg@gmail.com
Jean-François Collin jfcollin coachjeffcollin@gmail.com
Nicolas Déry nick3135 nick3185@gmail.com
Marina Fortin mafor530 marina.fortin.1@ulaval.ca
David Gagnon-Dupuis davidgdupuis david.gdupuis@gmail.com
Roxanne Landry roxou234 roxanne.landry.2@ulaval.ca
Maxime Paré MaximePare maxime.pare.4@ulaval.ca
Romy Piché romypiche ropiche48@gmail.com
Eliane Trudel eltru7 eliane.trudel.1@ulaval.ca

Liste de "story" réalisée:
Story 1 à 20 

Somaire de l'utilisation:
Pour démarrer le serveur utiliser les commande suivantes :
mvn clean install et ensuite
mvn exec:java -Dreservation.port=8888 -Dboarding.port=9999 -pl app

Vous devez remplacer 8888 et 9999 par les numéros de ports désirés.

Pour exécuter les test utiliser la commande 
mvn test ou mvn integration-test pour les tests d'intégration

Pour exécuter les tests de charge, ouvrir le fichier TestQualitePerfoCharge.jmx dans jmeter, mettre le fichier numbers.csv au même niveau que le dossier contenant jmeter et lancer jmeter.




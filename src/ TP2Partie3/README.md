# Tutoriel Spoon #

### Auteur : BEYA NTUMBA Joel

>**Spoon** est une librairie open source capable d'analyser du code source et de le transformer (rajouter du code pour analyser un comportement précis ou vérifier certaines conditions). Avant d'analyser quoi que ce soit *Spoon* parse les fichiers sources afin d'en construire l'AST (Arbre syntaxique abstrait).

>Chaque élément d’un programme (classes, méthodes, attributs, etc.) sont mappés par un élément du Méta-Modèle de Spoon. De plus, tous les éléments peuvent être accéder en lecture et écriture. Il est aussi capable de s’intégrer avec Gradel et Maven, ce qui le rend pratique.

### Différents cas d'utilisation de Spoon :

**`Analyse de code source`**
> Calcul des métriques intéressants 
> Amélioration des tests
> Application des techniques d'analyse statique pour l'identification des bugs

**`Traitement de code`**
> Génération de code à partir d'un modèle
> Transformation de code et Refactoring

### Utilisation (Analyse statique des données)

On commence le tutoriel par afficher l'AST de nos fichiers sources en utilisant l'interface graphique avec la commande suivante :

	java -cp spoon-core-6.0.0-jar-with-dependencies.jar spoon.Launcher -i spooned/Impot.java --gui --noclasspath

Nous obtenons la structure suivante:

**`

![testgui](https://user-images.githubusercontent.com/22857199/33799042-34df09c0-dd24-11e7-852f-5d9a2097a9b6.PNG)

>Afin d'analyser un ensemble de classes à la fois , on opte pour l'écriture d'une classe Main qui testera plusieurs fonctionnalités de *Spoon* à la fois.

On commence par itérer les classes du projet puis les parser comme ceci :

	for (File fileEntry : javaFiles){
	 	CtClass<?> classe = Launcher.parseClass(FileUtils.readFileToString(fileEntry));
	}
	
	
puis la récupération des méthodes,constructeurs, attributs ... se fait par l'appel de méthodes suivant:
	
*	classe.getMethods();
*	classe.getAllFields());
*	classe.getConstructors();
*	...

Exemple :


![code](https://user-images.githubusercontent.com/22857199/33799053-67e99966-dd24-11e7-831d-7a6a060b73e6.PNG)  ![test2](https://user-images.githubusercontent.com/22857199/33799060-7a9b9ef6-dd24-11e7-92a1-d0e604f0e853.PNG)

	
>En plus des méthodes de récupérations précédentes, *Spoon* met à notre disposition des méthodes qui servent à détecter certaines "anomalies" ou "pratiques déconseillées" dans le code tel que les block catch  ou les méthodes  vides, les méthodes ne possédant pas de documentation... 
Voici les méthodes testées :

*	EmptyMethodBodyProcessor() 
![emptymethodbody](https://user-images.githubusercontent.com/22857199/33799074-a7423cf8-dd24-11e7-8641-4852c53595b2.PNG)


*	CatchProcessor()  
![catchprocessor](https://user-images.githubusercontent.com/22857199/33799078-b98ff85a-dd24-11e7-98d2-eea9bdfcacbc.PNG)

*	DocProcessor()<br/> 
![docprocessor](https://user-images.githubusercontent.com/22857199/33799077-b9783620-dd24-11e7-8f11-45dbb57b0f81.PNG)



Concerant la tansformation de code, on a testé le rajout de conditions(not null) sur les parametres d'entrée des méthodes et l'ajout de logs pour le debuggage :

*	NotNullCheckAdderProcessor()
![checknotnull](https://user-images.githubusercontent.com/22857199/33799082-d089bf46-dd24-11e7-99e4-06d2ae331b2c.PNG)

*	LogProcessor() 

![logprocessor](https://user-images.githubusercontent.com/22857199/33799084-d7312866-dd24-11e7-9668-a190993b23e6.PNG)


### Mini-Tutoriel sur Spoon

> J'ai proposé un scénario pour présenter l'utilité de Spoon comme outil de refactoring à travers la classe SpoonTutorial
Inspiré de OW2con'18 Spoon: open source library to analyze, rewrite, transform, transpile Java source code
Utilise le programme d'exemple qu'il faut refactorer.
Les classes refactorées par le programme sont placées dans le dossier parent de ces dernières (lib/SimpleSample/company/src/com/company), pour être facilement comparées.

**`Scénario :`**

> Lors d'une code review, vous vous rendez-compte qu'une faille dans le code permet à des classes non autorisées de modifier librement des personnes.

> Après inspection, il s'avère que l'attribut statique allPersons de la classe Person a été laissé public, malgré le fait qu'un getter getAllPersons() existe déjà.

> On se rend compte au passage que le getter encapsule mal le champ et renvoie directement l'objet pointé par Person.allPersons (et permet donc d'obtenir une référence vers allPersons qui devrait être privé).

**`Objectifs :`**

+ Passer le champ Person.allPersons en private

+ Altérer Person.getAllPersons() pour qu'il retourne une copie de la liste privée de Personnes

+ Modifier dans la classe Main les accès en lecture du champ Person.allPersons pour appeler à la place le getter dorénavant sûr

**`Solution `**

+ Avec Spoon et en utilisant les bonnes requêtes, le programme récupère successivement les références vers :

> Les classes Person et Main

> La méthode getAllPersons()

> Le champ allPersons

+ La visibilité de allPersons et passée à PRIVATE

+ Le code de getAllPersons() est remplacé pour renvoyer Person.allPersons.clone()

+ Les accès en lecture du champ allPersons au sein de la classe Main sont remplacés par l'invocation de la méthode getAllPersons()

+ Un commentaire "TODO : check this auto-generated patch !" est ajouté à chaque remplacement



### Conclusion 
>On a testé quelques méthodes d'analyse et de transformation que propose *Spoon* et il est forcé de constater que l'analyse est bien plus légère et transparent car on ne voit pas les visitors et on ne les gère pas

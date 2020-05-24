# BeWizUProjet_AL_M1
Projet réalisé par Fabien ALAZET et Nicolas DEGUILLAUME dans le cadre du cours d'Architecture Logiciel du Master 1 Informatique de l'université de Bordeaux.
## Description
L'application est un logiciel de dessin vectoriel  dont le principe est de créer de nouveaux dessins en utilisant/modifiant/combinant des dessins existants. Plusieurs fonctionnalités sont proposées, qui sont les suivantes : 
<ul>
    <li> Proposer au minimum 2 objets : le polygone régulier et le rectangle</li>
    <li> Sélectionner un objet depuis un menu graphique (toolbar), et le positionner sur notre dessin – (whiteboard) en utilisant le glisser-déposer (drag and drop)</li>
    <li> Créer des groupes d’objets et sous-groupes d’objets, sous-sous-groupes etc</li>
    <li> Dissocier un groupe d’objet</li>
    <li> Modifier la taille, position, etc… de nos objets ou groupes d’objets une fois ceux-ci incorporés dans le dessin</li>
    <li> Ajouter des groupes d’objets ou des objets paramétrés à notre toolbar en les déposants sur la toolbar (drag and drop)</li>
    <li> Annuler ou refaire une opération</li>
    <li> Sauvegarder un document et charger un document</li>
    <li> Sauvegarder l’état du logiciel (toolbar) et le recharger au démarrage.</li>
</ul>
Nous avons utilisé certains des Patrons de Conception vus en cours que nous avons jugés pertinents dans la réalisation des fonctionnalités demandées ainsi que dans l'optique de ne pas assujettir notre code à un moteur graphique (ici, javaFX) et pour permettre la réutilisation de code.

## Prérequis

<p>L'application est une application JavaFX : vous aurez besoin des sources JavaFX pour la lancer (<a href="https://www.oracle.com/java/technologies/javase-jdk8-downloads.html">sdk 8</a> ou <a href="https://gluonhq.com/products/javafx/">javafx sdk</a>).</p>

Vous devez avoir cette configuration de fichiers afin de lancer l'application :
<code>
<ul>
<li> resources <ul>
    <li> saves <ul> 
        <li> autosave.ser </li>
        </ul>
        </li>
    <li> ico <ul>
        <li> bin.png </li>
        <li> ico.png </li>
        <li> load.png </li>
        <li> save.png </li>
        <li> undo.png </li>
        <li> redo.png </li>
        </ul>
        </li>
</ul>
<li> BeWizU.jar </li>
</ul>
</code>

## Lancer l'application

<p>Si vous avez le <a href="https://www.oracle.com/java/technologies/javase-jdk8-downloads.html">sdk 8</a> vous pouvez lancer l'application en vous plaçant dans le dossier du fichier .jar et en utilisant la commande <code>java -jar BeWizU.jar</code> ou en double cliquant sur l'application.</p> 

<p>Si vous avec le <a href="https://gluonhq.com/products/javafx/">javafx sdk</a> vous pouvez lancer l'application en vous plaçant dans le dossier du fichier <code>.jar</code> et en utilisant la commande <code>java --module-path &lt;chemin vers javafx sdk&gt;\lib --add-modules javafx.controls,javafx.graphics,javafx.fxml -jar BeWizU.jar</code></p>

## Rapport

Pour plus d'information sur le projet, consultez <a href="https://github.com/Peinturalo/BeWizUProjet_AL_M1/blob/master/rapport.pdf">notre rapport</a>.

## Vidéo

Une vidéo de présentation du projet est disponible
[![BeWizU](https://i.imgur.com/gZxlVnl.png)](https://www.youtube.com/watch?v=cWrU5IltSFo "BeWizU")
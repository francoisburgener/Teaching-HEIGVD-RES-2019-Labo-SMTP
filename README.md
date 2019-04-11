# Teaching-HEIGVD-RES-2019-Labo-SMTP

## Description



## Instructions



### Seeting up 

Avant de tester notre client qui envoie des messages prank a des groupes d'email différent il faut tout d'abord créer un fichiers de configuration pour pouvoir récupérer l'IP, le port, la list des adresse ainsi que la liste de message. Dans notre cas nous avons définie notre fichier de configue de la manière suivant :

#### config.properties

```properties
SMTPAddress = 192.168.99.100
SMTPPort = 25
nbreGroup = 3
victimsFile = ./config/victims.txt
messagesFile = ./config/message.txt
```

#### victims.txt

```
francois.burgener@heig-vd.ch
francois.burgener@hotmail.fr
burgener.francois@gmail.com
smtpheig@gmail.com
baptiste.hardrick@heig-vd.ch
batach31@hotmail.com
baptiste.hardrick@gmail.com
batach31@gmail.com
olivier.liechti@heig-vd.ch
raphael.machin@heig-vd.ch
```

#### messages.txt

Nos messages sont structuré de la manière suivante. Il y aura a la première ligne du message qui sera **subject: XXX** qui sera suivit de notre body. Et a la fin du message nous avont les caractère de séparation **--** qui nous servent a séparer les différents messages.

```
Subject: Vous avez gagne 1000000 d'euros

Bravo pour ces 1000000 durement mérité grâce à la chance!
Veuillez vous connecter au site www.unmillion.com, il vous faudra entrer votre carte de crédit pour
recevoir votre argent!
--
Subject: Bravo, 6 en RES!

C'est pas vrai, en fait vous avez eu 1!
--
Subject: Votre maman ne va pas bien

Suite à l'annonce de la réinvestiture du président Trump, votre mère a fait un arrêt cardiaque.
--
Subject: Besoin d'argent?

Venez sur www.trading.com, grâce à nos experts, vous pourrez gagner plus de 1000CHF en moins de 10min!
Succès garanti!
--
Subject: Vous etes devenu president!

Suite à un vote très démocratique, vous êtes devenu le président
--
Subject: Felicitation, vous avez gagne un voyage!

Venez passer un bon moment à Bora-Bora!
Connectez vous  sur notre site pour recevoir votre prix
```

### Testing

Pour tester notre programme il faudra tout d'abords lancer le serveur SMTP. Nous utilisons le serveur SMTP de mockmock.  Pour cela il faudra créer l'image en fonction de notre Dockerfile qui se trouve dans le dossier docker.

```dockerfile
FROM java:8

ADD ["https://github.com/tweakers-dev/MockMock/blob/master/release/MockMock.jar?raw=true", "/mail/MockMock.jar"]

CMD ["java", "-jar", "/mail/MockMock.jar"]
```

Ensuite pour créer l'image il suffit de lancer la commande suivant depuis le dossier ou se trouve le Dockerfile

```
docker build -t nameoftheimage .
```

Ensuite on vérifie que notre images a bien était créer en faisant

```
docker images
```

Normalement vous devrez voir une images avec le nom de votre image

Si votre images a été créer un suffit de lancer un container avec cette image en faisant la commande suivant 

```
docker run -p 8282:8282 -p 25:25 nameoftheimage
```

 le parametre **-p x:y** permet de lier le port local **x** au port **y** du serveur. Ici comme mockmock communique avec le port 8282 (page web) et le port 25 (serveur smtp) on doit lier ses deux port afin de pouvoir communiquer avec lui depuis l'extérieur.

### Time to prank

Maintenant que vous avez lancer le serveur SMTP mockmock vous pouvez lancer notre client stmp. Il faut que le jar du client soit au même niveau que le dossier de config. Ensuite pour lancer notre client il faut faire la commande suivant.

```
java -jar monjar ./config/config.properties
```

Maintenant si vous allez sur l'adresse **192.168.99.100:8282** vous devrez voire que le serveur SMTP a recu des email.

## Implementation

[UML]

### Configuration Manager

[Explication]

### Smtp client

[Explication]

[Diagram de flux entre client/serveur]

### Prank generator

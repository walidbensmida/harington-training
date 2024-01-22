# Utilisez une image OpenJDK 17 comme base
FROM openjdk:17-oracle
# Exposez le port si votre application utilise un port spécifique
EXPOSE 8080
# Copiez le fichier JAR de l'application dans l'image
ADD ./target/harington-training-0.0.1-SNAPSHOT.jar /harington-training-0.0.1-SNAPSHOT.jar
# Commande pour exécuter l'application lors du démarrage du conteneur
ENTRYPOINT ["java", "-jar", "/harington-training-0.0.1-SNAPSHOT.jar"]

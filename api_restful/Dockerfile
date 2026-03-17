# Estágio 1: Build da Aplicação com Maven
# Usamos uma imagem oficial do Maven com uma versão do JDK compatível (17)
FROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos de definição do projeto (pom.xml)
COPY pom.xml .

# Baixa todas as dependências do projeto. Isso aproveita o cache de camadas do Docker.
RUN mvn dependency:go-offline

# Copia todo o código-fonte da aplicação
COPY src ./src

# Compila a aplicação e empacota em um arquivo .jar
RUN mvn package -DskipTests


# Estágio 2: Criação da Imagem Final
# Usamos uma imagem base do OpenJDK 17 mais leve, apenas com o necessário para rodar a aplicação
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo .jar gerado no estágio de build para a imagem final
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080, que é a porta padrão do Spring Boot
EXPOSE 8080

# Comando para executar a aplicação quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]

# --- ESTÁGIO 1: Construção (Build) ---
# Usa a imagem oficial do Node.js super leve (alpine)
FROM node:18-alpine as build

# Define a pasta de trabalho dentro do contêiner
WORKDIR /app

# Copia apenas os arquivos de dependência primeiro (otimiza o cache do Docker)
COPY package.json package-lock.json ./

# Instala as dependências
RUN npm install

# Copia todo o resto do seu código
COPY . .

# Roda o comando para gerar a versão final de produção (pasta build/)
RUN npm run build


# --- ESTÁGIO 2: Servidor Web (Produção) ---
# Usa a imagem oficial do Nginx (servidor web de alta performance)
FROM nginx:alpine

# Copia os arquivos gerados no Estágio 1 para a pasta pública do Nginx
COPY --from=build /app/build /usr/share/nginx/html

# Libera a porta 80 (padrão de internet)
EXPOSE 80

# Liga o servidor
CMD ["nginx", "-g", "daemon off;"]
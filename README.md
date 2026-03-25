# ☁️ Unifor Cloud - Painel de Gestão de Chamados

**Trabalho acadêmico desenvolvido para a disciplina de Desenvolvimento de Software em Nuvem da Universidade de Fortaleza - UNIFOR.**

Este é um sistema de Gestão de Suporte Técnico desenvolvido para facilitar a abertura, o acompanhamento e a resolução de chamados de TI. O projeto possui uma interface moderna, amigável e **100% responsiva**, adaptando-se perfeitamente a desktops, tablets e smartphones.

Projeto prático com foco em arquitetura web, responsividade e integração de banco de dados em nuvem.

## ✨ Funcionalidades

- **CRUD Completo:** Abertura (Create), visualização de detalhes (Read), avanço de status (Update) e exclusão (Delete) de chamados.
- **Dashboard Dinâmico:** Cards de resumo atualizados em tempo real com o total de chamados Abertos, Em Atendimento, Finalizados e o Total Geral.
- **Filtros Inteligentes:** Clique nos cards para filtrar a tabela instantaneamente por status.
- **Busca em Tempo Real:** Barra de pesquisa para encontrar chamados rapidamente pelo Título ou ID.
- **Sistema de Urgência:** Identificação visual de prioridades (Baixa, Média, Alta) com badges coloridos.
- **Design Responsivo:** Layout fluido que converte a barra lateral (Sidebar) do PC em um menu de rodapé ou menu superior no celular, garantindo a melhor experiência UX/UI mobile.
- **Notificações:** Alertas visuais (Toasts) para confirmar ações como criação ou exclusão de chamados.

## 🛠️ Tecnologias Utilizadas

- **Frontend:** [React.js](https://reactjs.org/) (com React Hooks)
- **Roteamento:** React Router Dom
- **Backend / Banco de Dados:** [Supabase](https://supabase.com/) (PostgreSQL + API RESTful)
- **Alertas:** React Toastify
- **Deploy / CI/CD:** [Vercel](https://vercel.com/) (Integração contínua via GitHub)

## 🛠️ Instalação

- **Clone o repositório:**
git clone https://github.com/hkaroll/Desen-de-Software-em-Nuvem.git

- **Acesse a pasta do projeto:**
cd Desen-de-Software-em-Nuvem

- **Instale as dependências:**
npm install

## 🔗 Link da Aplicação

-- https://desen-de-software-em-nuvem.vercel.app/

## 📸 Preview

![Preview](./assets/tela1.png)
![Preview](./assets/tela2.png)
![Preview](./assets/tela3.png)
![Preview](./assets/tela4.png)
![Preview](./assets/tela5.png)

## 📂 Estrutura do projeto

```text
UNIFOR-HELPDESK/
├── node_modules/
├── public/
│   ├── assets/
│   ├── favicon.ico
│   ├── index.html
│   ├── logo.ico
│   ├── logo192.png
│   ├── logo512.png
│   ├── manifest.json
│   └── robots.txt
├── src/
│   ├── components/
│   ├── App.css
│   ├── App.js
│   ├── App.test.js
│   ├── index.css
│   ├── index.js
│   ├── logo.svg
│   ├── reportWebVitals.js
│   ├── setupTests.js
│   └── supabaseClient.js
├── .dockerignore
├── .env
├── .gitignore
├── Dockerfile
├── package-lock.json
├── package.json
└── README.md
```

## Autores (Matrículas)

Esse trabalho foi desenvolvido pela Equipe:

-- Hevlina Karoll Lima Reis (Matrícula: 2425124)
-- Francisco Erasmo Pires Abreu (Matrícula: 2415473)
-- Ana Beatriz da Silva de Oliveira (Matrícula: 2425104)
-- Levi Martins Marques (Matrícula: 2425085)
-- Higor Reis de Sátiro (Matrícula: 2425093)
-- Mayra Ribeiro da Silva (Matrícula: 2425026)
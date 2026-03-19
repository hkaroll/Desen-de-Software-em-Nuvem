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
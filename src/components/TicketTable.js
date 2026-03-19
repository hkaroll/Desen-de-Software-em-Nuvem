import React from 'react';

function TicketTable({ tickets, onStatusChange, onViewTicket }) {
  return (
    <div className="tickets-table-container">
      <table className="tickets-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Assunto</th>
            <th>Prioridade</th>
            <th>Status</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {tickets.map(t => (
            <tr key={t.id}>
              <td>#{t.id}</td>
              <td>{t.subject}</td>
              <td>
                <span className={`badge priority-${t.priority?.toLowerCase() || 'baixa'}`}>
                  {t.priority || 'Baixa'}
                </span>
              </td>
              <td>
                <select 
                  className="status-select" 
                  value={t.status} 
                  onChange={(e) => onStatusChange(t.id, e.target.value)}
                >
                  <option value="Aberto">Aberto</option>
                  <option value="Em Andamento">Em Andamento</option>
                  <option value="Finalizado">Finalizado</option>
                </select>
              </td>
              <td>
                <button className="btn-action-view" onClick={() => onViewTicket(t)}>
                  <i className="fas fa-eye"></i>
                </button>
              </td>
            </tr>
          ))}
          {tickets.length === 0 && (
            <tr>
              <td colSpan="5" style={{textAlign: 'center', padding: '30px'}}>
                Nenhum chamado encontrado.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}

export default TicketTable;
import React from 'react';

function ViewTicketModal({ ticket, onClose }) {
  if (!ticket) return null;

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <span className="close-modal" onClick={onClose}>&times;</span>
        <h2>Detalhes #{ticket.id}</h2>
        <div className="view-content">
          <p><strong>Assunto:</strong> {ticket.subject}</p>
          <p><strong>Status:</strong> {ticket.status}</p>

          <p><strong>Aberto por:</strong> {ticket.nome ? ticket.nome : 'Não informado'}</p>
          <p><strong>Setor:</strong> {ticket.setor ? ticket.setor : 'Não informado'}</p>
          <div className="view-description-box">{ticket.description}</div>
        </div>
        <button className="modal-submit-btn" style={{marginTop: '20px'}} onClick={onClose}>
          Fechar
        </button>
      </div>
    </div>
  );
}

export default ViewTicketModal;
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
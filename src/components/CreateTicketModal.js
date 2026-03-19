import React from 'react';

function CreateTicketModal({ onSubmit, onClose }) {
  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <span className="close-modal" onClick={onClose}>&times;</span>
        <h2>Novo Chamado</h2>
        <form onSubmit={onSubmit}>
          <div className="modal-form-group">
            <label>Assunto</label>
            <input name="subject" required />
          </div>
          <div className="modal-form-row">
            <div className="modal-form-group">
              <label>Prioridade</label>
              <select name="priority">
                <option value="Baixa">Baixa</option>
                <option value="Média">Média</option>
                <option value="Alta">Alta</option>
              </select>
            </div>
            <div className="modal-form-group">
              <label>Categoria</label>
              <select name="category">
                <option value="Suporte">Suporte</option>
                <option value="Infra">Infra</option>
              </select>
            </div>
          </div>
          <div className="modal-form-group">
            <label>Descrição</label>
            <textarea name="description" rows="3" required />
          </div>
          <button type="submit" className="modal-submit-btn">Criar Chamado</button>
        </form>
      </div>
    </div>
  );
}

export default CreateTicketModal;
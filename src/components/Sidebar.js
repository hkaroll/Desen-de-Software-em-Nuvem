import React from 'react';
import { Link, useLocation } from 'react-router-dom';

function Sidebar({ user, onLogout }) {
  const location = useLocation();

  return (
    <aside className="sidebar">
      <div className="sidebar-header">
        <h2>Unifor Cloud</h2>
      </div>

      <div className="user-info-sidebar">
        <p>Logado como:</p>
        <strong>{user}</strong>
      </div>

      <nav className="sidebar-nav">
        <Link to="/dashboard" style={{ textDecoration: 'none' }}>
          <button className={`nav-button ${location.pathname === '/dashboard' ? 'active' : ''}`}>
            <i className="fas fa-home"></i> Dashboard
          </button>
        </Link>

        <Link to="/configuracoes" style={{ textDecoration: 'none' }}>
          <button className={`nav-button ${location.pathname === '/configuracoes' ? 'active' : ''}`}>
            <i className="fas fa-cog"></i> Configurações
          </button>
        </Link>
        
        <button className="nav-button" onClick={onLogout} style={{ marginTop: 'auto', color: '#fc8181' }}>
          <i className="fas fa-sign-out-alt"></i> Sair
        </button>
      </nav>
    </aside>
  );
}

export default Sidebar;
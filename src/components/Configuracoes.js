import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Sidebar from './Sidebar';
import { toast } from 'react-toastify';
import { supabase } from '../supabaseClient';

function Configuracoes() {
  const navigate = useNavigate();
  const [user, setUser] = useState("");
  
  const [fullName, setFullName] = useState("");
  const [department, setDepartment] = useState("");
  const [email, setEmail] = useState("");
  const [loadingProfile, setLoadingProfile] = useState(false);

  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [loadingPassword, setLoadingPassword] = useState(false);

  useEffect(() => {
    const fetchUserData = async () => {
      const savedUser = localStorage.getItem('uniforUser');
      if (!savedUser) {
        navigate('/');
        return;
      }
      setUser(savedUser);

      const { data: { session } } = await supabase.auth.getSession();
      if (session?.user) {
        setEmail(session.user.email);
        setFullName(session.user.user_metadata?.full_name || "");
        setDepartment(session.user.user_metadata?.department || "");
      }
    };

    fetchUserData();
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem('uniforUser');
    navigate('/');
  };

  const handleUpdateProfile = async (e) => {
    e.preventDefault();
    setLoadingProfile(true);

    const { error } = await supabase.auth.updateUser({
      data: { full_name: fullName, department: department }
    });

    if (error) {
      toast.error('❌ Erro ao atualizar os dados do perfil.');
    } else {
      toast.success('✅ Perfil atualizado com sucesso!');
      
      const firstName = fullName.split(' ')[0] || fullName;
      localStorage.setItem('uniforUser', firstName);
      setUser(firstName);
    }
    setLoadingProfile(false);
  };

  const handleUpdatePassword = async (e) => {
    e.preventDefault();
    
    if (newPassword !== confirmPassword) {
      toast.warning('⚠️ As senhas digitadas não coincidem!');
      return;
    }

    if (newPassword.length < 6) {
      toast.warning('⚠️ A nova senha deve ter no mínimo 6 caracteres.');
      return;
    }

    setLoadingPassword(true);

    const { error } = await supabase.auth.updateUser({
      password: newPassword
    });

    if (error) {
      toast.error('❌ Erro ao alterar a senha. Tente novamente.');
    } else {
      toast.success('🔒 Senha alterada com sucesso!');
      setNewPassword("");
      setConfirmPassword("");
    }
    setLoadingPassword(false);
  };

  return (
    <div className="dashboard-container">
      <Sidebar user={user} onLogout={handleLogout} />

      <main className="main-content">
        <header className="content-header">
          <div className="header-left">
            <h1>Configurações da Conta</h1>
          </div>
        </header>

        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '2rem', alignItems: 'start' }}>
          
          <section style={{ background: '#fff', padding: '2rem', borderRadius: '12px', boxShadow: '0 4px 6px rgba(0,0,0,0.05)' }}>
            <h3 style={{ marginBottom: '20px', color: '#2d3748', borderBottom: '2px solid #e2e8f0', paddingBottom: '10px' }}>
              <i className="fas fa-user" style={{ marginRight: '8px', color: '#1a73e8' }}></i> Dados do Perfil
            </h3>
            
            <form onSubmit={handleUpdateProfile}>
              <div className="login-field-group" style={{ marginBottom: '15px' }}>
                <label style={{ fontWeight: 'bold', display: 'block', marginBottom: '5px' }}>E-mail de Acesso</label>
                <input type="email" value={email} disabled style={{ width: '100%', padding: '10px', borderRadius: '8px', border: '1px solid #e2e8f0', background: '#f8fafc', color: '#a0aec0', cursor: 'not-allowed' }} />
              </div>

              <div className="login-field-group" style={{ marginBottom: '15px' }}>
                <label style={{ fontWeight: 'bold', display: 'block', marginBottom: '5px' }}>Nome Completo</label>
                <input type="text" value={fullName} onChange={(e) => setFullName(e.target.value)} style={{ width: '100%', padding: '10px', borderRadius: '8px', border: '1px solid #e2e8f0' }} required />
              </div>

              <div className="login-field-group" style={{ marginBottom: '20px' }}>
                <label style={{ fontWeight: 'bold', display: 'block', marginBottom: '5px' }}>Setor ou Curso</label>
                <input type="text" value={department} onChange={(e) => setDepartment(e.target.value)} style={{ width: '100%', padding: '10px', borderRadius: '8px', border: '1px solid #e2e8f0' }} required />
              </div>

              <button type="submit" className="login-btn" disabled={loadingProfile} style={{ width: '100%', padding: '12px' }}>
                {loadingProfile ? 'Salvando...' : 'Salvar Perfil'}
              </button>
            </form>
          </section>

          <section style={{ background: '#fff', padding: '2rem', borderRadius: '12px', boxShadow: '0 4px 6px rgba(0,0,0,0.05)' }}>
            <h3 style={{ marginBottom: '20px', color: '#2d3748', borderBottom: '2px solid #e2e8f0', paddingBottom: '10px' }}>
              <i className="fas fa-lock" style={{ marginRight: '8px', color: '#1a73e8' }}></i> Segurança
            </h3>
            
            <form onSubmit={handleUpdatePassword}>
              <div className="login-field-group" style={{ marginBottom: '15px' }}>
                <label style={{ fontWeight: 'bold', display: 'block', marginBottom: '5px' }}>Nova Senha</label>
                <input type="password" placeholder="Digite a nova senha" value={newPassword} onChange={(e) => setNewPassword(e.target.value)} style={{ width: '100%', padding: '10px', borderRadius: '8px', border: '1px solid #e2e8f0' }} required />
              </div>

              <div className="login-field-group" style={{ marginBottom: '20px' }}>
                <label style={{ fontWeight: 'bold', display: 'block', marginBottom: '5px' }}>Confirmar Nova Senha</label>
                <input type="password" placeholder="Repita a nova senha" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} style={{ width: '100%', padding: '10px', borderRadius: '8px', border: '1px solid #e2e8f0' }} required />
              </div>

              <button type="submit" className="login-btn" disabled={loadingPassword} style={{ width: '100%', padding: '12px', background: '#2d3748' }}>
                {loadingPassword ? 'Alterando...' : 'Alterar Senha'}
              </button>
            </form>
          </section>

        </div>
      </main>
    </div>
  );
}

export default Configuracoes;
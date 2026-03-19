import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { toast } from 'react-toastify';
import { supabase } from '../supabaseClient'; 

function Login() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    const email = e.target.email.value;
    const password = e.target.password.value;
    
    const { data, error } = await supabase.auth.signInWithPassword({
      email,
      password,
    });

    if (error) {
      toast.error('❌ E-mail ou senha incorretos!');
    } else {
      const userName = data.user.user_metadata?.full_name || email.split('@')[0];
      localStorage.setItem('uniforUser', userName);
      toast.success(`Bem-vindo(a), ${userName}!`);
      navigate('/dashboard');
    }
    
    setLoading(false);
  };

  return (
    <div className="login-container">
      <form className="login-card" onSubmit={handleSubmit}>
        <h2>Unifor Cloud</h2>
        <p>Faça login para continuar</p>

        <div className="login-field-group">
          <label>E-mail</label>
          <input name="email" type="email" placeholder="seuemail@unifor.br" required />
        </div>

        <div className="login-field-group">
          <label>Senha</label>
          <input name="password" type="password" placeholder="••••" required />
        </div>

        <button type="submit" className="login-btn" disabled={loading}>
          {loading ? 'Autenticando...' : 'Entrar'}
        </button>

        <p style={{ marginTop: '20px', fontSize: '0.9rem', color: '#718096' }}>
          Não tem uma conta?{' '}

          <Link to="/cadastro" style={{ color: '#1a73e8', fontWeight: 'bold', textDecoration: 'none' }}>
            Cadastre-se
          </Link>
        </p>
      </form>
    </div>
  );
}

export default Login;
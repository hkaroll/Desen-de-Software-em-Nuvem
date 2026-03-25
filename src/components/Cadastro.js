import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { toast } from 'react-toastify';
import { supabase } from '../supabaseClient'; 

function Cadastro() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const fullName = e.target.fullName.value;
    const department = e.target.department.value;
    const email = e.target.email.value;
    const password = e.target.password.value;
    const confirmPassword = e.target.confirmPassword.value;

    if (password !== confirmPassword) {
      toast.warning('⚠️ As senhas digitadas não coincidem!');
      return; 
    }

    if (password.length < 6) {
      toast.warning('⚠️ A senha deve ter no mínimo 6 caracteres.');
      return;
    }

    setLoading(true);

    const { error } = await supabase.auth.signUp({
      email,
      password,
      options: {
        data: {
          full_name: fullName,
          department: department,
        }
      }
    });

    if (error) {
      console.error('Erro real do Supabase:', error.message); 
      toast.error('❌ Erro ao criar conta. Verifique os dados e tente novamente.');
    } else {
      toast.success('✅ Conta criada com sucesso! Faça o login agora.');
      navigate('/'); 
    }
    
    setLoading(false);
  };

  return (
    <div className="login-container">
      <form className="login-card" style={{ width: '550px' }} onSubmit={handleSubmit}>
        <h2>Criar Conta</h2>
        <p>Preencha seus dados para acessar o sistema</p>
        
        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px' }}>
          <div className="login-field-group">
            <label>Nome Completo</label>
            <input name="fullName" type="text" placeholder="Ex: Ana Silva" required />
          </div>
          
          <div className="login-field-group">
            <label>Setor ou Curso</label>
            <input name="department" type="text" placeholder="Ex: TI / Engenharia" required />
          </div>
        </div>

        <div className="login-field-group">
          <label>E-mail</label>
          <input name="email" type="email" placeholder="seuemail@unifor.br" required />
        </div>

        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px' }}>
          <div className="login-field-group">
            <label>Senha</label>
            <input name="password" type="password" placeholder="Mínimo de 6" required />
          </div>

          <div className="login-field-group">
            <label>Confirmar Senha</label>
            <input name="confirmPassword" type="password" placeholder="Repita a senha" required />
          </div>
        </div>

        <button type="submit" className="login-btn" disabled={loading} style={{ marginTop: '10px' }}>
          {loading ? 'Salvando...' : 'Cadastrar'}
        </button>

        <p style={{ marginTop: '20px', fontSize: '0.9rem', color: '#718096' }}>
          Já tem uma conta?{' '}
          <Link to="/" style={{ color: '#1a73e8', fontWeight: 'bold', textDecoration: 'none' }}>
            Faça Login
          </Link>
        </p>
      </form>
    </div>
  );
}

export default Cadastro;
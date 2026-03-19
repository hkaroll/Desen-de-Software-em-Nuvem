import React, { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import Sidebar from './Sidebar';
import { toast } from 'react-toastify';
import { supabase } from '../supabaseClient';

function Dashboard() {
  const navigate = useNavigate();
  const [user, setUser] = useState("");
  const [chamados, setChamados] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState("");
  const [filterStatus, setFilterStatus] = useState("TODOS");
  
  const [windowWidth, setWindowWidth] = useState(window.innerWidth);
  const isMobile = windowWidth < 768;

  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showViewModal, setShowViewModal] = useState(false);
  const [selectedChamado, setSelectedChamado] = useState(null);

  const [titulo, setTitulo] = useState("");
  const [descricao, setDescricao] = useState("");
  const [prioridade, setPrioridade] = useState("MEDIA");

  useEffect(() => {
    const handleResize = () => setWindowWidth(window.innerWidth);
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  const fetchChamados = useCallback(async () => {
    setLoading(true);
    const { data, error } = await supabase
      .from('chamados')
      .select('*')
      .order('data_abertura', { ascending: false }); 
    if (!error) setChamados(data || []);
    setLoading(false);
  }, []);

  useEffect(() => {
    const savedUser = localStorage.getItem('uniforUser');
    if (!savedUser) { navigate('/'); return; }
    setUser(savedUser);
    fetchChamados();
  }, [navigate, fetchChamados]);

  const handleCreate = async (e) => {
    e.preventDefault();
    const { error } = await supabase.from('chamados').insert([{ 
      titulo, descricao, prioridade, status: 'ABERTO', solicitante_id: 1, data_abertura: new Date().toISOString() 
    }]);

    if (error) {
      toast.error(`Erro: ${error.message}`);
    } else {
      toast.success('✅ Chamado Aberto com sucesso!');
      setShowCreateModal(false);
      setTitulo(""); setDescricao(""); setPrioridade("MEDIA");
      fetchChamados();
    }
  };

  const handleViewDetails = (chamado) => {
    setSelectedChamado(chamado);
    setShowViewModal(true);
  };

  const handleUpdateStatus = async (e, id, statusAtual) => {
    e.stopPropagation();
    let novoStatus = statusAtual === 'ABERTO' ? 'EM_ATENDIMENTO' : statusAtual === 'EM_ATENDIMENTO' ? 'RESOLVIDO' : 'FECHADO';
    const { error } = await supabase.from('chamados').update({ status: novoStatus }).eq('id', id);
    if (!error) {
      fetchChamados();
    } else {
      toast.error(`Erro ao atualizar: ${error.message}`);
    }
  };

  const handleDelete = async (e, id) => {
    e.stopPropagation(); 
    if (window.confirm("Deseja realmente apagar este chamado permanentemente?")) {
      setLoading(true); 
      try {
        const { error } = await supabase.from('chamados').delete().eq('id', id);
        if (error) {
          toast.error(`Erro ao excluir: ${error.message}`);
        } else {
          toast.success('🗑️ Chamado removido com sucesso!');
          fetchChamados(); 
        }
      } catch (err) {
        toast.error("Ocorreu um erro inesperado.");
      } finally {
        setLoading(false); 
      }
    }
  };

  const filteredChamados = chamados.filter(c => 
    (c.titulo.toLowerCase().includes(searchTerm.toLowerCase()) || c.id.toString().includes(searchTerm)) &&
    (filterStatus === "TODOS" || c.status === filterStatus)
  );

  return (
    <div style={{ display: 'flex', flexDirection: isMobile ? 'column' : 'row', minHeight: '100vh', backgroundColor: '#f8fafc' }}>
      
      <div style={{ width: isMobile ? '100%' : '280px', minWidth: isMobile ? '100%' : '280px', flexShrink: 0, backgroundColor: '#1a202c' }}>
        <Sidebar user={user} onLogout={() => { localStorage.removeItem('uniforUser'); navigate('/'); }} />
      </div>

      <main style={{ flex: 1, padding: isMobile ? '20px' : '40px', width: '100%', boxSizing: 'border-box' }}>
        
        <header style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '30px', flexWrap: 'wrap', gap: '15px' }}>
          <div>
            <h1 style={{ fontSize: isMobile ? '1.5rem' : '2rem', margin: 0, color: '#1a202c' }}>Painel de Chamados</h1>
            <p style={{ color: '#718096', margin: 0 }}>Gestão Unifor Cloud</p>
          </div>
          <button className="login-btn" style={{ width: 'auto', padding: '12px 24px', margin: 0 }} onClick={() => setShowCreateModal(true)}>
            + Novo Chamado
          </button>
        </header>

        <div style={{ display: 'grid', gridTemplateColumns: isMobile ? '1fr 1fr' : 'repeat(4, 1fr)', gap: '15px', marginBottom: '25px' }}>
          {[
            { label: "ABERTOS", value: "ABERTO", color: "#718096" },
            { label: "PROCESSO", value: "EM_ATENDIMENTO", color: "#3182ce" },
            { label: "FINALIZADOS", value: "RESOLVIDO", color: "#48bb78" },
            { label: "TOTAL", value: "TODOS", color: "#2d3748" }
          ].map(card => (
            <div key={card.value} onClick={() => setFilterStatus(card.value)} style={{ cursor: 'pointer', background: '#fff', padding: '20px', borderRadius: '15px', borderLeft: filterStatus === card.value ? `6px solid ${card.color}` : '6px solid #edf2f7', boxShadow: '0 2px 10px rgba(0,0,0,0.03)' }}>
              <span style={{ fontSize: '0.7rem', color: card.color, fontWeight: 'bold' }}>{card.label}</span>
              <p style={{ fontSize: '1.6rem', fontWeight: 'bold', margin: '5px 0 0' }}>
                {card.value === "TODOS" ? chamados.length : chamados.filter(c => c.status === card.value).length}
              </p>
            </div>
          ))}
        </div>

        <input type="text" placeholder="🔍 Buscar chamado..." value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)} style={{ width: '100%', padding: '12px 20px', borderRadius: '12px', border: '1px solid #e2e8f0', marginBottom: '25px', outline: 'none' }} />

        <section style={{ background: '#fff', borderRadius: '16px', boxShadow: '0 4px 20px rgba(0,0,0,0.05)', overflow: 'hidden' }}>
          <div style={{ overflowX: 'auto' }}>
            <table style={{ width: '100%', minWidth: '750px', borderCollapse: 'collapse', textAlign: 'left' }}>
              <thead style={{ background: '#fcfcfc' }}>
                <tr>
                  <th style={{ padding: '18px' }}>ID</th>
                  <th style={{ padding: '18px' }}>Solicitação</th>
                  <th style={{ padding: '18px' }}>Urgência</th>
                  <th style={{ padding: '18px' }}>Ações</th>
                </tr>
              </thead>
              <tbody>
              
                {loading ? (
                  <tr>
                    <td colSpan="4" style={{ padding: '30px', textAlign: 'center', color: '#718096' }}>
                      Sincronizando dados...
                    </td>
                  </tr>
                ) : filteredChamados.length === 0 ? (
                  <tr>
                    <td colSpan="4" style={{ padding: '30px', textAlign: 'center', color: '#718096' }}>
                      Nenhum chamado encontrado.
                    </td>
                  </tr>
                ) : (
                  filteredChamados.map((c) => (
                    <tr key={c.id} style={{ borderBottom: '1px solid #edf2f7', backgroundColor: c.prioridade === 'ALTA' ? '#fff5f5' : 'transparent' }}>
                      <td onClick={() => handleViewDetails(c)} style={{ padding: '18px', fontWeight: 'bold', color: '#1a73e8', cursor: 'pointer' }}>#{c.id}</td>
                      <td onClick={() => handleViewDetails(c)} style={{ padding: '18px', cursor: 'pointer' }}>
                        <div style={{ fontWeight: '600' }}>{c.titulo}</div>
                        <div style={{ fontSize: '0.75rem', color: '#a0aec0' }}>Aberto em {new Date(c.data_abertura).toLocaleDateString('pt-BR')}</div>
                      </td>
                      <td style={{ padding: '18px' }}>
                        <span style={{ background: c.prioridade === 'ALTA' ? '#e53e3e' : c.prioridade === 'MEDIA' ? '#ecc94b' : '#48bb78', color: '#fff', padding: '5px 12px', borderRadius: '8px', fontSize: '0.7rem', fontWeight: 'bold' }}>
                          {c.prioridade}
                        </span>
                      </td>
                      <td style={{ padding: '18px' }}>
                        <div style={{ display: 'flex', gap: '8px' }}>
                          {c.status !== 'FECHADO' && (
                            <button onClick={(e) => handleUpdateStatus(e, c.id, c.status)} style={{ padding: '8px 16px', background: '#3182ce', color: 'white', border: 'none', borderRadius: '8px', fontSize: '0.75rem', cursor: 'pointer' }}>Avançar</button>
                          )}
                          <button onClick={(e) => handleDelete(e, c.id)} style={{ padding: '8px 12px', background: 'none', border: '1px solid #feb2b2', color: '#e53e3e', borderRadius: '8px', fontSize: '0.75rem', cursor: 'pointer' }}>Excluir</button>
                        </div>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </section>
      </main>

      {showViewModal && selectedChamado && (
        <div style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.6)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 2000, padding: '20px' }} onClick={() => setShowViewModal(false)}>
          <div style={{ background: 'white', padding: '2rem', borderRadius: '20px', width: '100%', maxWidth: '450px' }} onClick={e => e.stopPropagation()}>
            <h2>#{selectedChamado.id} - {selectedChamado.titulo}</h2>
            <p style={{ background: '#f8fafc', padding: '20px', borderRadius: '12px', minHeight: '100px', marginBottom: '20px' }}>{selectedChamado.descricao}</p>
            <button onClick={() => setShowViewModal(false)} style={{ width: '100%', padding: '12px', background: '#1a73e8', color: 'white', border: 'none', borderRadius: '10px', fontWeight: 'bold' }}>Fechar</button>
          </div>
        </div>
      )}

      {showCreateModal && (
        <div style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.6)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 2000, padding: '20px' }}>
          <div style={{ background: 'white', padding: '2rem', borderRadius: '20px', width: '100%', maxWidth: '400px' }}>
            <h3 style={{ marginBottom: '20px' }}>Nova Solicitação</h3>
            <form onSubmit={handleCreate}>
              <input type="text" placeholder="Título" value={titulo} onChange={e => setTitulo(e.target.value)} required style={{ width: '100%', padding: '12px', marginBottom: '15px', borderRadius: '8px', border: '1px solid #ddd' }} />
              <textarea placeholder="Descrição" value={descricao} onChange={e => setDescricao(e.target.value)} required style={{ width: '100%', padding: '12px', marginBottom: '15px', borderRadius: '8px', border: '1px solid #ddd', height: '100px', resize: 'none' }}></textarea>
              <select value={prioridade} onChange={e => setPrioridade(e.target.value)} style={{ width: '100%', padding: '12px', marginBottom: '25px', borderRadius: '8px' }}>
                <option value="BAIXA">Baixa</option>
                <option value="MEDIA">Média</option>
                <option value="ALTA">Alta</option>
              </select>
              <div style={{ display: 'flex', gap: '15px' }}>
                <button type="button" onClick={() => setShowCreateModal(false)} style={{ flex: 1, padding: '12px', borderRadius: '8px', border: 'none', cursor: 'pointer' }}>Cancelar</button>
                <button type="submit" style={{ flex: 1, padding: '12px', background: '#1a73e8', color: 'white', border: 'none', borderRadius: '8px', fontWeight: 'bold', cursor: 'pointer' }}>Salvar</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

export default Dashboard;
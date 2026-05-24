import React, { useEffect, useState } from 'react';
import { createRoot } from 'react-dom/client';
import axios from 'axios';
import './styles.css';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

function App() {
  const [metadata, setMetadata] = useState(null);
  const [executions, setExecutions] = useState([]);
  const [form, setForm] = useState({ suite: 'ai', tags: '@ai-validation', environment: 'local', executionMode: 'mock' });
  const [message, setMessage] = useState('');

  useEffect(() => {
    loadMetadata();
    loadExecutions();
  }, []);

  const loadMetadata = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/api/framework/metadata`);
      setMetadata(response.data);
    } catch (error) {
      setMessage('Backend metadata is not available yet. Start Spring Boot on port 8080.');
    }
  };

  const loadExecutions = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/api/executions`);
      setExecutions(response.data);
    } catch (error) {
      setExecutions([]);
    }
  };

  const startExecution = async () => {
    try {
      const response = await axios.post(`${API_BASE_URL}/api/executions`, form);
      setMessage(`Execution queued: ${response.data.executionId}`);
      await loadExecutions();
    } catch (error) {
      setMessage('Unable to start execution. Check backend status.');
    }
  };

  const updateForm = (field, value) => {
    setForm((current) => ({ ...current, [field]: value }));
  };

  return (
    <main className="dashboard">
      <section className="hero">
        <div>
          <p className="eyebrow">TestMate AI</p>
          <h1>AI/API/UI/Mobile Test Execution Dashboard</h1>
          <p className="subtitle">Phase 2 starter dashboard for triggering test runs and viewing execution history.</p>
        </div>
        <span className="status-pill">Spring Boot + React</span>
      </section>

      {message && <div className="notice">{message}</div>}

      <section className="grid">
        <div className="card">
          <h2>Framework Metadata</h2>
          {metadata ? (
            <div className="metadata">
              <p><strong>Phase:</strong> {metadata.phase}</p>
              <p><strong>Backend:</strong> {metadata.backend}</p>
              <p><strong>Modules:</strong> {metadata.frameworkModules?.join(', ')}</p>
              <p><strong>Profiles:</strong> {metadata.supportedProfiles?.join(', ')}</p>
            </div>
          ) : (
            <p>Start the backend to view framework metadata.</p>
          )}
        </div>

        <div className="card">
          <h2>Start Test Execution</h2>
          <label>Suite</label>
          <select value={form.suite} onChange={(event) => updateForm('suite', event.target.value)}>
            <option value="ai">AI</option>
            <option value="api">API</option>
            <option value="web">Web</option>
            <option value="mobile">Mobile</option>
            <option value="all">All</option>
          </select>

          <label>Tags</label>
          <input value={form.tags} onChange={(event) => updateForm('tags', event.target.value)} />

          <label>Environment</label>
          <input value={form.environment} onChange={(event) => updateForm('environment', event.target.value)} />

          <label>Execution Mode</label>
          <select value={form.executionMode} onChange={(event) => updateForm('executionMode', event.target.value)}>
            <option value="mock">Mock</option>
            <option value="local">Local</option>
            <option value="sauce">Sauce Labs</option>
          </select>

          <button onClick={startExecution}>Start Execution</button>
        </div>
      </section>

      <section className="card full-width">
        <div className="section-header">
          <h2>Execution History</h2>
          <button className="secondary" onClick={loadExecutions}>Refresh</button>
        </div>
        <table>
          <thead>
            <tr>
              <th>Execution ID</th>
              <th>Status</th>
              <th>Suite</th>
              <th>Tags</th>
              <th>Environment</th>
            </tr>
          </thead>
          <tbody>
            {executions.length === 0 ? (
              <tr><td colSpan="5">No executions yet.</td></tr>
            ) : executions.map((execution) => (
              <tr key={execution.executionId}>
                <td>{execution.executionId}</td>
                <td>{execution.status}</td>
                <td>{execution.suite}</td>
                <td>{execution.tags}</td>
                <td>{execution.environment}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>
    </main>
  );
}

createRoot(document.getElementById('root')).render(<App />);

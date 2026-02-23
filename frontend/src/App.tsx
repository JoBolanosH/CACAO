import React, { useState } from 'react'
import axios from 'axios'
import './App.css'

interface LoginRequest {
  username: string
  password: string
}

interface LoginResponse {
  token: string
  message: string
}

function App() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [token, setToken] = useState<string | null>(null)
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  const apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:8080'

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault()
    setLoading(true)
    setError(null)

    try {
      const response = await axios.post<LoginResponse>(`${apiUrl}/api/auth/login`, {
        username,
        password,
      })

      setToken(response.data.token)
      setUsername('')
      setPassword('')
    } catch (err) {
      if (axios.isAxiosError(err)) {
        setError(err.response?.data?.message || 'Error en la autenticación')
      } else {
        setError('Error desconocido')
      }
    } finally {
      setLoading(false)
    }
  }

  const handleLogout = () => {
    setToken(null)
    setError(null)
  }

  return (
    <div className="container">
      <div className="card">
        <h1>CACAO</h1>
        <p className="subtitle">Microservicio de Autenticación y Autorización</p>

        {token ? (
          <div className="logged-in">
            <div className="token-box">
              <h2>Autenticado</h2>
              <p className="token-label">Token:</p>
              <code className="token">{token.substring(0, 50)}...</code>
              <button onClick={handleLogout} className="btn btn-logout">
                Cerrar Sesión
              </button>
            </div>
          </div>
        ) : (
          <form onSubmit={handleLogin} className="login-form">
            <div className="form-group">
              <label htmlFor="username">Usuario</label>
              <input
                id="username"
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholder="Ingrese su usuario"
                disabled={loading}
              />
            </div>

            <div className="form-group">
              <label htmlFor="password">Contraseña</label>
              <input
                id="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Ingrese su contraseña"
                disabled={loading}
              />
            </div>

            {error && <div className="error-message">{error}</div>}

            <button type="submit" className="btn btn-login" disabled={loading || !username || !password}>
              {loading ? 'Autenticando...' : 'Iniciar Sesión'}
            </button>
          </form>
        )}

        <div className="info-box">
          <h3>Credenciales de Prueba</h3>
          <p>Usuario: <code>admin</code></p>
          <p>Contraseña: <code>password123</code></p>
        </div>
      </div>
    </div>
  )
}

export default App

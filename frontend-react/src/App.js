import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [parkingLots, setParkingLots] = useState([]);
  const [loading, setLoading] = useState(true);
  const [user, setUser] = useState(null);
  const [loginForm, setLoginForm] = useState({ username: '', password: '' });

  // Check if user is already logged in
  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    if (token) {
      // In a real app, you'd decode the JWT to get user info
      fetchParkingLots();
    }
  }, []);

  const fetchParkingLots = async () => {
    try {
      const response = await axios.get('http://localhost:8082/api/parking-lots');
      setParkingLots(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching parking lots:', error);
      setLoading(false);
    }
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8081/auth/login', {
        username: loginForm.username,
        password: loginForm.password
      });
      
      const { accessToken, refreshToken, role, username } = response.data;
      localStorage.setItem('accessToken', accessToken);
      localStorage.setItem('refreshToken', refreshToken);
      setUser({ username, role });
      fetchParkingLots();
    } catch (error) {
      console.error('Login failed:', error);
      alert('Login failed. Please check your credentials.');
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    setUser(null);
    setParkingLots([]);
  };

  const getSpotTypeColor = (type) => {
    switch (type) {
      case 'ELECTRIC': return '#4CAF50';
      case 'HANDICAP': return '#FF9800';
      case 'COMPACT': return '#2196F3';
      case 'RESERVED': return '#F44336';
      default: return '#757575';
    }
  };

  if (!user) {
    return (
      <div className="login-container">
        <div className="login-form">
          <h1>🚗 Campus Parking System</h1>
          <h2>Login</h2>
          <form onSubmit={handleLogin}>
            <div className="form-group">
              <label>Username:</label>
              <input
                type="text"
                value={loginForm.username}
                onChange={(e) => setLoginForm({...loginForm, username: e.target.value})}
                placeholder="Enter username"
                required
              />
            </div>
            <div className="form-group">
              <label>Password:</label>
              <input
                type="password"
                value={loginForm.password}
                onChange={(e) => setLoginForm({...loginForm, password: e.target.value})}
                placeholder="Enter password"
                required
              />
            </div>
            <button type="submit" className="login-btn">Login</button>
          </form>
          <div className="demo-accounts">
            <h3>Demo Accounts:</h3>
            <p><strong>Student:</strong> student1 / password123</p>
            <p><strong>Faculty:</strong> faculty1 / password123</p>
            <p><strong>Admin:</strong> admin / admin123</p>
          </div>
        </div>
      </div>
    );
  }

  if (loading) {
    return (
      <div className="app-container">
        <div className="loading">Loading parking lots...</div>
      </div>
    );
  }

  return (
    <div className="app-container">
      <header className="app-header">
        <h1>🚗 Campus Parking System</h1>
        <div className="user-info">
          <span>Welcome, {user.username} ({user.role})</span>
          <button onClick={handleLogout} className="logout-btn">Logout</button>
        </div>
      </header>

      <main className="main-content">
        <h2>Available Parking Lots</h2>
        
        {parkingLots.length === 0 ? (
          <div className="no-data">No parking lots available</div>
        ) : (
          <div className="parking-lots-grid">
            {parkingLots.map(lot => (
              <div key={lot.id} className="parking-lot-card">
                <div className="lot-header">
                  <h3>{lot.location} - {lot.lotNumber}</h3>
                  <span className="lot-type">{lot.type.replace('_', ' ')}</span>
                </div>
                
                <div className="lot-info">
                  <p><strong>Total Spots:</strong> {lot.totalSpots}</p>
                  <p><strong>Facilities:</strong> {lot.facilities?.join(', ') || 'None'}</p>
                </div>

                <div className="parking-spots">
                  <h4>Parking Spots:</h4>
                  <div className="spots-grid">
                    {lot.parkingSpots && lot.parkingSpots.map(spot => (
                      <div 
                        key={spot.id} 
                        className={`spot ${spot.available ? 'available' : 'occupied'}`}
                        style={{ borderLeftColor: getSpotTypeColor(spot.type) }}
                      >
                        <div className="spot-number">{spot.spotNumber}</div>
                        <div className="spot-type">{spot.type}</div>
                        <div className="spot-status">
                          {spot.available ? 'Available' : 'Occupied'}
                        </div>
                        <div className="spot-position">{spot.positionDescription}</div>
                      </div>
                    ))}
                  </div>
                </div>

                {user.role === 'STUDENT' && (
                  <button className="reserve-btn">Reserve Spot</button>
                )}
              </div>
            ))}
          </div>
        )}
      </main>

      <footer className="app-footer">
        <p>Distributed Parking System - P4 Fault Tolerance Demo</p>
      </footer>
    </div>
  );
}

export default App;
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { warningAPI } from '../services/api';

export default function WarningBadge() {
  const [high, setHigh] = useState(0);
  const [total, setTotal] = useState(0);
  const navigate = useNavigate();

  useEffect(() => {
    const fetch = async () => {
      try {
        const data = await warningAPI.getAll();
        setTotal(data.length);
        setHigh(data.filter(w => w.severity === 'HIGH').length);
      } catch {
        // ignore
      }
    };
    fetch();
    const interval = setInterval(fetch, 30000);
    return () => clearInterval(interval);
  }, []);

  if (total === 0) return null;

  return (
    <button
      className="warning-badge"
      onClick={() => navigate('/warnings')}
      title={`${total} warning${total !== 1 ? 's' : ''} (${high} high)`}
    >
      <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
        <path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z" />
        <line x1="12" y1="9" x2="12" y2="13" />
        <line x1="12" y1="17" x2="12.01" y2="17" />
      </svg>
      <span className="warning-badge-count">{total}</span>
      {high > 0 && <span className="warning-badge-high">{high}</span>}
    </button>
  );
}

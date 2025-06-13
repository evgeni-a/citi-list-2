import React, { createContext, useContext, useState, useEffect } from 'react';
import type { ReactNode } from 'react';

interface AuthState {
  accessToken: string;
  tokenType: string;
  expiresIn: string;
  refreshToken: string;
  scope: string;
}

interface AuthContextType {
  authState: AuthState;
  authorized: boolean;
  hasEditRole: boolean;
  getAuthorizationHeader: () => string | null;
  login: (accessToken: string, tokenType: string, expiresIn: string, refreshToken: string, scope: string) => void;
  logout: () => void;
}

const initialAuthState: AuthState = {
  accessToken: '',
  tokenType: '',
  expiresIn: '',
  refreshToken: '',
  scope: '',
};

const AUTH_STORAGE_KEY = 'auth_state';

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [authState, setAuthState] = useState<AuthState>(initialAuthState);

  // Load auth state from localStorage on mount
  useEffect(() => {
    const storedAuth = localStorage.getItem(AUTH_STORAGE_KEY);
    if (storedAuth) {
      try {
        const parsedAuth = JSON.parse(storedAuth) as AuthState;
        
        // Check if token is still valid
        if (parsedAuth.expiresIn) {
          const expirationTime = parseInt(parsedAuth.expiresIn, 10) * 1000; // Convert to milliseconds
          const now = Date.now();
          
          if (now < expirationTime) {
            setAuthState(parsedAuth);
          } else {
            // Token expired, clear storage
            localStorage.removeItem(AUTH_STORAGE_KEY);
          }
        }
      } catch (error) {
        console.error('Error parsing stored auth state:', error);
        localStorage.removeItem(AUTH_STORAGE_KEY);
      }
    }
  }, []);

  // Save auth state to localStorage whenever it changes
  useEffect(() => {
    if (authState.accessToken) {
      localStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(authState));
    } else {
      localStorage.removeItem(AUTH_STORAGE_KEY);
    }
  }, [authState]);

  // Check if user is authorized
  const authorized = authState.accessToken !== '';

  // Check if user has edit role
  const hasEditRole = authState.scope ? authState.scope.includes('ROLE_ALLOW_EDIT') : false;

  // Get authorization header
  const getAuthorizationHeader = (): string | null => {
    if (authState.accessToken) {
      return `${authState.tokenType} ${authState.accessToken}`;
    }
    return null;
  };

  // Login function
  const login = (accessToken: string, tokenType: string, expiresIn: string, refreshToken: string, scope: string): void => {
    // Calculate actual expiration time
    const expirationTime = Date.now() + (parseInt(expiresIn, 10) * 1000);
    
    setAuthState({
      accessToken,
      tokenType,
      expiresIn: expirationTime.toString(),
      refreshToken,
      scope,
    });
  };

  // Logout function
  const logout = (): void => {
    setAuthState(initialAuthState);
    localStorage.removeItem(AUTH_STORAGE_KEY);
  };

  return (
    <AuthContext.Provider
      value={{
        authState,
        authorized,
        hasEditRole,
        getAuthorizationHeader,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

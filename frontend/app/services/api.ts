import axios from 'axios';

// Use environment variable with fallback
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8081/api/v1';

// Create an axios instance with the base URL
const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000, // 10 seconds timeout
});

// Add request interceptor for logging (in development)
api.interceptors.request.use(
  (config) => {
    if (import.meta.env.DEV) {
      console.log('API Request:', config.method?.toUpperCase(), config.url);
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Add response interceptor for error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (import.meta.env.DEV) {
      console.error('API Error:', error.response?.status, error.response?.data);
    }
    
    // Handle common error scenarios
    if (error.response?.status === 401) {
      // Handle unauthorized access
      window.location.href = '/login';
    }
    
    return Promise.reject(error);
  }
);

// Types for API responses
export interface City {
  id: string;
  name: string;
  photo: string;
}

export interface CitiesResponse {
  cities: City[];
  totalElements: number;
  totalPages: number;
  number: number;
  numberOfElements: number;
  size: number;
}

export interface AuthResponse {
  access_token: string;
  token_type: string;
  expires_in: string;
  refresh_token: string;
  scope: string;
}

// API functions
export const fetchCities = async (page: number, size: number, search?: string): Promise<CitiesResponse> => {
  const params: Record<string, string> = {
    page: page.toString(),
    size: size.toString(),
  };
  
  if (search) {
    params.search = search;
  }
  
  const response = await api.get<CitiesResponse>('/cities', { params });
  return response.data;
};

export const fetchCity = async (id: string): Promise<City> => {
  const response = await api.get<City>(`/cities/${id}`);
  return response.data;
};

export const updateCity = async (id: string, name: string, photo: string, authHeader: string): Promise<City> => {
  const response = await api.put<City>(
    `/cities/${id}`,
    { name, photo },
    { headers: { Authorization: authHeader } }
  );
  return response.data;
};

export const login = async (username: string, password: string): Promise<AuthResponse> => {
  const response = await api.post<AuthResponse>('/auth/login', { username, password });
  return response.data;
};

export default api;
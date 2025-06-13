import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router';
import { fetchCity, updateCity } from '../services/api';
import { useAuth } from '../context/AuthContext';

export default function CityEdit() {
  const { id } = useParams<{ id: string }>();
  const { getAuthorizationHeader } = useAuth();
  
  const [cityId, setCityId] = useState('');
  const [name, setName] = useState('');
  const [photo, setPhoto] = useState('');
  const [loading, setLoading] = useState(true);
  const [updating, setUpdating] = useState(false);
  const [error, setError] = useState('');
  const [updateSuccess, setUpdateSuccess] = useState(false);

  // Fetch city data when component mounts
  useEffect(() => {
    if (id) {
      getCityData(id);
    }
  }, [id]);

  const getCityData = async (cityId: string) => {
    setLoading(true);
    try {
      const city = await fetchCity(cityId);
      setCityId(city.id);
      setName(city.name);
      setPhoto(city.photo);
    } catch (error) {
      console.error('Error fetching city:', error);
      setError('Failed to load city data');
    } finally {
      setLoading(false);
    }
  };

  const handleUpdate = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setUpdateSuccess(false);
    setUpdating(true);

    const authHeader = getAuthorizationHeader();
    if (!authHeader) {
      setError('You must be logged in to update a city');
      setUpdating(false);
      return;
    }

    try {
      await updateCity(cityId, name, photo, authHeader);
      setUpdateSuccess(true);
    } catch (error) {
      console.error('Error updating city:', error);
      setError('Failed to update city');
    } finally {
      setUpdating(false);
    }
  };

  if (loading) {
    return (
      <div className="container mx-auto p-4 text-center">
        Loading city data...
      </div>
    );
  }

  return (
    <div className="container mx-auto p-4 max-w-md">
      <div className="bg-white p-6 rounded-lg shadow-md">
        <h2 className="text-2xl font-bold mb-6 text-center">Edit City</h2>
        
        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            {error}
          </div>
        )}
        
        {updateSuccess && (
          <div className="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4">
            City updated successfully!
          </div>
        )}
        
        <form onSubmit={handleUpdate}>
          <div className="mb-4">
            <label htmlFor="id" className="block text-gray-700 mb-2">
              ID
            </label>
            <input
              type="text"
              id="id"
              value={cityId}
              className="w-full p-2 border rounded-md bg-gray-100"
              disabled
            />
          </div>
          
          <div className="mb-4">
            <label htmlFor="name" className="block text-gray-700 mb-2">
              Name
            </label>
            <input
              type="text"
              id="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              className="w-full p-2 border rounded-md"
              required
            />
          </div>
          
          <div className="mb-6">
            <label htmlFor="photo" className="block text-gray-700 mb-2">
              Photo URL
            </label>
            <input
              type="text"
              id="photo"
              value={photo}
              onChange={(e) => setPhoto(e.target.value)}
              className="w-full p-2 border rounded-md"
              required
            />
          </div>
          
          <div className="flex space-x-4">
            <button
              type="submit"
              disabled={updating}
              className="flex-1 bg-blue-700 text-white py-2 px-4 rounded-md hover:bg-blue-800 disabled:opacity-50"
            >
              {updating ? 'Updating...' : 'Update'}
            </button>
            
            <Link to="/" className="flex-1">
              <button
                type="button"
                className="w-full bg-gray-300 text-gray-800 py-2 px-4 rounded-md hover:bg-gray-400"
              >
                Back
              </button>
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
}
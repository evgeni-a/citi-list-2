import React, { useState, useEffect, useCallback } from 'react';
import { Link } from 'react-router';
import { fetchCities, type City } from '~/services/api';
import { useAuth } from '~/context/AuthContext';

interface CityListProps {
  initialPageSize?: number;
}

const SEARCH_DEBOUNCE_MS = 300;
const ITEMS_PER_PAGE_OPTIONS = [4, 8, 12, 16] as const;

export default function CityList({ initialPageSize = 8 }: CityListProps): React.ReactElement {
  const { hasEditRole } = useAuth();
  const [cities, setCities] = useState<City[]>([]);
  const [search, setSearch] = useState('');
  const [lastSearchValue, setLastSearchValue] = useState('');
  const [totalElements, setTotalElements] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);
  const [numberOfElements, setNumberOfElements] = useState(0);
  const [pageSize, setPageSize] = useState(initialPageSize);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  // Reset pagination when search changes
  useEffect(() => {
    if (lastSearchValue !== search) {
      resetPagination();
    }
    setLastSearchValue(search);
  }, [search, lastSearchValue]);

  // Fetch cities when component mounts or when search, page, or size changes
  useEffect(() => {
    const timer = setTimeout(() => {
      if (search === '' || search.length >= 3) {
        void getCities();
      }
    }, SEARCH_DEBOUNCE_MS);

    return () => clearTimeout(timer);
  }, [search, currentPage, pageSize]);

  const resetPagination = useCallback((): void => {
    setTotalElements(0);
    setTotalPages(0);
    setCurrentPage(0);
    setNumberOfElements(0);
  }, []);

  const getCities = useCallback(async (): Promise<void> => {
    setLoading(true);
    setError(null);
    
    try {
      const response = await fetchCities(currentPage, pageSize, search);
      setCities(response.cities);
      setTotalElements(response.totalElements);
      setTotalPages(response.totalPages);
      setCurrentPage(response.number);
      setNumberOfElements(response.numberOfElements);
      setPageSize(response.size);
    } catch (error) {
      console.error('Error fetching cities:', error);
      setError('Failed to load cities. Please try again.');
      setCities([]);
    } finally {
      setLoading(false);
    }
  }, [currentPage, pageSize, search]);

  const nextPage = useCallback((): void => {
    if (currentPage < totalPages - 1) {
      setCurrentPage(currentPage + 1);
    }
  }, [currentPage, totalPages]);

  const previousPage = useCallback((): void => {
    if (currentPage > 0) {
      setCurrentPage(currentPage - 1);
    }
  }, [currentPage]);

  const updateItemsPerPage = useCallback((size: number): void => {
    setPageSize(size);
    setCurrentPage(0); // Reset to first page when changing page size
  }, []);

  const handleSearchChange = useCallback((e: React.ChangeEvent<HTMLInputElement>): void => {
    setSearch(e.target.value);
  }, []);

  if (error) {
    return (
      <div className="container mx-auto p-4">
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
          {error}
          <button 
            onClick={() => void getCities()} 
            className="ml-4 bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700"
          >
            Retry
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="container mx-auto p-4">
      {/* Search Bar */}
      <div className="bg-blue-800 p-4 mb-4 rounded-md">
        <input
          type="text"
          value={search}
          onChange={handleSearchChange}
          placeholder="Search cities..."
          className="w-full p-2 rounded-md text-black"
          aria-label="Search cities"
        />
      </div>

      {/* Pagination */}
      {!loading && cities.length > 0 && (
          <div className="mt-4 flex flex-col sm:flex-row items-center justify-center gap-4">
            <div className="flex items-center gap-2">
              <span className="text-gray-600">Items per page:</span>
              <select
                  value={pageSize}
                  onChange={(e) => updateItemsPerPage(Number(e.target.value))}
                  className="bg-blue-700 text-white border border-blue-800 rounded py-2 px-4 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500"
                  aria-label="Items per page"
              >
                {ITEMS_PER_PAGE_OPTIONS.map((size) => (
                    <option key={size} value={size}>
                      {size}
                    </option>
                ))}
              </select>
            </div>

            <span className="text-gray-600">
            Page {currentPage + 1} of {totalPages} ({totalElements} total)
          </span>

            <div className="flex items-center gap-2">
              <button
                  onClick={previousPage}
                  disabled={currentPage === 0}
                  className="bg-blue-700 text-white p-2 rounded-full disabled:opacity-50 disabled:cursor-not-allowed hover:bg-blue-800 focus:outline-none focus:ring-2 focus:ring-blue-500"
                  aria-label="Previous page"
              >
                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                  <path fillRule="evenodd" d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z" clipRule="evenodd" />
                </svg>
              </button>

              <button
                  onClick={nextPage}
                  disabled={currentPage >= totalPages - 1}
                  className="bg-blue-700 text-white p-2 rounded-full disabled:opacity-50 disabled:cursor-not-allowed hover:bg-blue-800 focus:outline-none focus:ring-2 focus:ring-blue-500"
                  aria-label="Next page"
              >
                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                  <path fillRule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clipRule="evenodd" />
                </svg>
              </button>
            </div>
          </div>
      )}

      {/* Loading State */}
      {loading && (
        <div className="flex justify-center items-center py-8">
          <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
          <span className="ml-2 text-gray-600">Loading cities...</span>
        </div>
      )}

      {/* City Grid */}
      {!loading && (
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
          {cities.map((city) => (
            <div key={city.id} className="border rounded-md overflow-hidden shadow-md">
              <div className="p-4 font-bold border-b">{city.name}</div>

              {hasEditRole && (
                <div className="p-2 border-b">
                  <Link 
                    to={`/edit/${city.id}`} 
                    className="text-blue-600 hover:underline focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
                    aria-label={`Edit ${city.name}`}
                  >
                    Edit
                  </Link>
                </div>
              )}

              <img
                src={city.photo}
                alt={`Photo of ${city.name}`}
                className="w-full aspect-square object-cover bg-white"
                loading="lazy"
              />
            </div>
          ))}
        </div>
      )}

      {/* Empty State */}
      {!loading && cities.length === 0 && !error && (
        <div className="text-center py-8 text-gray-600">
          {search ? `No cities found for "${search}"` : 'No cities available'}
        </div>
      )}


    </div>
  );
}

import { Link } from 'react-router';
import { useAuth } from '../context/AuthContext';

export default function Navigation() {
  const { authorized } = useAuth();

  return (
    <nav className="bg-green-500 p-4 text-white">
      <div className="container mx-auto flex justify-between items-center">
        <Link to="/" className="flex items-center">
          <div className="flex items-center">
            <h1>City List</h1>
          </div>
        </Link>

        <div>
          {!authorized && (
            <Link to="/login" className="flex items-center text-white hover:underline">
              <span className="mr-2">Login</span>
              <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                <path fillRule="evenodd" d="M10.293 5.293a1 1 0 011.414 0l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414-1.414L12.586 11H5a1 1 0 110-2h7.586l-2.293-2.293a1 1 0 010-1.414z" clipRule="evenodd" />
              </svg>
            </Link>
          )}
        </div>
      </div>
    </nav>
  );
}
# Frontend Application

A modern React application built with React Router v7, TypeScript, and Tailwind CSS.

## 🚀 Tech Stack

- **React 19** - UI Library
- **React Router v7** - Routing with SSR support
- **TypeScript** - Type safety
- **Tailwind CSS v4** - Styling
- **Vite** - Build tool
- **Axios** - HTTP client
- **ESLint + Prettier** - Code quality

## 📁 Project Structure

```
frontend/
├── app/
│   ├── components/          # Reusable UI components
│   ├── context/            # React Context providers
│   ├── hooks/              # Custom React hooks
│   ├── routes/             # Route components
│   ├── services/           # API services
│   └── root.tsx           # Root application component
├── public/                 # Static assets
├── .eslintrc.js           # ESLint configuration
├── .prettierrc            # Prettier configuration
├── tsconfig.json          # TypeScript configuration
├── vite.config.ts         # Vite configuration
└── package.json           # Dependencies and scripts
```

## 🛠️ Development

### Prerequisites

- Node.js 20+ 
- npm or yarn

### Getting Started

1. **Install dependencies:**
   ```bash
   npm install
   ```

2. **Set up environment variables:**
   ```bash
   cp env.example .env.local
   ```
   Edit `.env.local` with your configuration.

3. **Start development server:**
   ```bash
   npm run dev
   ```

4. **Run linting:**
   ```bash
   npm run lint
   ```

5. **Format code:**
   ```bash
   npm run format
   ```

6. **Type checking:**
   ```bash
   npm run typecheck
   ```

7. **Build for production:**
   ```bash
   npm run build
   ```

8. **Start production server:**
   ```bash
   npm start
   ```

## 📝 Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm start` - Start production server
- `npm run lint` - Run ESLint with auto-fix
- `npm run lint:check` - Check linting without fixing
- `npm run format` - Format code with Prettier
- `npm run format:check` - Check formatting
- `npm run typecheck` - Run TypeScript type checking
- `npm run ci` - Run all checks (used in CI)

## 🔧 Configuration

### Environment Variables

Create a `.env.local` file with:

```env
VITE_API_BASE_URL=http://localhost:8081/api/v1
VITE_APP_ENV=development
VITE_ENABLE_DEV_TOOLS=true
```

### Code Quality

- **ESLint**: Configured with TypeScript, React, and accessibility rules
- **Prettier**: Consistent code formatting
- **TypeScript**: Strict mode with additional checks for better code quality

## 🏗️ Architecture

### State Management
- **React Context** for global state (Authentication)
- **Local state** with `useState` for component-specific state
- **Custom hooks** for reusable stateful logic

### API Integration
- **Axios** with interceptors for request/response handling
- **Environment-based** API URLs
- **Error handling** and loading states
- **Type-safe** API responses

### Routing
- **React Router v7** with SSR support
- **File-based** routing in `app/routes/`
- **Nested layouts** and error boundaries

### Styling
- **Tailwind CSS v4** for utility-first styling
- **Responsive design** with mobile-first approach
- **Dark mode** support built-in

## 🐳 Docker

The application is containerized with multi-stage Docker build:

```bash
# Build image
docker build -t frontend .

# Run container
docker run -p 3000:3000 frontend
```

## 🚀 Deployment

1. **Build the application:**
   ```bash
   npm run build
   ```

2. **The build artifacts** will be in the `build/` directory

3. **Serve the built application:**
   ```bash
   npm start
   ```

## 🔍 Best Practices

### Code Organization
- Components are organized by feature/domain
- Custom hooks for reusable logic
- Services layer for API integration
- Type definitions for better IntelliSense

### Performance
- Lazy loading for images
- Code splitting with manual chunks
- Memoization with `useCallback` and `useMemo`
- Optimized bundle size

### Accessibility
- ARIA labels and roles
- Keyboard navigation support
- Screen reader friendly
- Color contrast compliance

### Security
- Environment variables for sensitive data
- Input validation and sanitization
- HTTPS enforcement in production
- Content Security Policy headers

## 🤝 Contributing

1. Follow the existing code style
2. Run linting and type checking before committing
3. Write meaningful commit messages
4. Update documentation when needed

## 📄 License

This project is private and proprietary.

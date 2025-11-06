import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import LoginForm from './screens/LoginForm.jsx'
import ProfessorListScreen from './screens/ProfessorListScreen'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App/>
    {/*<LoginForm title="Login"/>*/}
    {/*<ProfessorListScreen/>*/}
  </StrictMode>,
)

import { Box, Drawer, List, ListItem, ListItemButton, ListItemText } from '@mui/material';
import { Outlet, Link, useLocation } from 'react-router-dom';
import logo from '../assets/logo.png';

const drawerWidth = 240;


const isMenuItemActive = (itemPath, currentPath) => {
  if (currentPath === itemPath) {
    return true;
  }
  if (currentPath.startsWith(itemPath + '/')) {
    return true;
  }
  return false;
};

function HomeScreen({paths}) {
  const location = useLocation();

  return (
    <Box sx={{ display: 'flex', height: '100vh' }}>
      <Drawer
        variant="permanent"
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: drawerWidth,
            boxSizing: 'border-box',
            backgroundColor: '#1976d2',
            color: 'white',
          },
        }}
      >
        <Box sx={{ p: 2, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
          <img 
            src={logo} 
            alt="Logo" 
            style={{ 
              maxWidth: '100%', 
              height: 'auto',
              maxHeight: '60px',
              objectFit: 'contain'
            }} 
          />
        </Box>
        <Box sx={{ overflow: 'auto' }}>
          <List>
            {paths.map((item) => {
              const isActive = isMenuItemActive(item.path, location.pathname);
              
              return (
                <ListItem key={item.path} disablePadding sx={{ px: 1, py: 0.5 }}>
                  <ListItemButton
                    component={Link}
                    to={item.path}
                    sx={{
                      borderRadius: 1,
                      color: 'white',
                      backgroundColor: isActive ? 'rgba(255, 255, 255, 0.16)' : 'transparent',
                      '&:hover': {
                        backgroundColor: isActive ? 'rgba(255, 255, 255, 0.24)' : 'rgba(255, 255, 255, 0.08)',
                      },
                      transition: 'background-color 0.2s ease',
                      fontWeight: isActive ? 600 : 400,
                    }}
                  >
                    <ListItemText 
                      primary={item.text}
                    />
                  </ListItemButton>
                </ListItem>
              );
            })}
          </List>
        </Box>
      </Drawer>
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          width: `calc(100% - ${drawerWidth}px)`,
        }}
      >
        <Outlet />
      </Box>
    </Box>
  );
}

export default HomeScreen;
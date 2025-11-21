/**
 * JavaScript para manejar la navbar dinámica según el estado de autenticación
 * Se ejecuta en todas las páginas para mantener la UI consistente
 */

// Función para actualizar la navbar basada en el estado de autenticación
function updateNavbarAuth() {
    const isAuthenticated = AuthService.isAuthenticated();
    
    // Elementos para usuarios no autenticados
    const loginElements = document.querySelectorAll('.auth-login');
    // Elementos para usuarios autenticados
    const logoutElements = document.querySelectorAll('.auth-logout');
    
    if (isAuthenticated) {
        // Usuario autenticado - mostrar dropdown de cuenta
        loginElements.forEach(el => {
            el.style.display = 'none';
        });
        
        logoutElements.forEach(el => {
            el.style.display = 'block';
        });
        
        // Intentar mostrar nombre del usuario si está disponible
        updateUserName();
        
    } else {
        // Usuario no autenticado - mostrar enlace de portal/login
        loginElements.forEach(el => {
            el.style.display = 'block';
        });
        
        logoutElements.forEach(el => {
            el.style.display = 'none';
        });
    }
}

// Función para actualizar el nombre del usuario en la navbar
function updateUserName() {
    const userNameElement = document.getElementById('user-name');
    if (userNameElement) {
        // Por ahora mostrar "Mi Cuenta", pero podrías extraer el nombre del JWT
        // o hacer una llamada a la API para obtener el perfil del usuario
        const token = AuthService.getToken();
        if (token) {
            try {
                // Decodificar JWT para obtener información del usuario
                const payload = JSON.parse(atob(token.split('.')[1]));
                const userName = payload.sub || payload.email || 'Mi Cuenta';
                
                // Si es un email, mostrar solo la parte antes del @
                if (userName.includes('@')) {
                    const displayName = userName.split('@')[0];
                    userNameElement.textContent = displayName.charAt(0).toUpperCase() + displayName.slice(1);
                } else {
                    userNameElement.textContent = userName;
                }
            } catch (e) {
                // Si hay error decodificando, usar texto por defecto
                userNameElement.textContent = 'Mi Cuenta';
            }
        }
    }
}

// Función mejorada para manejar logout
function handleLogout() {
    // Confirmar logout
    if (confirm('¿Estás seguro de que quieres cerrar sesión?')) {
        AuthService.logout();
        
        // Actualizar inmediatamente la navbar
        updateNavbarAuth();
        
        // Mostrar mensaje y redirigir
        AuthService.showSuccess('Sesión cerrada exitosamente');
        
        setTimeout(() => {
            // Redirigir a home si estamos en una página protegida
            const currentPath = window.location.pathname;
            const protectedPaths = ['/portal/estudiante', '/portal/acceso'];
            
            if (protectedPaths.some(path => currentPath.includes(path))) {
                window.location.href = '/';
            } else {
                // Solo recargar la página si estamos en una página pública
                window.location.reload();
            }
        }, 1000);
    }
}

// Función para proteger rutas del portal
function protectPortalRoute() {
    const currentPath = window.location.pathname;
    const protectedPaths = ['/portal/estudiante'];
    
    if (protectedPaths.some(path => currentPath.includes(path))) {
        if (!AuthService.isAuthenticated()) {
            AuthService.showError('Debe iniciar sesión para acceder a esta página');
            setTimeout(() => {
                window.location.href = '/portal/acceso/loginportal';
            }, 2000);
            return false;
        }
    }
    return true;
}

// Función para destacar el elemento activo en la navbar
function highlightActiveNavItem() {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('.navbar-nav .nav-link');
    
    navLinks.forEach(link => {
        link.classList.remove('active');
        
        const href = link.getAttribute('href');
        if (href && (href === currentPath || currentPath.startsWith(href))) {
            link.classList.add('active');
        }
    });
    
    // Casos especiales
    if (currentPath === '/' || currentPath === '/home') {
        const homeLink = document.querySelector('.navbar-nav .nav-link[href="/"]');
        if (homeLink) homeLink.classList.add('active');
    }
    
    if (currentPath.includes('/carreras')) {
        const carrerasLink = document.querySelector('.navbar-nav .nav-link[href="/carreras"]');
        if (carrerasLink) carrerasLink.classList.add('active');
    }
    
    if (currentPath.includes('/portal')) {
        const portalLink = document.querySelector('.navbar-nav .nav-link[href="/portal"]');
        if (portalLink) portalLink.classList.add('active');
    }
    
    if (currentPath.includes('/admision')) {
        const admisionLink = document.querySelector('.navbar-nav .nav-link[href="/admision"]');
        if (admisionLink) admisionLink.classList.add('active');
    }
}

// Inicializar cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', function() {
    // Esperar un poco para asegurar que AuthService esté cargado
    setTimeout(() => {
        if (typeof AuthService !== 'undefined') {
            // Proteger rutas si es necesario
            protectPortalRoute();
            
            // Actualizar navbar según estado de autenticación
            updateNavbarAuth();
            
            // Destacar elemento activo
            highlightActiveNavItem();
        }
    }, 100);
});

// Escuchar cambios en localStorage (para cuando el usuario haga login/logout en otra pestaña)
window.addEventListener('storage', function(e) {
    if (e.key === 'jwt_token') {
        updateNavbarAuth();
    }
});

// Función para mostrar notificación de bienvenida cuando el usuario hace login
function showWelcomeMessage() {
    const isAuthenticated = AuthService.isAuthenticated();
    const hasShownWelcome = sessionStorage.getItem('welcome_shown');
    
    if (isAuthenticated && !hasShownWelcome) {
        AuthService.showSuccess('¡Bienvenido al portal estudiantil!');
        sessionStorage.setItem('welcome_shown', 'true');
    }
}

// Ejecutar mensaje de bienvenida
document.addEventListener('DOMContentLoaded', function() {
    setTimeout(showWelcomeMessage, 500);
});
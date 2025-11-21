/**
 * JavaScript para manejar autenticación con JWT
 * Conecta el frontend Thymeleaf con la API REST
 */

// URL base de la API
const API_BASE_URL = 'http://localhost:8080/api';

// Utilidad para hacer requests con fetch
class ApiClient {
    
    static async request(url, options = {}) {
        const token = localStorage.getItem('jwt_token');
        
        const config = {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            ...options
        };
        
        // Agregar token JWT si existe
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        
        try {
            const response = await fetch(url, config);
            const data = await response.json();
            
            if (!response.ok) {
                throw new Error(data.message || `Error ${response.status}`);
            }
            
            return data;
        } catch (error) {
            console.error('Error en request:', error);
            throw error;
        }
    }
    
    static async get(endpoint) {
        return this.request(`${API_BASE_URL}${endpoint}`);
    }
    
    static async post(endpoint, data) {
        return this.request(`${API_BASE_URL}${endpoint}`, {
            method: 'POST',
            body: JSON.stringify(data)
        });
    }
}

// Funciones de autenticación
class AuthService {
    
    static async login(email, password) {
        try {
            const response = await ApiClient.post('/auth/login', {
                email: email,
                password: password
            });
            
            // Guardar token en localStorage
            if (response.token) {
                localStorage.setItem('jwt_token', response.token);
                console.log('Login exitoso');
                
                // Notificar cambio de autenticación
                if (typeof notifyAuthChange === 'function') {
                    notifyAuthChange();
                }
                
                return response;
            }
            
        } catch (error) {
            console.error('Error en login:', error);
            this.showError('Error de login: ' + error.message);
            throw error;
        }
    }
    
    static async register(nombre, email, password) {
        try {
            const response = await ApiClient.post('/auth/registro', {
                nombre: nombre,
                email: email,
                password: password
            });
            
            console.log('Registro exitoso:', response);
            this.showSuccess('Usuario registrado exitosamente');
            return response;
            
        } catch (error) {
            console.error('Error en registro:', error);
            this.showError('Error de registro: ' + error.message);
            throw error;
        }
    }
    
    static logout() {
        localStorage.removeItem('jwt_token');
        console.log('Logout exitoso');
        // Opcional: redirigir a home
        window.location.href = '/';
    }
    
    static isAuthenticated() {
        const token = localStorage.getItem('jwt_token');
        return token !== null;
    }
    
    static getToken() {
        return localStorage.getItem('jwt_token');
    }
    
    // Utilidades para mostrar mensajes
    static showError(message) {
        // Buscar contenedor de mensajes o crear uno
        let messageContainer = document.getElementById('message-container');
        if (!messageContainer) {
            messageContainer = document.createElement('div');
            messageContainer.id = 'message-container';
            messageContainer.style.position = 'fixed';
            messageContainer.style.top = '20px';
            messageContainer.style.right = '20px';
            messageContainer.style.zIndex = '1000';
            document.body.appendChild(messageContainer);
        }
        
        const alert = document.createElement('div');
        alert.className = 'alert alert-danger alert-dismissible fade show';
        alert.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;
        
        messageContainer.appendChild(alert);
        
        // Auto-remover después de 5 segundos
        setTimeout(() => {
            if (alert && alert.parentNode) {
                alert.parentNode.removeChild(alert);
            }
        }, 5000);
    }
    
    static showSuccess(message) {
        let messageContainer = document.getElementById('message-container');
        if (!messageContainer) {
            messageContainer = document.createElement('div');
            messageContainer.id = 'message-container';
            messageContainer.style.position = 'fixed';
            messageContainer.style.top = '20px';
            messageContainer.style.right = '20px';
            messageContainer.style.zIndex = '1000';
            document.body.appendChild(messageContainer);
        }
        
        const alert = document.createElement('div');
        alert.className = 'alert alert-success alert-dismissible fade show';
        alert.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;
        
        messageContainer.appendChild(alert);
        
        setTimeout(() => {
            if (alert && alert.parentNode) {
                alert.parentNode.removeChild(alert);
            }
        }, 5000);
    }
}

// Funciones para usar en formularios HTML
function handleLogin(event) {
    event.preventDefault();
    
    const form = event.target;
    const email = form.email.value;
    const password = form.password.value;
    
    AuthService.login(email, password)
        .then(() => {
            AuthService.showSuccess('Login exitoso');
            // Redirigir al portal después de 1 segundo
            setTimeout(() => {
                window.location.href = '/portal';
            }, 1000);
        })
        .catch(error => {
            // Error ya manejado en AuthService.login
        });
}

function handleRegister(event) {
    event.preventDefault();
    
    const form = event.target;
    const nombre = form.nombre.value;
    const email = form.email.value;
    const password = form.password.value;
    
    AuthService.register(nombre, email, password)
        .then(() => {
            // Después del registro exitoso, hacer login automático
            return AuthService.login(email, password);
        })
        .then(() => {
            AuthService.showSuccess('Registro y login exitosos');
            setTimeout(() => {
                window.location.href = '/portal';
            }, 1000);
        })
        .catch(error => {
            // Error ya manejado en las funciones
        });
}

// Verificar autenticación al cargar páginas
document.addEventListener('DOMContentLoaded', function() {
    // Si estamos en una página que requiere autenticación
    const protectedPages = ['/portal/estudiante'];
    const currentPath = window.location.pathname;
    
    if (protectedPages.some(page => currentPath.startsWith(page))) {
        if (!AuthService.isAuthenticated()) {
            AuthService.showError('Debe iniciar sesión para acceder a esta página');
            setTimeout(() => {
                window.location.href = '/portal/acceso/loginportal';
            }, 2000);
        }
    }
});

// Función para logout (será sobrescrita por navbar-auth.js si está presente)
function handleLogout() {
    AuthService.logout();
    AuthService.showSuccess('Sesión cerrada exitosamente');
    setTimeout(() => {
        window.location.href = '/';
    }, 1000);
}

// Función para notificar cambios de autenticación a otros scripts
function notifyAuthChange() {
    // Disparar evento personalizado para que navbar-auth.js pueda reaccionar
    const event = new CustomEvent('authStateChanged', { 
        detail: { isAuthenticated: AuthService.isAuthenticated() } 
    });
    document.dispatchEvent(event);
}
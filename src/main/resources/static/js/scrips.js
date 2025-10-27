document.addEventListener('DOMContentLoaded', function () {
    console.log('UTNC Website Ready!');
  
    // Validación personalizada para el Formulario de Contacto
    const form = document.getElementById('contactForm');
    if (form) {
      // Array para guardar los datos enviados
      const datosEnviados = [];
  
      // Mensaje dinámico para el textarea de mensaje
      const message = document.getElementById('message');
      let msgHint = document.createElement('div');
      msgHint.id = 'msg-hint';
      msgHint.style.color = '#dc3545';
      msgHint.style.fontSize = '0.9em';
      msgHint.style.display = 'none';
      message.parentNode.appendChild(msgHint);
  
      message.addEventListener('input', function () {
        if (message.value.trim().length > 0 && message.value.trim().length < 10) {
          msgHint.textContent = 'al menos 10 caracteres.';
          msgHint.style.display = 'block';
        } else {
          msgHint.textContent = '';
          msgHint.style.display = 'none';
        }
      });
  
    form.addEventListener('submit', function (event) {
        let valid = true;
  
  
        // Validar nombre: solo letras y espacios
        const name = document.getElementById('name');
        const nameRegex = /^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$/;
        if (!name.value.trim() || !nameRegex.test(name.value.trim())) {
          name.classList.add('is-invalid');
          valid = false;
        } else {
          name.classList.remove('is-invalid');
        }
  
        // Validar carrera: opción seleccionada
        const carrera = document.getElementById('carrera');
        if (!carrera.value) {
          carrera.classList.add('is-invalid');
          valid = false;
        } else {
          carrera.classList.remove('is-invalid');
        }
  
        // Validar edad: entre 16 y 99
        const edad = document.getElementById('edad');
        const edadVal = parseInt(edad.value, 10);
        if (!edad.value || isNaN(edadVal) || edadVal < 16 || edadVal > 99) {
          edad.classList.add('is-invalid');
          valid = false;
        } else {
          edad.classList.remove('is-invalid');
        }
  
        // Validar DNI: 8 dígitos numéricos
        const dni = document.getElementById('dni');
        const dniRegex = /^\d{8}$/;
        if (!dni.value.trim() || !dniRegex.test(dni.value.trim())) {
          dni.classList.add('is-invalid');
          valid = false;
        } else {
          dni.classList.remove('is-invalid');
        }
  
        // Validar email
        const email = document.getElementById('email');
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!email.value.trim() || !emailRegex.test(email.value.trim())) {
          email.classList.add('is-invalid');
          valid = false;
        } else {
          email.classList.remove('is-invalid');
        }
  
        // Validar asunto: no vacío
        const subject = document.getElementById('subject');
        if (!subject.value.trim()) {
          subject.classList.add('is-invalid');
          valid = false;
        } else {
          subject.classList.remove('is-invalid');
        }
  
        // Validar mensaje: mínimo 10 caracteres
        if (!message.value.trim() || message.value.trim().length < 10) {
          message.classList.add('is-invalid');
          valid = false;
        } else {
          message.classList.remove('is-invalid');
        }
  
        if (!valid) {
          event.preventDefault();
          event.stopPropagation();
          form.classList.add('was-validated');
        } else {
          // Guardar datos en el array y mostrar en pantalla
          event.preventDefault();
          const datos = {
            nombre: name.value.trim(),
            carrera: carrera.value,
            edad: edad.value,
            dni: dni.value.trim(),
            asunto: subject.value.trim()
          };
          datosEnviados.push(datos);
  
          // Mostrar los datos en pantalla
          let resultado = document.getElementById('resultado-datos');
          if (!resultado) {
            resultado = document.createElement('div');
            resultado.id = 'resultado-datos';
            resultado.className = 'alert alert-info mt-4';
            form.parentNode.appendChild(resultado);
          }
          resultado.innerHTML = `<strong>Datos enviados:</strong><br>
            Nombre: ${datos.nombre}<br>
            Carrera: ${datos.carrera}<br>
            Edad: ${datos.edad}<br>
            DNI: ${datos.dni}<br>
            Asunto: ${datos.asunto}`;
  
          form.reset();
          form.classList.remove('was-validated');
        }
      });
    }
  });
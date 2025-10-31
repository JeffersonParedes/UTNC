document.addEventListener('DOMContentLoaded', function () {
    console.log('UTNC Website Ready!');
  
    // RESTful Contact Form Logic
    const contactForm = document.getElementById('contactForm');
    if (contactForm) {
        contactForm.addEventListener('submit', async (event) => {
            event.preventDefault();

            const formData = {
                name: document.getElementById('name').value,
                carrera: document.getElementById('carrera').value,
                dni: document.getElementById('dni').value,
                email: document.getElementById('email').value,
                subject: document.getElementById('subject').value,
                message: document.getElementById('message').value
            };

            try {
                const response = await fetch('/api/v1/contacto', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(formData),
                });

                const resultContainer = document.getElementById('contact-form-result');
                if (!resultContainer) {
                    resultContainer = document.createElement('div');
                    resultContainer.id = 'contact-form-result';
                    contactForm.parentNode.insertBefore(resultContainer, contactForm.nextSibling);
                }

                if (response.ok) {
                    const result = await response.json();
                    contactForm.style.display = 'none';
                    resultContainer.innerHTML = `<div class="alert alert-success"><h5>Mensaje Enviado</h5><p>${result.message}</p></div>`;
                } else {
                    const errors = await response.json();
                    // Clear previous errors
                    document.querySelectorAll('.invalid-feedback').forEach(el => el.remove());
                    document.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));

                    // Display new errors
                    for (const field in errors) {
                        const input = document.getElementById(field);
                        if (input) {
                            input.classList.add('is-invalid');
                            const errorDiv = document.createElement('div');
                            errorDiv.className = 'invalid-feedback d-block';
                            errorDiv.innerText = errors[field];
                            input.parentNode.appendChild(errorDiv);
                        }
                    }
                    resultContainer.innerHTML = ''; // Clear general result message if there are specific errors
                }
            } catch (error) {
                console.error('Error:', error);
            }
        });
    }

    // Multi-step form logic for inscription page
    const inscriptionForm = document.getElementById('inscription-form');
    if (inscriptionForm) {
        let currentStep = 1;
        const steps = inscriptionForm.querySelectorAll('.form-step');
        const stepperIcons = document.querySelectorAll('#stepper .step');
        const nextBtn = inscriptionForm.querySelector('#next-btn');
        const prevBtn = inscriptionForm.querySelector('#prev-btn');
        const submitBtn = inscriptionForm.querySelector('#submit-btn');

        const showStep = (stepNumber) => {
            steps.forEach(step => {
                step.classList.toggle('active', parseInt(step.dataset.step) === stepNumber);
            });

            stepperIcons.forEach(stepIcon => {
                const iconStep = parseInt(stepIcon.dataset.step);
                if (iconStep < stepNumber) {
                    stepIcon.classList.add('completed');
                    stepIcon.classList.remove('active');
                } else {
                    stepIcon.classList.toggle('active', iconStep === stepNumber);
                    stepIcon.classList.remove('completed');
                }
            });

            prevBtn.disabled = stepNumber === 1;
            nextBtn.classList.toggle('d-none', stepNumber === steps.length);
            submitBtn.classList.toggle('d-none', stepNumber !== steps.length);
        };

        nextBtn.addEventListener('click', () => {
            if (currentStep < steps.length) {
                currentStep++;
                showStep(currentStep);
            }
        });

        prevBtn.addEventListener('click', () => {
            if (currentStep > 1) {
                currentStep--;
                showStep(currentStep);
            }
        });

        inscriptionForm.addEventListener('submit', async (event) => {
            event.preventDefault();

            const formData = {
                firstName: document.getElementById('firstName').value,
                lastName: document.getElementById('lastName').value,
                dni: document.getElementById('dni').value,
                birthDate: document.getElementById('birthDate').value,
                gender: document.getElementById('gender').value,
                phone: document.getElementById('phone').value,
                email: document.getElementById('email').value,
                schoolName: document.getElementById('schoolName').value,
                graduationYear: document.getElementById('graduationYear').value,
                career: document.getElementById('career').value,
                hasDisability: document.getElementById('hasDisability').checked,
                emergencyContact: document.getElementById('emergencyContact').value,
                emergencyPhone: document.getElementById('emergencyPhone').value,
                acceptTerms: document.getElementById('acceptTerms').checked
            };

            try {
                const response = await fetch('/api/v1/inscripciones', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(formData),
                });

                if (response.ok) {
                    const result = await response.json();
                    inscriptionForm.innerHTML = `<div class="alert alert-success"><h5>¡Inscripción Exitosa!</h5><p>${result.message}</p></div>`;
                } else {
                    const errors = await response.json();
                    // Clear previous errors
                    document.querySelectorAll('.invalid-feedback').forEach(el => el.remove());
                    document.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));

                    // Display new errors
                    for (const field in errors) {
                        const input = document.getElementById(field);
                        if (input) {
                            input.classList.add('is-invalid');
                            const errorDiv = document.createElement('div');
                            errorDiv.className = 'invalid-feedback d-block';
                            errorDiv.innerText = errors[field];
                            input.parentNode.appendChild(errorDiv);
                        }
                    }
                }
            } catch (error) {
                console.error('Error:', error);
                // Handle network errors
            }
        });

        // Initially show the first step
        showStep(currentStep);
    }

    // Filtrado de carreras por facultad en /carreras
    const tabs = document.querySelectorAll('#facultadesTabs .nav-link');
    const cards = document.querySelectorAll('#gridCarreras .carrera-card');

    function activarFacultad(facultad) {
        if (!facultad) return;
        tabs.forEach(t => t.classList.toggle('active', t.dataset.facultad === facultad));
        cards.forEach(c => {
            c.style.display = (c.dataset.facultad === facultad) ? '' : 'none';
        });
    }

    if (tabs.length && cards.length) {
        // Click en pestañas
        tabs.forEach(tab => {
            tab.addEventListener('click', (e) => {
                e.preventDefault();
                const fac = tab.dataset.facultad;
                history.replaceState(null, '', `#${fac}`);
                activarFacultad(fac);
            });
        });

        // Selección por hash inicial o por defecto
        const hash = (location.hash || '#ingenieria').replace('#', '');
        activarFacultad(hash);

        // Responder a cambios de hash
        window.addEventListener('hashchange', () => {
            const fac = (location.hash || '#ingenieria').replace('#', '');
            activarFacultad(fac);
        });
    }

    // Smooth scroll para navbar de facultades en /carreras
    const navFacultades = document.querySelectorAll('#facultadesNav a[href^="#"]');
    if (navFacultades.length) {
        navFacultades.forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                const targetId = link.getAttribute('href').substring(1);
                const target = document.getElementById(targetId);
                if (target) {
                    target.scrollIntoView({ behavior: 'smooth', block: 'start' });
                    history.replaceState(null, '', `#${targetId}`);
                }
            });
        });
    }
});

document.addEventListener('DOMContentLoaded', function () {
    console.log('UTNC Website Ready!');
  
    // Validación personalizada para el Formulario de Contacto
    const form = document.getElementById('contactForm');
    if (form) {
      // ... (existing contact form logic remains the same) ...
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

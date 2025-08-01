const canvas = document.getElementById("canvas1");
const ctx = canvas.getContext("2d");
canvas.style.backgroundColor = "black";
canvas.width = canvas.parentElement.clientWidth - 1;
canvas.height = canvas.parentElement.clientHeight - 1;

const bounds = [[0, 0], [canvas.width, canvas.height]];

var mouseX = 0;
var mouseY = 0;let isMouseDown = false;

canvas.addEventListener('mousedown', () => {
    isMouseDown = true;
});

canvas.addEventListener('mouseup', () => {
    isMouseDown = false;
});

canvas.addEventListener('mouseleave', () => {
    isMouseDown = false;
});

canvas.addEventListener('mousemove', (event) => {
    const rect = canvas.getBoundingClientRect();
    mouseX = event.clientX - rect.left;
    mouseY = event.clientY - rect.top;
});

function trueModulo(n, m) {
    return ((n % m) + m) % m;
}    

function clamp(value, min, max) {
    return Math.min(Math.max(value, min), max);
}

class Particle {
    constructor(x, y, vx, vy, color = 'lightgray') {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.color = color;
    }

    move() {
        this.x += this.vx;
        this.y += this.vy;
        this.x = trueModulo(this.x, bounds[1][0]);
        this.y = trueModulo(this.y, bounds[1][1]);        
        // if (Math.random() < 0.00003) {
        //     this.x += Math.random() * 100 - 50;
        //     this.y += Math.random() * 100 - 50;
        // }
        // if (Math.random() < 0.0001) {    
        //     this.x += Math.random() * 100 - 50;
        //     this.y += Math.random() * 100 - 50;
        
        // }
    }
}

const particles = [];

function spawnParticle(x, y, color = 'lightgray') {
    var i = particles.length;
    var vx = Math.cos(i) * (1 + i/10000) * 0.5;
    var vy = Math.sin(i) * (1 + i/10000) * 0.5;

    particles.push(new Particle(x, y, vx, vy, color));
}

// for (let i = 0; i < 2000; i++) {
//     var x = bounds[1][0]/2 + Math.cos(i) * 100;
//     var y = bounds[1][1]/2 + Math.cos(i) * 100;
//     spawnParticle(x, y);
// }

function moveParticles(particles) {
    particles.forEach(particle => {
        particle.move();
    });
}

function renderParticles(particles) {
    ctx.clearRect(0, 0, bounds[1][0], bounds[1][1]);
    particles.forEach(particle => {
        ctx.beginPath();
        ctx.arc(particle.x, particle.y, 3, 0, 2 * Math.PI);
        ctx.fillStyle = particle.color;
        ctx.fill();
    });
    ctx.beginPath();
    particles.forEach(particle => {
        ctx.lineTo(particle.x, particle.y);
    });
    ctx.strokeStyle = `rgb(100, 100, 255, ${25/particles.length + 0.1})`;
    ctx.stroke();
} 

function renderTitle() {
    ctx.font = '24px Century Gothic';
    ctx.fillStyle = 'white';
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';
    const text = "Welcome to ascension.box";
    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
    ctx.fillText(text, centerX, centerY);
}


setInterval(() => {
    renderParticles(particles);
    renderTitle();
    moveParticles(particles);        
    if (isMouseDown) {
        spawnParticle(mouseX, mouseY, 'darkblue');
    }
    if (Math.random() < 0.1 - particles.length / 30000) {
        spawnParticle(
            bounds[1][0]/2,
            bounds[1][1]/2);
    }
}, 1000/60);

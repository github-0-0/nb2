document.getElementById("myForm").addEventListener("submit", function(e) {
    e.preventDefault();

    const formData = new FormData(this);
    try {
        gameId = +formData.get(text);
    } catch {
        game = 0;
    }
    
});

var gameId = 0;

async function getGame(id) {
    try {
        const response = await fetch('/getGame?id=' + id);
        const data = await response.json();
        console.log('Game received:', data);
        return data;
    } catch (e) {
        console.error('Error fetching game:', e);
    }
}

const canvas = document.getElementById("gameCanvas");
const ctx = canvas.getContext("2d");
canvas.style.backgroundColor = "black";
canvas.width = canvas.parentElement.clientWidth;
canvas.height = canvas.parentElement.clientHeight;

function drawCircle(x, y, radius, color) {
    ctx.beginPath();
    ctx.arc(x, y, radius, 0, 2 * Math.PI);
    ctx.fillStyle = color;
    ctx.fill();
}

function drawGame(game) {
    try {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        var simState = game.simState;
        var colliderStates = simState.colliderStates;
        colliderStates.forEach(state => {
            var pos = state.collider.position;
            drawCircle(pos.x, pos.y, state.collider.radius, 'white');
        });
        console.log("success");
    } catch (e) {
        console.log("h");
    }
}

var update = async () => {
    try {
        const game = await getGame(0);
        if (game) {
            drawGame(game);
            let wait = game.simState.dt;
            console.log(wait);
            setTimeout(update, wait);
        } else {
            setTimeout(update, 1000);
        }
    } catch (e) {
        console.error(e);
    }
};

update();
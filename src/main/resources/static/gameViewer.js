function getGame(id, callback) {
    $.ajax({
        url: '/getGame',
        method: 'GET',
        data: { id: id },
        success: function(response) {
            callback(response);
            console.log('Game received:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error fetching game:', error);
        }
    });
}

const canvas = document.getElementById("gameCanvas");
const ctx = canvas.getContext("2d");
canvas.style.backgroundColor = "black";
canvas.width = canvas.parentElement.clientWidth - 1;
canvas.height = canvas.parentElement.clientHeight - 1;

function drawCircle(x, y, radius, color) {
    ctx.beginPath();
    ctx.arc(x, y, radius, 0, 2 * Math.PI);
    ctx.fillStyle = color;
    ctx.fill();
}

function drawGame(game) {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    var simState = game.simState;
    var colliderStates = simState.colliderStates;
    colliderStates.forEach(state => {
        var pos = state.collider.position;
        drawCircle(pos.x, pos.y, state.collider.radius, 'white');
    });
}

setInterval(() => {
    getGame(0, drawGame);
}, 1 / 15 * 1000);
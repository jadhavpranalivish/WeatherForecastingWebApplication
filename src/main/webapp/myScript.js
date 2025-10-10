var weatherIcon = document.getElementById("weather-icon");
var wcInput = document.getElementById("wc");
var val = wcInput ? wcInput.value : null;

if (val) {
  switch (val) {
    case 'Clouds':
      weatherIcon.src = "https://cdn-icons-png.flaticon.com/512/1163/1163624.png";
      break;
    case 'Clear':
      weatherIcon.src = "https://cdn-icons-png.flaticon.com/512/6974/6974833.png";
      break;
    case 'Rain':
      weatherIcon.src = "https://cdn-icons-png.flaticon.com/512/1163/1163657.png";
      break;
    case 'Mist':
    case 'Haze':
      weatherIcon.src = "https://cdn-icons-png.flaticon.com/512/4005/4005901.png";
      break;
    case 'Snow':
      weatherIcon.src = "https://cdn-icons-png.flaticon.com/512/642/642102.png";
      break;
    default:
      weatherIcon.src = "https://cdn-icons-png.flaticon.com/512/1163/1163661.png"; // default cloudy
  }
}

// ---------------- Live Clock ----------------
function updateClock() {
  let now = new Date();
  let options = {
    weekday: 'short',
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  };
  let formatted = now.toLocaleString('en-IN', options);
  
  let dateElement = document.querySelector(".date");
  if (dateElement) {
    dateElement.textContent = formatted;
  }
}

// Update every second
setInterval(updateClock, 1000);

// Call once immediately
updateClock();

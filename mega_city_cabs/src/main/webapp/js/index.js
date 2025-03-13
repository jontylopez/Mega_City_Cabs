const container = document.querySelector(".services-container");

function scrollLeft() {
    container.scrollBy({left: -250, behavior: "smooth"});
}

function scrollRight() {
    container.scrollBy({left: 250, behavior: "smooth"});
}

// Enable click and drag scrolling
let isDown = false;
let startX;
let scrollLeftVal;

container.addEventListener("mousedown", (e) => {
    isDown = true;
    container.classList.add("active");
    startX = e.pageX - container.offsetLeft;
    scrollLeftVal = container.scrollLeft;
});

container.addEventListener("mouseleave", () => {
    isDown = false;
    container.classList.remove("active");
});

container.addEventListener("mouseup", () => {
    isDown = false;
    container.classList.remove("active");
});

container.addEventListener("mousemove", (e) => {
    if (!isDown)
        return;
    e.preventDefault();
    const x = e.pageX - container.offsetLeft;
    const walk = (x - startX) * 2; // Increase multiplier for faster scroll
    container.scrollLeft = scrollLeftVal - walk;
});
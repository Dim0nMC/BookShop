document.addEventListener("DOMContentLoaded", () => {
    fetch('/api/books')
        .then(res => res.json())
        .then(books => renderBooks(books));

    document.getElementById("searchBtn").addEventListener("click", () => {
        const query = document.getElementById("searchInput").value;
        if (query.trim()) {
            window.location.href = `/search.html?query=${encodeURIComponent(query)}`;
        }
    });
});

function renderBooks(books) {
    const grid = document.getElementById("bookGrid");
    grid.innerHTML = books.map(book => `
    <div class="book" onclick="location.href='/book.html?id=${book.id}'">
      <img src="${book.cover}" alt="${book.title}" />
      <h3>${book.title}</h3>
      <p>${book.author}</p>
    </div>
  `).join('');
}

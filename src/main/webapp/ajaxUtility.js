function autocompleteSearch() {
    const query = document.getElementById("searchBox").value;
    if (query.length === 0) {
        document.getElementById("suggestions").innerHTML = "";
        return;
    }

    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            const products = JSON.parse(this.responseText);
            let suggestionHTML = '<ul class="list-group">';
            products.forEach(product => {
                suggestionHTML += `<li class="list-group-item" onclick="redirectToProduct(${product.id})">
                                      ${product.name}
                                   </li>`;
            });
            suggestionHTML += '</ul>';
            document.getElementById("suggestions").innerHTML = suggestionHTML;
        }
    };

    xhr.open("GET", "ajaxServlet?term=" + query, true);
    xhr.send();
}

function redirectToProduct(productId) {
    window.location.href = 'productDetailsServlet?productID=' + productId;
}

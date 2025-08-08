document.addEventListener('DOMContentLoaded', () => {
    const productList = document.getElementById('product-list');

    fetch('/products')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(products => {
            if (products.length > 0) {
                productList.innerHTML = '<ul></ul>';
                const ul = productList.querySelector('ul');
                products.forEach(product => {
                    const li = document.createElement('li');
                    li.innerHTML = `
                        <strong>ID:</strong> ${product.id}<br>
                        <strong>Product Name:</strong> ${product.name}<br>
                        <strong>Description:</strong> ${product.description}<br>
						<strong>Quantity:</strong> ${product.quantity}
                    `;
                    ul.appendChild(li);
                });
            } else {
                productList.innerHTML = '<p>No products found in the database.</p>';
            }
        })
        .catch(error => {
            console.error('Error fetching products:', error);
            productList.innerHTML = `<p style="color:red;">Failed to fetch data: ${error.message}. The API might not be running or is unreachable.</p>`;
        });
});
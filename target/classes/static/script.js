document.addEventListener('DOMContentLoaded', () => {
    const warehouseList = document.getElementById('warehouse-list');

    fetch('/warehouses')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(warehouses => {
            if (warehouses.length > 0) {
                warehouseList.innerHTML = '<ul></ul>';
                const ul = warehouseList.querySelector('ul');
                warehouses.forEach(warehouse => {
                    const li = document.createElement('li');
                    li.innerHTML = `
                        <strong>Warehouse Name:</strong> ${warehouse.name}<br>
                        <strong>Location:</strong> ${warehouse.location}<br>
                        <strong>Capacity:</strong> ${warehouse.capacity}
                    `;
                    ul.appendChild(li);
                });
            } else {
                warehouseList.innerHTML = '<p>No warehouses found in the database.</p>';
            }
        })
        .catch(error => {
            console.error('Error fetching warehouses:', error);
            warehouseList.innerHTML = `<p style="color:red;">Failed to fetch data: ${error.message}. The API might not be running or is unreachable.</p>`;
        });
});
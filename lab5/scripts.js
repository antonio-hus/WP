document.addEventListener('DOMContentLoaded', function () {

    const principalMenu = document.getElementById('principal-menu');

    for (let i = 1; i <= 5; i++) {
        const li = document.createElement('li');

        // Create the principal menu link
        const menuLink = document.createElement('a');
        menuLink.textContent = `Menu ${i}`;
        menuLink.href = "#";
        menuLink.classList.add("mainmenu");

        // Create the submenu
        const submenu = document.createElement('ul');
        submenu.classList.add('submenu');

        // Generate a random number of submenus (between 3 and 5)
        // Math.random() => random number from [0, 1)
        // Math.random() * 3 => random number from [0, 3)
        // Math.floor(Math.random() * 3) => random number from {0, 1, 2}
        // Math.floor(Math.random() * 3) + 3 => random number from {3, 4, 5}
        const submenuCount = Math.floor(Math.random() * 3) + 3;
        for (let j = 1; j <= submenuCount; j++) {
            const subLi = document.createElement('li');
            const subLink = document.createElement('a');
            subLink.textContent = `Submenu ${i}.${j}`;
            subLink.href = "#";
            subLi.appendChild(subLink);
            subLi.classList.add('sublink')
            submenu.appendChild(subLi);
        }

        // Append elements
        li.appendChild(menuLink);
        li.appendChild(submenu);
        principalMenu.appendChild(li);

        // Toggle submenu visibility on click
        menuLink.addEventListener('click', function (event) {
            for(let menu in document.getElementsByClassName('submenu')) {
                let element = document.getElementsByClassName('submenu')[menu];
                if (element.style !== undefined) {
                    element.style.display = 'none';
                }
            }

            submenu.style.display = submenu.style.display === 'block' ? 'none' : 'block';
        });
    }
});
